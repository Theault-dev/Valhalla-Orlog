package com.game.network.server.clientHandling;

import com.game.network.server.logger.ServerLogger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;
import java.util.concurrent.Semaphore;

public class HandlingNewClientRunnable implements Runnable {
    private final ServerSocket serverSocket;
    private BufferedReader _input;
    private final Stack<Request> serverInstructionStack;
    private final Semaphore instructionStackSemaphore;

    public HandlingNewClientRunnable(ServerSocket serverSocket, Stack<Request> serverInstructionStack, Semaphore instructionStackSemaphore) {
        this.serverSocket = serverSocket;
        this.serverInstructionStack = serverInstructionStack;
        this.instructionStackSemaphore = instructionStackSemaphore;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                this._input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                ServerLogger.getLogger().log(System.Logger.Level.INFO, "New client connected " + client);
                JSONObject json = new JSONObject(receive());
                while (!instructionStackSemaphore.tryAcquire());
                serverInstructionStack.add(new Request(json, client));
                instructionStackSemaphore.release();
                ServerLogger.getLogger().log(System.Logger.Level.INFO, "Data received from " + client + " : " + json);
            } catch (Exception e) {
                ServerLogger.getLogger().log(System.Logger.Level.ERROR, "Error while handling clients : " + e.getMessage());
            }
        }
    }

    private String receive() throws IOException {
        return _input.readLine();
    }
}
