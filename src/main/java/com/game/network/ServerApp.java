package com.game.network;

import com.game.network.client.Client;
import com.game.network.server.Server;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) {
        Thread serverTh = new Thread(() -> {
            try {
                Server s = Server.getServer();
                s.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverTh.start();
        Thread c1 = new Thread(() -> {
            Client c = new Client("testgvzuyevuz");
            System.out.println(c.getOpponent());
        });
        c1.start();

        Client c = new Client("test proxy");
        c.getToken();
    }
}
