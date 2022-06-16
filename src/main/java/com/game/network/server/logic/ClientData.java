package com.game.network.server.logic;

public class ClientData {
    private String Adress;
    private int port;
    private String username;

    public ClientData(String adress, String username, int port) {
        Adress = adress;
        this.port = port;
        this.username = username;
    }

    public String getAdress() {
        return Adress;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "ClientData{" +
                "Adress='" + Adress + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                '}';
    }
}
