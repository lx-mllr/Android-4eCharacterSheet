package com.example.echaractersheet;

import java.io.Serializable;

public class Defense implements Serializable {
	
	private static final long serialVersionUID = 43L;

	private String name;
	public String conditionals;
	
	public int score;
	public int tenPlusHalfLevel;
	public int abilArmor;			// Ability or Armor bonus
	public int classMod;
	public int featMod;
	public int enhMod;
	public int miscMod;
	
	public String getName() { return name; };
	
	public Defense(String n)
	{
		name = n;
		conditionals = "Conditional Bonuses";
		abilArmor = classMod = featMod = enhMod = miscMod = 0;
		tenPlusHalfLevel = 10;
		score = tenPlusHalfLevel + abilArmor + classMod + featMod + enhMod + miscMod;
	}
}
