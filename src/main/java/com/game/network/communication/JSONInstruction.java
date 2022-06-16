package com.game.network.communication;

import org.json.JSONObject;

import java.util.Iterator;

public class JSONInstruction extends JSONObject {
    public static JSONInstruction fromJSON(JSONObject json) throws Exception {
        if (json.has("type")) {
            JSONInstruction converted = new JSONInstruction(InstructionType.fromString(json.getString("type")));
            for (Iterator<String> it = json.keys(); it.hasNext(); ) {
                String key = it.next();
                converted.put(key, json.getString(key));
            }
            return converted;
        } else throw new Exception("Invalid JSON format.");
    }
    public JSONInstruction(InstructionType type) {
        super();
        this.put("type", type.toString());
    }
    public JSONInstruction(InstructionType type, Object obj) {
        super();
        this.put("type", type.toString());
        this.put("content", obj);
    }

    public InstructionType getType() throws Exception {
        if (this.has("type")) {
            return InstructionType.fromString(this.getString("type"));
        } else throw new Exception("Invalid content format.");
    }
}
