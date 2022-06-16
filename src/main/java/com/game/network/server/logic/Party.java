package com.game.network.server.logic;

public class Party {
    String tokenJ1, tokenJ2;

    public Party(String tokenJ1, String tokenJ2) {
        this.tokenJ1 = tokenJ1;
        this.tokenJ2 = tokenJ2;
    }

    public String getTokenJ1() {
        return tokenJ1;
    }

    public String getTokenJ2() {
        return tokenJ2;
    }

    @Override
    public String toString() {
        return "Party{" +
                "tokenJ1='" + tokenJ1 + '\'' +
                ", tokenJ2='" + tokenJ2 + '\'' +
                '}';
    }
}
