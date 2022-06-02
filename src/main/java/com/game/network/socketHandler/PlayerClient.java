package com.game.network;

import java.io.IOException;
import java.net.Socket;

public class PlayerClient {
    Socket gameServerConnection;
    System.Logger out;

    public PlayerClient() {
        gameServerConnection = null;
        out = null;
    }

    public System.Logger getOut() {
        return out;
    }

    public void setOut(System.Logger out) {
        this.out = out;
    }

    public void initConnectionToServer() throws IOException {
        gameServerConnection = new Socket("127.0.0.1", 42069);
        if (out != null) {
            out.log(System.Logger.Level.INFO, "[Player client " + this + "] Connection to the server established successfully !");
        }
        while (gameServerConnection.isConnected()) {

        }
        if (out != null) {
            out.log(System.Logger.Level.ERROR, "[Player client \" + this + \"] connection to the server closed.");
        }
        gameServerConnection.close();
        throw new IOException("Connection to server unexpectedly ended.");
    }
}
