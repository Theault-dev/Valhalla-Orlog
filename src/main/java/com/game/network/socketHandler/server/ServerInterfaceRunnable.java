package com.game.network.socketHandler.server;

import com.game.network.socketHandler.OrlogInstruction;
import com.game.network.socketHandler.server.clientHandling.ServerClientHandlingInterfaceRunnable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

public class ServerInterfaceRunnable implements Runnable {
    private ServerSocket server;
    HashMap<String, Thread> clients;
    public ServerInterfaceRunnable() throws IOException {
        this.server = new ServerSocket(42069);
        clients = new HashMap<String, Thread>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = server.accept();
                String token = String.valueOf(UUID.randomUUID());
                Thread th = new Thread(new ServerClientHandlingInterfaceRunnable(client, token));
                th.start();
                clients.put(token, th);
            } catch (Exception e) {
                System.out.println(e);
                // TODO handling
            }
        }
    }
}
