package com.game.network.socketHandler.instructionManagement;

import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Iterator;

// TODO special char handling in instruction
public class Instruction {
    protected HashMap<String, Object> content;
    protected InstructionType type;
    public Instruction(InstructionType type) {
        this.type = type;
        this.content = new HashMap<String, Object>();
    }
    public Instruction(String s) throws InvalidParameterException {
        this(InstructionType.NO_TYPE);
        JSONObject contentS = new JSONObject(s);
        for (Iterator<String> it = contentS.keys(); it.hasNext(); ) {
            String key = it.next();
            content.put(key, contentS.get(key));
        }
        // TODO check is working
        if (content.containsKey("type")) {
            String typeStr = content.remove("type").toString();
            InstructionType extractedType = InstructionType.valueOf(typeStr);
            if (extractedType != null) {
                type = extractedType;
            }
        }
    }

    public InstructionType getType() {
        return type;
    }

    public void setType(InstructionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "content=" + content +
                ", type=" + type +
                '}';
    }
    public JSONObject getSocketRpr() {
        JSONObject json = new JSONObject();
        json.put("type", type.toString());
        for (String key :
                content.keySet()) {
            json.put(key, content.get(key));
        }
        return json;
    }
    public void addField(String fieldName, Object data) {
        content.put(fieldName, data);
    }
    public Object getContent(String key) throws Exception {
        if (content.containsKey(key)) {
            return content.get(key);
        } throw new Exception("The given key is not present in the content.");
    }
}
