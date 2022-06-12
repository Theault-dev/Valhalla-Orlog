package com.game.orlog.model.entity;

import java.util.ArrayList;

import com.game.orlog.model.Effect;
import com.game.orlog.utils.Utils;

public class Divinity {
	private String name;
	private Effect effect;
	private boolean isAvailable;

	public Divinity(String name, Effect effect) {
		this.name = name;
		this.effect = effect;
		this.isAvailable = true;
	}

	public final boolean isAvailable() {
		return isAvailable;
	}

	public final void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	public static void main(String[] args) {
		ArrayList<Divinity> d = Utils.getAllDivinities();
		System.out.println(d);
	}

	@Override
	public String toString() {
		return "Divinity [name=" + name + ", effect=" + effect + ", isAvailable=" + isAvailable + "]\n";
	}
}
