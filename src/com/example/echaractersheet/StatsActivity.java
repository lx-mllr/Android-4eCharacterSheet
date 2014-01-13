package com.example.echaractersheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

public class StatsActivity extends ExpandableListActivity {
	
	public static final int REQUEST_CODE = 5;
	
	//	Ability Scores
	public String[] statAbilNames, statAbilSubHeadings;
	public int[] statAbilScores, statAbilMods;
	//			Defenses
	public Defense[] defenses;
	//			Passive Abilities
	public int statInitScore, statInitDex, statInitHalfLevel, statInitMisc, statInitDieRoll;
	public int statSpeedScore, statSpeedBase, statSpeedArmor, statSpeedItem, statSpeedMisc;
	public int statInsightScore, statInsightSkill;
	public int statPercepScore, statPercepSkill;
	
	// Adapter Data
	private SimpleExpandableListAdapter adapter;
	
	private ArrayList<Map<String,String>> groupData = new ArrayList<Map<String, String>>();
	private final String[] groupFrom = { "Name" };
	private final int[] groupTo = { R.id.statGroupName };
	
	private ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<ArrayList<Map<String, String>>>();
	private final String[] childFrom = { "Name", "SubHeading", "Score", 
											"ScoreLabel", "Mod", "ModLabel" };
	private final int[] childTo = { R.id.statChildName, R.id.statChildSubHeading, R.id.statChildScore,
									R.id.statChildScoreLabel, R.id.statChildMod, R.id.statChildModLabel };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent data = getIntent();
		statAbilNames = data.getStringArrayExtra("AbilNames");
		statAbilSubHeadings = data.getStringArrayExtra("AbilSubHeadings");
		
		if(savedInstanceState == null)
		{			
			// ABILITY SCORES
			statAbilScores = data.getIntArrayExtra("AbilScores");
			statAbilMods = data.getIntArrayExtra("AbilMods");
			// DEFENSES
			Object[] defObjs = (Object[])data.getSerializableExtra("Defenses");
			defenses = new Defense[defObjs.length];
			for(int i = 0; i < defObjs.length; i++)
			{
				defenses[i] = (Defense) defObjs[i];
			}
			// PASSIVE ABILITIES
			statInitScore = data.getIntExtra("InitScore", 0);	statInitDex = data.getIntExtra("InitDex", 0);
			statInitHalfLevel = data.getIntExtra("InitHalfLevel", 0);	statInitMisc = data.getIntExtra("InitMisc", 0);	statInitDieRoll = data.getIntExtra("IntDieRoll", 0);
			statSpeedScore = data.getIntExtra("SpeedScore", 0); 	statSpeedBase = data.getIntExtra("SpeedBase", 0);
			statSpeedArmor = data.getIntExtra("SpeedArmor", 0); 	statSpeedItem = data.getIntExtra("SpeedItem", 0);	statSpeedMisc = data.getIntExtra("SpeedMisc", 0);
			statInsightScore = data.getIntExtra("InsightScore", 0);	statInsightSkill = data.getIntExtra("InsightSkill", 0);
			statPercepScore = data.getIntExtra("PercepScore", 0);	statPercepSkill = data.getIntExtra("PercepSkill", 0);
		}
		else
		{
			// ABILITY SCORES
			statAbilScores = savedInstanceState.getIntArray("AbilScores");
			statAbilMods = savedInstanceState.getIntArray("AbilMods");
			// DEFENSES
			Object[] defObjs = (Object[])savedInstanceState.getSerializable("Defenses");
			defenses = new Defense[defObjs.length];
			for(int i = 0; i < defObjs.length; i++)
			{
				defenses[i] = (Defense) defObjs[i];
			}
			// PASSIVE ABILITIES
			statInitScore = savedInstanceState.getInt("InitScore", 0);	statInitDex = savedInstanceState.getInt("InitDex", 0);
			statInitHalfLevel = savedInstanceState.getInt("InitHalfLevel", 0);	statInitMisc = savedInstanceState.getInt("InitMisc", 0);
			statSpeedScore = savedInstanceState.getInt("SpeedScore", 0); 	statSpeedBase = savedInstanceState.getInt("SpeedBase", 0);
			statSpeedArmor = savedInstanceState.getInt("SpeedArmor", 0); 	statSpeedItem = savedInstanceState.getInt("SpeedItem", 0);	statSpeedMisc = savedInstanceState.getInt("SpeedMisc", 0);
			statInsightScore = savedInstanceState.getInt("InsightScore", 0);	statInsightSkill = savedInstanceState.getInt("InsightSkill", 0);
			statPercepScore = savedInstanceState.getInt("PercepScore", 0);	statPercepSkill = savedInstanceState.getInt("PercepSkill", 0);
		}
		
