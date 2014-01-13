package com.example.echaractersheet;

import java.io.Serializable;

public class Skill implements Serializable {
	static final long serialVersionUID = 44L;
	
	private String name;
	private String abilName;
	
	public int score;
	public int abilMod;
	public int miscMod;
	public int armorPen;		// MaxInt if N/A
	
	public boolean trained;
	
	public String getName() { return name; };
	public String getAbilName() { return abilName; };
	
	public Skill(String n, String abN, int aPen)
	{
		name = n;
		abilName = abN;
		armorPen = aPen;
		score = abilMod = miscMod = 0;
		trained = false;
	}
}
