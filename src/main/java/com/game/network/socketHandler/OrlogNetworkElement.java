package com.game.network.socketHandler;

import java.io.IOException;

public abstract class OrlogNetworkElement {
    public abstract void start() throws IOException;
    public abstract void stop();
}
