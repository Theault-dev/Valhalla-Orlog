package com.game.orlog.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.game.orlog.model.enumClass.AffectEnum;
import com.game.orlog.model.enumClass.GamePhaseEnum;
import com.game.orlog.model.enumClass.OfferingEnum;
import com.game.orlog.model.enumClass.OnWhatEnum;
import com.game.orlog.model.enumClass.OnWhoEnum;

/**
 * Model of an Effect for a divinity
 * 
 * @author Theault & Titouan
 *
 */
public class Effect {
	private String name;
	private String description;
	private GamePhaseEnum when;
	private OnWhoEnum onWho;
	private ArrayList<AffectEnum> affect;
	private OfferingEnum offering;
	private OnWhoEnum multiplierOnWho;
	private OnWhatEnum multiplierOnWhat;
	private HashMap<Byte, Float> costs;
	
	public Effect(String name, String description, GamePhaseEnum when,
			OnWhoEnum onWho, ArrayList<AffectEnum> affect,
			OfferingEnum offering, OnWhoEnum multiplierOnWho,
			OnWhatEnum multiplierOnWhat, HashMap<Byte,Float> costs) {
		this.name = name;
		this.description = description;
		this.when = when;
		this.onWho = onWho;
		this.affect = affect;
		this.offering = offering;
		this.multiplierOnWho = multiplierOnWho;
		this.multiplierOnWhat = multiplierOnWhat;
		this.costs = costs;
	}

	public final String getName() {
		return name;
	}

	public final String getDescription() {
		return description;
	}

	public final GamePhaseEnum getWhen() {
		return when;
	}

	public final OnWhoEnum getOnWho() {
		return onWho;
	}

	public final ArrayList<AffectEnum> getAffect() {
		return affect;
	}

	public final OfferingEnum getOffering() {
		return offering;
	}

	public final OnWhoEnum getMultiplierOnWho() {
		return multiplierOnWho;
	}

	public final OnWhatEnum getMultiplierOnWhat() {
		return multiplierOnWhat;
	}

	public final HashMap<Byte, Float> getCosts() {
		return costs;
	}

	@Override
	public String toString() {
		return "Effect [name=" + name + ", description=" + description + ", when=" + when + ", onWho=" + onWho
				+ ", affect=" + affect + ", offering=" + offering + ", multiplierOnWho=" + multiplierOnWho
				+ ", multiplierOnWhat=" + multiplierOnWhat + ", costs=" + costs + "]";
	}
}
