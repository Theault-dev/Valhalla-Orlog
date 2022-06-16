package com.game.network.server.router.action;

import com.game.network.server.logic.ClientData;
import com.game.network.server.router.RouterActionType;

public class NewPlayerRouterAction extends  RouterAction {
    private String address, username;
    private int port;

    public NewPlayerRouterAction(RouterActionType type, String clientToken, String address, String username, int port) {
        super(type, clientToken);
        this.address = address;
        this.username = username;
        this.port = port;
    }

    @Override
    public String toString() {
        return "NewPlayerRouterAction{token=" + getClientToken() + '}';
    }

    public ClientData getClientData() {
        return new ClientData(getAddress(), getUsername(), getPort());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
