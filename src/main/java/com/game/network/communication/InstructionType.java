package com.game.network.communication;

public enum InstructionType {
    INIT,
    GET,
    PUT,
    DELETE;

    public static InstructionType fromString(String str) throws Exception {
        for (InstructionType type :
                InstructionType.values()) {
            if (str.equals(type.toString()))
                return type;
        }
        throw new Exception("No InstructionType match found");
    }
}
