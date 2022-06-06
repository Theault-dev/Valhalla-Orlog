package com.game.network.logic;

import java.util.ArrayList;

public class Party {
    private ArrayList<Client> p1, p2;

    public Party(ArrayList<Client> p1, ArrayList<Client> p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
}
