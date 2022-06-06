package com.game.network.socketHandler.client;

import com.game.network.socketHandler.OrlogInstruction;
import com.game.network.socketHandler.instructionManagement.Instruction;
import com.game.network.socketHandler.instructionManagement.InstructionHandler;
import com.game.network.socketHandler.instructionManagement.specialInstruction.InitInstruction;

import java.io.*;
import java.net.Socket;

public class ClientIntefaceRunnable implements  Runnable, InstructionHandler {
    private Socket s;
    private String token;

    public ClientIntefaceRunnable() throws IOException {
        s = new Socket("127.0.0.1", 42069);
    }
    @Override
    public void run() {
        try {
            handleInstruction(waitForInstruction());
            while (true) {}
        } catch (Exception e) {
            System.out.println(e);
            // TODO exception handling
        }
    }

    @Override
    public void sendInstruction(Instruction instruction) throws Exception {
        if (!s.isConnected() || s.isClosed())
            throw new Exception("The connection to the server has been closed.");
        OutputStream outStream = s.getOutputStream();
        PrintWriter out = new PrintWriter(outStream);
        out.print(instruction.getSocketRpr());
        out.flush();
    }

    @Override
    public Instruction waitForInstruction() throws Exception {
        if (!s.isConnected() || s.isClosed())
            throw new Exception("The connection to the server has been closed.");
        byte[] buffer = new byte[1024];
        int read;
        while((read = s.getInputStream().read(buffer)) != -1) {
            String output = new String(buffer, 0, read);
            return new Instruction(output);
        };
        throw new Exception("Unexpected error."); // TODO
    }

    @Override
    public void handleInstruction(Instruction instruction) {
        switch (instruction.getType()) {
            case INIT_INSTRUCTION -> {
                try {
                    this.token = instruction.getContent("game_token").toString();
                    System.out.println(this.token);
                } catch (Exception e) {
                    System.out.println(e);
                    // TODO
                }
            }
            case NO_TYPE -> {
                // TODO
            }
        }
    }
}
