package com.game.proxy;

import com.game.network.client.Client;
import com.game.orlog.model.entity.Divinity;
import com.game.orlog.model.entity.Player;

import java.util.ArrayList;

public class PlayerProxy extends Player {
    private Client proxyClient;
    public PlayerProxy(ArrayList<Divinity> divinities, Client proxyClient) {
        super("proxy", divinities);
        this.proxyClient = proxyClient;
    }
}