		// Populate the data
		compileStatData();
		adapter = new SimpleExpandableListAdapter(this, groupData, R.layout.expandable_listview_group_item_stats, groupFrom, groupTo,
													childData, R.layout.expandable_listview_child_item_stats, childFrom, childTo);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
		
		savedInstanceState.putIntArray("AbilScores", statAbilScores);
		savedInstanceState.putIntArray("AbilMods", statAbilMods);
		// DEFENSES
		savedInstanceState.putSerializable("Defenses", defenses);
		// PASSIVE ABILITIES
		savedInstanceState.putInt("InitScore", statInitScore);	savedInstanceState.putInt("InitDex", statInitDex);
		savedInstanceState.putInt("InitHalfLevel", statInitHalfLevel);	savedInstanceState.putInt("InitMisc", statInitMisc);	savedInstanceState.putInt("InitDieRoll", statInitDieRoll);
		savedInstanceState.putInt("SpeedScore", statSpeedScore); 	savedInstanceState.putInt("SpeedBase", statSpeedBase);
		savedInstanceState.putInt("SpeedArmor", statSpeedArmor); 	savedInstanceState.putInt("SpeedItem", statSpeedItem);	savedInstanceState.putInt("SpeedMisc", statSpeedMisc);
		savedInstanceState.putInt("InsightScore", statInsightScore);	savedInstanceState.putInt("InsightSkill", statInsightSkill);
		savedInstanceState.putInt("PercepScore", statPercepScore);	savedInstanceState.putInt("PercepSkill", statPercepSkill);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		
		// ABILITY SCORES
		statAbilScores = savedInstanceState.getIntArray("AbilScores");
		statAbilMods = savedInstanceState.getIntArray("AbilMods");
		// DEFENSES
		Object[] defObjs = (Object[])savedInstanceState.getSerializable("Defenses");
		defenses = new Defense[defObjs.length];
		for(int i = 0; i < defObjs.length; i++)
		{
			defenses[i] = (Defense) defObjs[i];
		}
		// PASSIVE ABILITIES
		statInitScore = savedInstanceState.getInt("InitScore", 0);	statInitDex = savedInstanceState.getInt("InitDex", 0);
		statInitHalfLevel = savedInstanceState.getInt("InitHalfLevel", 0);	statInitMisc = savedInstanceState.getInt("InitMisc", 0);	statInitDieRoll = savedInstanceState.getInt("InitDieRoll");
		statSpeedScore = savedInstanceState.getInt("SpeedScore", 0); 	statSpeedBase = savedInstanceState.getInt("SpeedBase", 0);
		statSpeedArmor = savedInstanceState.getInt("SpeedArmor", 0); 	statSpeedItem = savedInstanceState.getInt("SpeedItem", 0);	statSpeedMisc = savedInstanceState.getInt("SpeedMisc", 0);
		statInsightScore = savedInstanceState.getInt("InsightScore", 0);	statInsightSkill = savedInstanceState.getInt("InsightSkill", 0);
		statPercepScore = savedInstanceState.getInt("PercepScore", 0);	statPercepSkill = savedInstanceState.getInt("PercepSkill", 0);
	}
	
	private void compileStatData()
	{
		// Populate Group Data
		groupData.clear();
		HashMap<String, String> ab, def, pass;
		ab = new HashMap<String, String>();
		ab.put("Name", "Ability Scores");
		def = new HashMap<String, String>();
		def.put("Name", "Defenses");
		pass = new HashMap<String, String>();
		pass.put("Name", "Passive Stats");
		groupData.add(ab);
		groupData.add(def);
		groupData.add(pass);
		
		// Populate Child Data;
		childData.clear();
		//		Ability Scores
		ArrayList<Map<String, String>> abilScoresData = new ArrayList<Map<String, String>>();
		for(int i = 0; i < statAbilNames.length; i++)
		{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Name", statAbilNames[i]);
			map.put("SubHeading", statAbilSubHeadings[i]);
			map.put("Score", Integer.toString(statAbilScores[i]));
			map.put("ScoreLabel", "Score");
			map.put("Mod", Integer.toString(statAbilMods[i]));
			map.put("ModLabel", "Abil Mod");
			abilScoresData.add(map);
		}
		childData.add(abilScoresData);
		
		//		Defenses
		ArrayList<Map<String, String>> defensesData = new ArrayList<Map<String, String>>();
		for(int i = 0; i < defenses.length; i++)
		{
			HashMap<String,String> map = new HashMap<String, String>();
			map.put("Name", defenses[i].getName());
			map.put("SubHeading", defenses[i].conditionals);
			map.put("Score", Integer.toString(defenses[i].score));
			map.put("ScoreLabel", "Score");
			map.put("Mod", " ");					// Leave blank for this group
			map.put("ModLabel", " ");
			defensesData.add(map);
		}
		childData.add(defensesData);
		
		// 		Passives
		ArrayList<Map<String, String>> passivesData = new ArrayList<Map<String, String>>();
		HashMap<String, String> initData, movementData, passInsightData, passPercepData;
		
		initData = new HashMap<String, String>();
		initData.put("Name", "Initiative");	initData.put("Score", Integer.toString(statInitScore));	initData.put("ScoreLabel", "Score");
		initData.put("Mod", Integer.toString(statInitDieRoll));	initData.put("ModLabel", "D20 Roll");
		initData.put("SubHeading", " ");
		
		movementData = new HashMap<String, String>();
		movementData.put("Name", "Movement");	movementData.put("Score", Integer.toString(statSpeedScore));	movementData.put("ScoreLabel", "Score");
		movementData.put("SubHeading", " ");	movementData.put("Mod", " ");	movementData.put("ModLabel", " ");
		
		passInsightData = new HashMap<String, String>();
		passInsightData.put("Name", "Insight");	passInsightData.put("Score", Integer.toString(statInsightScore));	passInsightData.put("ScoreLabel", "Score");
		passInsightData.put("SubHeading", "Passive");	passInsightData.put("Mod", " ");	passInsightData.put("ModLabel", " ");
		
		passPercepData = new HashMap<String, String>();
		passPercepData.put("Name", "Perception");	passPercepData.put("Score", Integer.toString(statPercepScore));	passPercepData.put("ScoreLabel", "Score");
		passPercepData.put("SubHeading", "Passive");	passPercepData.put("Mod", " ");	passPercepData.put("ModLabel", " ");
		
		passivesData.add(initData);
		passivesData.add(movementData);
		passivesData.add(passInsightData);
		passivesData.add(passPercepData);
		
		childData.add(passivesData);
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			onFinish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed()
	{
		Log.d("CDA", "onBackPressed Called");
		
		onFinish();
	}
	
	private void onFinish()
	{
		Intent result = new Intent();
		// ABILITY SCORES
		result.putExtra("AbilScores", statAbilScores);
		result.putExtra("AbilMods", statAbilMods);
		// DEFENSES
		result.putExtra("Defenses", defenses);
		// PASSIVE ABILITIES
		result.putExtra("InitScore", statInitScore);	result.putExtra("InitDex", statInitDex);
		result.putExtra("InitHalfLevel", statInitHalfLevel);	result.putExtra("InitMisc", statInitMisc);	result.putExtra("InitDieRoll", statInitDieRoll);
		result.putExtra("SpeedScore", statSpeedScore); 	result.putExtra("SpeedBase", statSpeedBase);
		result.putExtra("SpeedArmor", statSpeedArmor); 	result.putExtra("SpeedItem", statSpeedItem);	result.putExtra("SpeedMisc", statSpeedMisc);
		result.putExtra("InsightScore", statInsightScore);	result.putExtra("InsightSkill", statInsightSkill);
		result.putExtra("PercepScore", statPercepScore);	result.putExtra("PercepSkill", statPercepSkill);
		setResult(Activity.RESULT_OK, result);
		finish();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
	{
		Intent intent;
		if(groupPosition == 0)		// Ability Scores
		{
			intent = new Intent(this, EditAbilityScoreActivity.class);
			switch(childPosition){
			case 0:	// Str
				intent.putExtra("Position", 0);
				intent.putExtra("Name", statAbilNames[0]);
				intent.putExtra("SubHeading", statAbilSubHeadings[0]);
				intent.putExtra("Score", statAbilScores[0]);
				intent.putExtra("Mod", statAbilMods[0]);
				break;
			case 1:	// Con
				intent.putExtra("Position", 1);
				intent.putExtra("Name", statAbilNames[1]);
				intent.putExtra("SubHeading", statAbilSubHeadings[1]);
				intent.putExtra("Score", statAbilScores[1]);
				intent.putExtra("Mod", statAbilMods[1]);
				break;
			case 2: // Dex
				intent.putExtra("Position", 2);
				intent.putExtra("Name", statAbilNames[2]);
				intent.putExtra("SubHeading", statAbilSubHeadings[2]);
				intent.putExtra("Score", statAbilScores[2]);
				intent.putExtra("Mod", statAbilMods[2]);
				break;
			case 3: // Int
				intent.putExtra("Position", 3);
				intent.putExtra("Name", statAbilNames[3]);
				intent.putExtra("SubHeading", statAbilSubHeadings[3]);
				intent.putExtra("Score", statAbilScores[3]);
				intent.putExtra("Mod", statAbilMods[3]);
				break;
			case 4: // Wis
				intent.putExtra("Position", 4);
				intent.putExtra("Name", statAbilNames[4]);
				intent.putExtra("SubHeading", statAbilSubHeadings[4]);
				intent.putExtra("Score", statAbilScores[4]);
				intent.putExtra("Mod", statAbilMods[4]);
				break;
			case 5: // Cha
				intent.putExtra("Position", 5);
				intent.putExtra("Name", statAbilNames[5]);
				intent.putExtra("SubHeading", statAbilSubHeadings[5]);
				intent.putExtra("Score", statAbilScores[5]);
				intent.putExtra("Mod", statAbilMods[5]);
				break;
			}
			startActivityForResult(intent, EditAbilityScoreActivity.REQUEST_CODE);
		}
		else if(groupPosition == 1)	// Defenses
		{
			intent = new Intent(this, EditDefenseActivity.class);
			intent.putExtra("Position", childPosition);
			intent.putExtra("Defense", defenses[childPosition]);
			startActivityForResult(intent, EditDefenseActivity.REQUEST_CODE);
		}
		else if(groupPosition == 2)	// Passives
		{
			switch(childPosition){
			case 0: // Initiative
				intent = new Intent(this, EditInitiativeActivity.class);
				intent.putExtra("Dex", statInitDex);
				intent.putExtra("HalfLevel", statInitHalfLevel);
				intent.putExtra("Misc", statInitMisc);
				intent.putExtra("DieRoll", statInitDieRoll);
				startActivityForResult(intent, EditInitiativeActivity.REQUEST_CODE);
				break;
			case 1: // Movement
				intent = new Intent(this, EditMovementActivity.class);
				intent.putExtra("Base", statSpeedBase);
				intent.putExtra("Armor", statSpeedArmor);
				intent.putExtra("Item", statSpeedItem);
				intent.putExtra("Misc", statSpeedMisc);
				startActivityForResult(intent, EditMovementActivity.REQUEST_CODE);
				break;
			case 2: // Insight
				intent = new Intent(this, EditPassiveSkillsActivity.class);
				intent.putExtra("Name", "Passive Insight");
				intent.putExtra("Skill", statInsightSkill);
				startActivityForResult(intent, EditPassiveSkillsActivity.REQUEST_CODE);
				break;
			case 3: // Perception
				intent = new Intent(this, EditPassiveSkillsActivity.class);
				intent.putExtra("Name", "Passive Perception");
				intent.putExtra("Skill", statPercepSkill);
				startActivityForResult(intent, EditPassiveSkillsActivity.REQUEST_CODE);
				break;
			}
		}
		
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode == Activity.RESULT_OK)
		{
			int position;
			switch(requestCode)
			{
			case EditAbilityScoreActivity.REQUEST_CODE:
				position = data.getIntExtra("Position", -1);
				if(position != -1)
				{
					statAbilScores[position] = data.getIntExtra("Score", 0);
					statAbilMods[position] = data.getIntExtra("Mod", 0);
				}
				else
					Toast.makeText(this, "Position Lost", Toast.LENGTH_SHORT).show();
				break;
			case EditDefenseActivity.REQUEST_CODE:
				position = data.getIntExtra("Position", -1);
				if(position != -1)
				{
					defenses[position] = (Defense) data.getSerializableExtra("Defense");
				}
				else
					Toast.makeText(this, "Position Lost", Toast.LENGTH_SHORT).show();
				break;
			case EditInitiativeActivity.REQUEST_CODE:
				statInitDex = data.getIntExtra("Dex", 0);
				statInitHalfLevel = data.getIntExtra("HalfLevel", 0);
				statInitMisc = data.getIntExtra("Misc", 0);
				statInitDieRoll = data.getIntExtra("DieRoll", 0);
				statInitScore = statInitDex + statInitHalfLevel + statInitMisc;
				break;
			case EditMovementActivity.REQUEST_CODE:
				statSpeedBase = data.getIntExtra("Base", 0);
				statSpeedArmor = data.getIntExtra("Armor", 0);
				statSpeedItem = data.getIntExtra("Item", 0);
				statSpeedMisc = data.getIntExtra("Misc", 0);
				statSpeedScore = statSpeedBase + statSpeedArmor + statSpeedItem + statSpeedMisc;
				break;
			case EditPassiveSkillsActivity.REQUEST_CODE:
				String name = data.getStringExtra("Name");
				if(name.equals("Passive Insight")) 		// Insight
				{
					statInsightSkill = data.getIntExtra("Skill", 0);
					statInsightScore = statInsightSkill + 10;
				}
				else if(name.equals("Passive Perception"))	// Perception
				{
					statPercepSkill = data.getIntExtra("Skill", 0);
					statPercepScore = statPercepSkill + 10;
				}
				else
					Toast.makeText(this, "Position Lost", Toast.LENGTH_SHORT).show();
				break;
			}
		}
		
		compileStatData();
		adapter.notifyDataSetChanged();
	}
}
