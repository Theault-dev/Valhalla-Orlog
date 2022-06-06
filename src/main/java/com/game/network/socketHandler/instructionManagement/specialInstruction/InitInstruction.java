package com.game.network.socketHandler.instructionManagement.specialInstruction;

import com.game.network.socketHandler.instructionManagement.Instruction;
import com.game.network.socketHandler.instructionManagement.InstructionType;

import java.security.InvalidParameterException;

public class InitInstruction extends Instruction {
    public InitInstruction(String token) {
        super(InstructionType.INIT_INSTRUCTION);
        content.put("game_token", token);
    }
}
