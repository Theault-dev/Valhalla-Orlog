package com.game.network;

import com.game.network.logic.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {
    private System.Logger out;
    private ServerSocket gameServer;
    private ArrayList<Client> clients;

    public GameServer() throws IOException {
        gameServer = new ServerSocket(42069);
        clients = new ArrayList<Client>();
    }

    public System.Logger getOut() {
        return out;
    }

    public void setOut(System.Logger out) {
        this.out = out;
    }

    public void launch() {
        try {
            if (out != null) {
                out.log(System.Logger.Level.INFO,"[Game server " + this + "] waiting for client connection on port (" + gameServer.getLocalPort() + ") ...");
            }
            Socket client = gameServer.accept();
            Thread clientThread = new Thread(){
                public void run(){
                    handleClient(client);
                }
            };
            clientThread.start();
        } catch (Exception e) {
            if (out != null) {
                out.log(System.Logger.Level.ERROR, "[Game server " + this + "] Server error : " + e);
            }
        }
    }

    public void handleClient(Socket client) {
        if (out != null) {
            out.log(System.Logger.Level.INFO, "[Game server " + this + "] client connection (" + client + ")");
        }

        try {
            // TODO client routine
            if (out != null) {
                out.log(System.Logger.Level.INFO, "[Game server " + this + "] client deconnection (" + client + ")");
            }
            client.close();
        } catch (Exception e) {
            if (out != null) {
                out.log(System.Logger.Level.ERROR, "[Game server " + this + "] error while handling client (" + client + ") : " + e);
            }
        }
    }
}
