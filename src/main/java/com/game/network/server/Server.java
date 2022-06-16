package com.game.network.server;

import com.game.network.server.clientHandling.HandlingNewClientRunnable;
import com.game.network.server.clientHandling.Request;
import com.game.network.server.logger.ServerLogger;
import com.game.network.server.logic.ClientData;
import com.game.network.server.logic.Party;
import com.game.network.server.router.ServerRouter;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.Semaphore;

public class Server {
    private static Server serverInstance;

    public static Server getServer() throws IOException {
        if (serverInstance == null)
            serverInstance = new Server();
        return serverInstance;
    }

    public static final int PORT = 42069;
    public static final String ADRESS = "127.0.0.1";
    public final System.Logger logger = ServerLogger.getLogger();
    private final ServerSocket server;
    private final Thread connHandler;
    private final Stack<Request> instructionStack;
    private final ServerRouter router;
    private final ArrayList<Party> onGoingGames;
    private final HashMap<String, ClientData> mappingTokenToClient;
    private final Semaphore instructionStackSemaphore, onGoingGamesSemaphore, mappingTokenToClientSemaphore;

    public Server() throws IOException {
        server = new ServerSocket(PORT);
        instructionStack = new Stack<Request>();
        instructionStackSemaphore = new Semaphore(1);
        connHandler = new Thread(new HandlingNewClientRunnable(server, instructionStack, instructionStackSemaphore));
        onGoingGames = new ArrayList<Party>();
        mappingTokenToClient = new HashMap<String, ClientData>();
        onGoingGamesSemaphore = new Semaphore(1);
        mappingTokenToClientSemaphore = new Semaphore(1);
        router = new ServerRouter(onGoingGames, mappingTokenToClient, onGoingGamesSemaphore, mappingTokenToClientSemaphore);

        connHandler.start();
    }

    public void start() {
        while (true) {
            while (!instructionStackSemaphore.tryAcquire());
            if (!instructionStack.isEmpty()) {
                router.resolve(instructionStack.pop());
            }
            instructionStackSemaphore.release();
        }
    }
}
