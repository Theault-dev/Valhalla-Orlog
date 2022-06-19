package com.game.network.server.router.action.get;

import com.game.network.server.router.RouterActionType;

public enum GetTypes {
    OPPONENT;

    public static GetTypes fromString(String str) throws Exception {
        for (GetTypes type :
                GetTypes.values()) {
            if (str.equals(type.toString()))
                return type;
        }
        throw new Exception("No GetTypes match found");
    }
}
