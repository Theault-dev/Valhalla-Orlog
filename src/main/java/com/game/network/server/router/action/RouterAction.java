package com.game.network.server.router.action;

import com.game.network.server.router.RouterActionType;

public class RouterAction {
    private RouterActionType type;
    private String clientToken;

    public RouterAction(RouterActionType type) {
        this.type = type;
    }

    public RouterAction(RouterActionType type, String clientToken) {
        this.type = type;
        this.clientToken = clientToken;
    }

    @Override
    public String toString() {
        return "RouterAction{" +
                "type=" + type +
                ", clientToken='" + clientToken + '\'' +
                '}';
    }

    public RouterActionType getType() {
        return type;
    }

    public void setType(RouterActionType type) {
        this.type = type;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }
}
