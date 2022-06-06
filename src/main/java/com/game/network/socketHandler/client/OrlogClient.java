package com.game.network.socketHandler.client;

import com.game.network.socketHandler.OrlogNetworkElement;

import java.io.IOException;

public class OrlogClient extends OrlogNetworkElement {
    private Thread th;
    public OrlogClient() throws IOException {
        th = new Thread(new ClientIntefaceRunnable());
    }

    @Override
    public void start() {
        th.start();
    }

    @Override
    public void stop() {
        th.interrupt();
    }
}
