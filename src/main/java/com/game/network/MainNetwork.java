package com.game.network;

import com.game.network.socketHandler.client.OrlogClient;
import com.game.network.socketHandler.server.OrlogServer;

import java.io.IOException;
import java.util.Timer;

public class MainNetwork {
    public static void main(String[] args) {
        try {
            OrlogServer s = new OrlogServer();
            s.start();

            OrlogClient c = new OrlogClient();
            c.start();
            OrlogClient c1 = new OrlogClient();
            c1.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
