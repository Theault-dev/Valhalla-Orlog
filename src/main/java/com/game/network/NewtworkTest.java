package com.game.network;

import com.game.network.client.Client;
import com.game.network.server.Server;

import java.io.IOException;

public class NewtworkTest {
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

        Thread clientThread = new Thread(() -> {
            Client c = new Client("Thread player 0");
            c.getToken();
        });
        clientThread.start();

        Thread clientThread1 = new Thread(() -> {
            Client c = new Client("Thread player 1");
            c.getToken();
        });
        clientThread1.start();

        Thread clientThread2 = new Thread(() -> {
            Client c = new Client("Thread player 2");
            c.getToken();
        });
        clientThread2.start();

        Client c = new Client("Joueur 1");
    }
}
