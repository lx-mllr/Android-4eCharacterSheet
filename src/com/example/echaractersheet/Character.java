package com.example.echaractersheet;

import java.io.Serializable;
import java.util.Comparator;


class CharacterComparator implements Comparator<Character>{

	@Override
	public int compare(Character c1, Character c2) {
		return Integer.compare(c1.getId(), c2.getId());
	}
	
}

public class Character implements Serializable {
	
	// Serializable allows to be passed through intents
	private static final long serialVersionUID = 42L;				// Life the Universe and Everything
	
	// ID for Saving Characters
	private final int Id;
	public int getId() { return Id; }
	
	// Can't really see a user entering this...
	public static final String SEPARATOR = "``";
	
	//*****************************************BIO************************************************
	// bio: contains Name | Title | Race | Class | 
	//				 Paragon Path | Epic Destiny |
	//				 Gender | Age | Size | 
	//				 Height | Weight | Alignment |
	//				 Deity | Languages |
	//				 Level | Exp | NextLevelExp
	public String bio;
	// backgroundAndPersonality: contains Background | Personality
	// Since background could get rather large I thought it might be best to separate that to a separate string
	public String backgroundAndPersonality;
	
	//****************************************STATS************************
	//			Ability Scores
	public final String[] statAbilNames = { "STR", "CON", "DEX", "INT", "WIS", "CHA" };
	public final String[] statAbilSubHeadings = { "Strength", "Constitution", "Dexterity", "Intelligence", "Wisdom", "Charisma" };
	public int[] statAbilScores, statAbilMods;
	//			Defenses
	public Defense[] defenses;
	//			Passive Abilities
	public final String[] statPassiveNames = { "Initiative", "Speed", "Passive Insight", "Passive Perception" };
	public int statInitScore, statInitDex, statInitHalfLevel, statInitMisc, statInitDieRoll;
	public int statSpeedScore, statSpeedBase, statSpeedArmor, statSpeedItem, statSpeedMisc;
	public int statInsightScore, statInsightSkill;
	public int statPercepScore, statPercepSkill;
	
	//****************************************HEALTH********************************************
	
	//****************************************SKILLS***************************************************
		public Skill[] skills;
		
	//****************************************FEATS*************************************************
		
	//****************************************ATTACKS***********************************************
		
	//****************************************EQUIPMENT***********************************************
	
	public Character(int id)
	{
		Id = id;
		
		// *****BIO*****
		// Give base value for Hints
		bio = "Name" + SEPARATOR + "Title"+ SEPARATOR + "Race" + SEPARATOR + "Class" +  SEPARATOR +  
				"Paragon Path" + SEPARATOR + "Epic Destiny" + SEPARATOR + "Gender" + SEPARATOR +
				"Age" + SEPARATOR + "Size" + SEPARATOR + "Height" + SEPARATOR + "Weight" + SEPARATOR + 
				"Alignment" + SEPARATOR + "Diety" + SEPARATOR + "Languages" + SEPARATOR + "Level" + SEPARATOR +
				"Expirence" + SEPARATOR + "Next Level";
		
		backgroundAndPersonality = "Background" + SEPARATOR + "Personality";
		
		//*****Stats****
		// Zero out Abilities
		statAbilScores = new int[6];
		statAbilMods = new int[6];
		for(int i = 0; i < 6; i++)
		{
			statAbilScores[i] = 10;
			statAbilMods[i] = 0;
		} 
		// Defenses
		defenses = new Defense[4];
		defenses[0] = new Defense("AC"); defenses[1] = new Defense("Fort");
		defenses[2] = new Defense("Ref"); defenses[3] = new Defense("Will");
		// Passives
		statInitScore = statInitDex = statInitHalfLevel = statInitMisc = statInitDieRoll = 0;
		statSpeedScore = statSpeedBase = statSpeedArmor = statSpeedItem = statSpeedMisc = 0;
		statInsightSkill = statPercepSkill = 0;
		statInsightScore = statPercepScore = 10;
		
		//*****HEALTH*****
		
		//*****SKILLS*****
				// set default values for scores(0), abilMods(0), miscMods(0), and trained(false);
				skills = new Skill[17];
				skills[0] = new Skill("Acrobatics", "Dex", 0);					skills[1] = new Skill("Arcana", "Int", Integer.MAX_VALUE);
				skills[2] = new Skill("Athletics", "Str", 0);					skills[3] = new Skill("Bluff", "Cha", Integer.MAX_VALUE);
				skills[4] = new Skill("Diplomacy", "Cha", Integer.MAX_VALUE);	skills[5] = new Skill("Dungeoneering", "Wis", Integer.MAX_VALUE);
				skills[6] = new Skill("Endurance", "Con", 0);					skills[7] = new Skill("Heal", "Wis", Integer.MAX_VALUE);
				skills[8] = new Skill("History", "Int", Integer.MAX_VALUE);		skills[9] = new Skill("Insight", "Wis", Integer.MAX_VALUE);
				skills[10] = new Skill("Intimidate", "Cha", Integer.MAX_VALUE);	skills[11] = new Skill("Nature", "Wis", Integer.MAX_VALUE);
				skills[12] = new Skill("Perception", "Wis", Integer.MAX_VALUE);	skills[13] = new Skill("Religion", "Int", Integer.MAX_VALUE);
				skills[14] = new Skill("Stealth", "Dex", 0);					skills[15] = new Skill("Streetwise", "Cha", Integer.MAX_VALUE);
				skills[16] = new Skill("Thievery", "Dex", 0);
				
		//*****FEATS*****
				
		//*****ATTACKS*****
				
		//*****EQUIPMENT*****
		
	}
}
