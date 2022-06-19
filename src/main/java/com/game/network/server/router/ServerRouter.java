package com.game.network.server.router;

import com.game.network.server.clientHandling.Request;
import com.game.network.server.logger.ServerLogger;
import com.game.network.server.logic.ClientData;
import com.game.network.server.logic.Party;
import com.game.network.server.router.action.NewPlayerRouterAction;
import com.game.network.server.router.action.RouterAction;
import com.game.network.server.router.action.get.GetRouterAction;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class ServerRouter {
    private String clientWaitingForParty;
    private ArrayList<Party> onGoingGames;
    private HashMap<String, ClientData> mappingTokenToClient;
    private Semaphore onGoingGamesSemaphore, mappingTokenToClientSemaphore;

    public ServerRouter(ArrayList<Party> onGoingGames, HashMap<String, ClientData> mappingTokenToClient, Semaphore onGoingGamesSemaphore, Semaphore mappingTokenToClientSemaphore) {
        this.onGoingGames = onGoingGames;
        this.mappingTokenToClient = mappingTokenToClient;
        this.onGoingGamesSemaphore = onGoingGamesSemaphore;
        this.mappingTokenToClientSemaphore = mappingTokenToClientSemaphore;
    }

    public void resolve(Request req) {
        RouterAction action = req.process();
        ServerLogger.getLogger().log(System.Logger.Level.INFO, "Processing action : "  + action);
        try {
            switch (action.getType()) {
                case DEFAULT:
                    break;
                case NEW_PLAYER:
                    newPlayerRoutine((NewPlayerRouterAction) action);
                    break;
                case GET:
                    getRoutine((GetRouterAction) action);
                    break;
            }
        } catch (Exception e) {
            ServerLogger.getLogger().log(System.Logger.Level.ERROR, "Error while processing requests from client : " + e.getMessage());
        }
    }

    private void newPlayerRoutine(NewPlayerRouterAction action) {
        while (!mappingTokenToClientSemaphore.tryAcquire());
        ClientData newClient = action.getClientData();
        mappingTokenToClient.put(action.getClientToken(), newClient);
        ServerLogger.getLogger().log(System.Logger.Level.INFO, "New PLayer > All players : " + mappingTokenToClient);
        resolveParty(action.getClientToken());
        mappingTokenToClientSemaphore.release();
    }

    private void resolveParty(String newClientToken) {
        if (clientWaitingForParty == null) {
            clientWaitingForParty = newClientToken;
        } else {
            while (!onGoingGamesSemaphore.tryAcquire());
            Party newParty = new Party(newClientToken, clientWaitingForParty);
            onGoingGames.add(newParty);
            clientWaitingForParty = null;
            ServerLogger.getLogger().log(System.Logger.Level.INFO, "New party created : " + newParty);
            onGoingGamesSemaphore.release();
        }
    }

    private void getRoutine(GetRouterAction action) throws IOException {
        switch (action.getGetType()) {
            case OPPONENT -> {
                try {
                    action.sendRequestedData(new JSONObject("opponentToken", getOpponentToken(action.getClientToken())));
                } catch (Exception e) {
                    ServerLogger.getLogger().log(System.Logger.Level.ERROR, "Cant send get response to client : " + e.getMessage());
                    action.sendRequestedData(new JSONObject());
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private String getOpponentToken(String playerToken) throws Exception {
        while (!onGoingGamesSemaphore.tryAcquire());
        for (Party party: onGoingGames) {
            if (playerToken.equals(party.getTokenJ1())) {
                String opponentToken = party.getTokenJ1();

                onGoingGamesSemaphore.release();
                return opponentToken;
            } else if (playerToken.equals(party.getTokenJ2())) {
                String opponentToken = party.getTokenJ2();
                onGoingGamesSemaphore.release();
                return opponentToken;
            }
        }
        onGoingGamesSemaphore.release();
        throw new Exception("No opponent found");
    }
}
