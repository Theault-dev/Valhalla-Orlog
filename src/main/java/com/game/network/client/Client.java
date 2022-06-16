package com.game.network.client;

import com.game.network.client.logger.ClientLogger;
import com.game.network.communication.InstructionType;
import com.game.network.communication.JSONInstruction;
import com.game.network.server.Server;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    private String token;
    private ServerSocket exchangeSocket;
    private String username;
    private OutputStreamWriter _output;
    private BufferedReader _input;

    public Client(String username) {
        this.username = username;
    }

    private boolean initConnToServer() {
        try {
            JSONInstruction initInstruction = new JSONInstruction(InstructionType.INIT);
            exchangeSocket = new ServerSocket(0);
            InetAddress addr = exchangeSocket.getInetAddress();
            initInstruction.put("hostname", "127.0.0.1");
            initInstruction.put("port", exchangeSocket.getLocalPort() + "");
            initInstruction.put("username", username);
            JSONObject response = sendDataToServer(initInstruction);
            this.token = response.getString("token");
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public String getToken() {
        while (token == null)
            initConnToServer();
        return token;
    }

    public JSONObject sendDataToServer(JSONObject json) throws IOException {
        // Connection to server
        Socket s = null;
        try {
            s = new Socket(Server.ADRESS, Server.PORT);
        } catch (IOException e) {
            ClientLogger.getLogger().log(System.Logger.Level.ERROR, "Error while connecting to the server : " + e);
            throw e;
        }
        // sending data
        try {
            // sending data
            OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8);
            out.write(json.toString());
            out.write('\n');
            out.flush();
            // server response
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line;
            while ( (line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            return new JSONObject(sb.toString());
        } catch (Exception e) {
            ClientLogger.getLogger().log(System.Logger.Level.ERROR, "Error while communicating with the server : " + e);
            throw e;
        }
    }
}
