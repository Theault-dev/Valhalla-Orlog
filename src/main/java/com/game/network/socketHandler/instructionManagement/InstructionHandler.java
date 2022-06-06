package com.game.network.socketHandler.instructionManagement;

import java.io.IOException;

public interface InstructionHandler {
    public void sendInstruction(Instruction instruction) throws Exception;
    public Instruction waitForInstruction() throws Exception;
    public void handleInstruction(Instruction instruction);
}
