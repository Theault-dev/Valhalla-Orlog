package com.game.network.server.clientHandling;

import com.game.network.communication.JSONInstruction;
import com.game.network.server.logger.ServerLogger;
import com.game.network.server.router.RouterActionType;
import com.game.network.server.router.action.NewPlayerRouterAction;
import com.game.network.server.router.action.RouterAction;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class Request {
    private final JSONObject content;
    private final Socket associatedSocket;
    private OutputStreamWriter _output;

    public Request(JSONObject content, Socket associatedSocket) {
        this.content = content;
        this.associatedSocket = associatedSocket;
    }

    private OutputStreamWriter get_output() throws IOException {
        if (_output == null) {
            _output = new OutputStreamWriter(associatedSocket.getOutputStream(), StandardCharsets.UTF_8);
        }
        return _output;
    }

    public RouterAction process() {
        RouterAction routerAction = new RouterAction(RouterActionType.DEFAULT);
        // processing
        try {
            JSONInstruction instruction = JSONInstruction.fromJSON(content);
            switch (instruction.getType()) {
                case INIT:
                    routerAction = initRoutine(instruction);
                    break;
            }
        } catch (Exception e) {
            ServerLogger.getLogger().log(System.Logger.Level.ERROR, "Error while processing client ("+ associatedSocket +") request : " + e.getMessage());
        }
        // closing connection to client
        try {
            ServerLogger.getLogger().log(System.Logger.Level.WARNING, "Closing connection to client " + associatedSocket);
            associatedSocket.close();
        } catch (Exception e) {
            ServerLogger.getLogger().log(System.Logger.Level.ERROR, "Error while closing connection to client ("+ associatedSocket +") request : " + e.getMessage());
        }

        return routerAction;
    }

    private void send(JSONObject json) throws IOException {
        get_output().write(json.toString());
        get_output().write('\n');
        get_output().flush();
    }

    private RouterAction initRoutine(JSONInstruction instruction) throws Exception {
        String token = generateToken();

        JSONObject response = new JSONObject();
        response.put("token", token);
        send(response);

        return new NewPlayerRouterAction(
                RouterActionType.NEW_PLAYER,
                token,
                instruction.getString("hostname"),
                instruction.getString("username"),
                instruction.getInt("port")
        );
    }

    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[16];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token); //base64 encoding
    }
}
