package com.game.orlog;

import java.util.ArrayList;
import java.util.HashMap;

import com.game.orlog.utils.Utils.Affect;
import com.game.orlog.utils.Utils.MultiplierOnWhat;
import com.game.orlog.utils.Utils.MultiplierOnWho;
import com.game.orlog.utils.Utils.Offering;
import com.game.orlog.utils.Utils.OnWho;
import com.game.orlog.utils.Utils.When;

public class Effect {
	private String name;
	private String description;
	private When when;
	private OnWho onWho;
	private ArrayList<Affect> affect;
	private Offering offering;
	private MultiplierOnWho multiplierOnWho;
	private MultiplierOnWhat multiplierOnWhat;
	private HashMap<Byte, Float> costs;
	
	public Effect(String name, String description, When when,
			OnWho onWho, ArrayList<Affect> affect,
			Offering offering, MultiplierOnWho multiplierOnWho,
			MultiplierOnWhat multiplierOnWhat, HashMap<Byte,Float> costs) {
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
	
}
