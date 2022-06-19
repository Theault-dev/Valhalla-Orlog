package com.game.network.server.router.action.get;

import com.game.network.server.clientHandling.Request;
import com.game.network.server.router.RouterActionType;
import com.game.network.server.router.action.RouterAction;
import org.json.JSONObject;

import java.io.IOException;

public class GetRouterAction extends RouterAction {
    private GetTypes getTypes;
    private Request req;

    public GetRouterAction(RouterActionType type, String clientToken, GetTypes getTypes, Request req) {
        super(type, clientToken);
        this.getTypes = getTypes;
        this.req = req;
    }

    public void sendRequestedData(JSONObject json) throws IOException {
        req.send(json);
    }

    public GetTypes getGetType() {
        return getTypes;
    }
}
