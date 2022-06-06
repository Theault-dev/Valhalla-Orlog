package com.game.network.socketHandler.server.clientHandling;

import com.game.network.socketHandler.instructionManagement.specialInstruction.InitInstruction;
import com.game.network.socketHandler.instructionManagement.Instruction;
import com.game.network.socketHandler.instructionManagement.InstructionHandler;

import java.io.*;
import java.net.Socket;

public class ServerClientHandlingInterfaceRunnable implements Runnable, InstructionHandler {
    private Socket s;
    private String token;
    private PrintWriter out;
    private BufferedReader in;

    public ServerClientHandlingInterfaceRunnable(Socket s, String token) throws IOException {
        this.s = s;
        this.token = token;
        out = new PrintWriter(s.getOutputStream());
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    @Override
    public void run() {
        try {
            Instruction initialInstruction = new InitInstruction(token);
            sendInstruction(initialInstruction);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        // TODO
        return new Instruction("");
    }

    @Override
    public void handleInstruction(Instruction instruction) {
        // System.out.println(instruction);
    }
}
