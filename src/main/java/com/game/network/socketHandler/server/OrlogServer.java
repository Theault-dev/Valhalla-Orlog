package com.game.network.socketHandler.server;

import com.game.network.socketHandler.OrlogNetworkElement;

import java.io.IOException;

public class OrlogServer extends OrlogNetworkElement {
    private Thread th;

    public OrlogServer() throws IOException {
        th = new Thread(new ServerInterfaceRunnable());
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
