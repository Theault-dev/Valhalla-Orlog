package com.game.network.server.router;

public enum RouterActionType {
    DEFAULT,
    NEW_PLAYER;

    public static RouterActionType fromString(String str) throws Exception {
        for (RouterActionType type :
                RouterActionType.values()) {
            if (str.equals(type.toString()))
                return type;
        }
        throw new Exception("No InstructionType match found");
    }
}
