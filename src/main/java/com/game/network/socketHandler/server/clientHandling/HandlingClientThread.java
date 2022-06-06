package com.game.network.socketHandler.server.clientHandling;

public class HandlingClientThread extends Thread {
    private String token;

    public HandlingClientThread(String token) {
        super();
        this.token = token;
    }

    public HandlingClientThread(Runnable target, String token) {
        super(target);
        this.token = token;
    }

    public HandlingClientThread(ThreadGroup group, Runnable target, String token) {
        super(group, target);
        this.token = token;
    }

    public HandlingClientThread(String name, String token) {
        super(name);
        this.token = token;
    }

    public HandlingClientThread(ThreadGroup group, String name, String token) {
        super(group, name);
        this.token = token;
    }

    public HandlingClientThread(Runnable target, String name, String token) {
        super(target, name);
        this.token = token;
    }

    public HandlingClientThread(ThreadGroup group, Runnable target, String name, String token) {
        super(group, target, name);
        this.token = token;
    }

    public HandlingClientThread(ThreadGroup group, Runnable target, String name, long stackSize, String token) {
        super(group, target, name, stackSize);
        this.token = token;
    }

    public HandlingClientThread(ThreadGroup group, Runnable target, String name, long stackSize, boolean inheritThreadLocals, String token) {
        super(group, target, name, stackSize, inheritThreadLocals);
        this.token = token;
    }
}
