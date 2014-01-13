package com.example.echaractersheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CharacterActivity extends ListActivity {
	
	// The character in question
	private Character character;
	
	// Is there a better way to organize this data?
	private final int BIO_POS = 0;
	private final int STATS_POS = 1;
	private final int HEALTH_POS = 2;
	private final int SKILLS_POS = 3;
	private final int FEATS_POS = 4;
	private final int ATTACKS_POS = 5;
	private final int EQUIPMENT_POS = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character);
		// Show the Up button in the action bar.
		setupActionBar();
		
		if(savedInstanceState == null)
		{
			Intent i = getIntent();
			character = (Character) i.getSerializableExtra("Character");
		}
		else
		{
			character = (Character) savedInstanceState.getSerializable("Character");
		}
		String[] values = new String[]{ "Biography", "Stats", "Health", "Skills", "Feats",
				"Attacks", "Equipment"	};
		
		ListView lv = getListView();
		lv.addFooterView(getLayoutInflater().inflate(R.layout.listview_footer_character, null));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putSerializable("Character", character);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		character = (Character) savedInstanceState.getSerializable("Character");
		
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
		getMenuInflater().inflate(R.menu.character, menu);
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
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch(requestCode){
		case BioActivity.REQUEST_CODE:
			if(resultCode == Activity.RESULT_OK)
			{
				character.bio = data.getStringExtra("Bio");
				character.backgroundAndPersonality = data.getStringExtra("Bg");
			}
			break;
		case StatsActivity.REQUEST_CODE:
			if(resultCode == Activity.RESULT_OK)
			{
				// ABILITY SCORES
				character.statAbilScores = data.getIntArrayExtra("AbilScores");
				character.statAbilMods = data.getIntArrayExtra("AbilMods");
				// DEFENSES
				Object[] defObjs = (Object[]) data.getSerializableExtra("Defenses");
				character.defenses = new Defense[defObjs.length];
				for(int i = 0; i < defObjs.length; i++)
				{
					character.defenses[i] = (Defense) defObjs[i];
				}
				// PASSIVE ABILITIES
				character.statInitScore = data.getIntExtra("InitScore", 0);	character.statInitDex = data.getIntExtra("InitDex", 0);
				character.statInitHalfLevel = data.getIntExtra("InitHalfLevel", 0);	character.statInitMisc = data.getIntExtra("InitMisc", 0);	character.statInitDieRoll = data.getIntExtra("InitDieRoll", 0);
				character.statSpeedScore = data.getIntExtra("SpeedScore", 0); 	character.statSpeedBase = data.getIntExtra("SpeedBase", 0);
				character.statSpeedArmor = data.getIntExtra("SpeedArmor", 0); 	character.statSpeedItem = data.getIntExtra("SpeedItem", 0);	character.statSpeedMisc = data.getIntExtra("SpeedMisc", 0);
				character.statInsightScore = data.getIntExtra("InsightScore", 0);	character.statInsightSkill = data.getIntExtra("InsightSkill", 0);
				character.statPercepScore = data.getIntExtra("PercepScore", 0);	character.statPercepSkill = data.getIntExtra("PercepSkill", 0);
			}
			break;
		case SkillsActivity.REQUEST_CODE:
			if(resultCode == Activity.RESULT_OK)
			{
				Object[] skillObjs = (Object[]) data.getSerializableExtra("Skills");
				character.skills = new Skill[skillObjs.length];
				for(int i = 0; i < skillObjs.length; i++)
				{
					character.skills[i] = (Skill) skillObjs[i];
				}
			}
			break;
		default:
			Toast.makeText(this, "Request Code not found", Toast.LENGTH_SHORT).show();
			break;
		}
			
	}
	
	public void saveCharacter(View view)
	{
		try
		{
			File file = new File(getFilesDir(), "Character" + Integer.toString(character.getId()) + ".4ech");
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			// Yay serializable objects!
			oos.writeObject(character);
			
			oos.close();		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		// Show Intents
		Intent intent;
		switch(position) {
		case BIO_POS:
			intent = new Intent(this, BioActivity.class);
			intent.putExtra("Bio", character.bio);											// Send the Bio
			intent.putExtra("bgAndPersonality", character.backgroundAndPersonality);		// Send the BG
			startActivityForResult(intent, BioActivity.REQUEST_CODE);
			break;
		case STATS_POS:
			intent = new Intent(this, StatsActivity.class);
			// ABILITY SCORES
			intent.putExtra("AbilNames", character.statAbilNames);
			intent.putExtra("AbilSubHeadings", character.statAbilSubHeadings);
			intent.putExtra("AbilScores", character.statAbilScores);
			intent.putExtra("AbilMods", character.statAbilMods);
			// DEFENSES
			intent.putExtra("Defenses", character.defenses);
			// PASSIVE ABILITIES
			intent.putExtra("PassiveNames", character.statPassiveNames);
			intent.putExtra("InitScore", character.statInitScore);	intent.putExtra("InitDex", character.statInitDex);
			intent.putExtra("InitHalfLevel", character.statInitHalfLevel);	intent.putExtra("InitMisc", character.statInitMisc);	intent.putExtra("IntDieRoll", character.statInitDieRoll);
			intent.putExtra("SpeedScore", character.statSpeedScore); 	intent.putExtra("SpeedBase", character.statSpeedBase);
			intent.putExtra("SpeedArmor", character.statSpeedArmor); 	intent.putExtra("SpeedItem", character.statSpeedItem);	intent.putExtra("SpeedMisc", character.statSpeedMisc);
			intent.putExtra("InsightScore", character.statInsightScore);	intent.putExtra("InsightSkill", character.statInsightSkill);
			intent.putExtra("PercepScore", character.statPercepScore);	intent.putExtra("PercepSkill", character.statPercepSkill);
			startActivityForResult(intent, StatsActivity.REQUEST_CODE);
			break;
		case HEALTH_POS:
			// SHow Health Intent
			break;
		case SKILLS_POS:
			intent = new Intent(this, SkillsActivity.class);
			intent.putExtra("Skills", character.skills);
			startActivityForResult(intent, SkillsActivity.REQUEST_CODE);
			break;
		case FEATS_POS:
			// Show Features Intent
			Toast.makeText(this, "Feats", Toast.LENGTH_SHORT).show();
			break;
		case ATTACKS_POS:
			// Show Attacks Intent
			Toast.makeText(this, "Attacks", Toast.LENGTH_SHORT).show();
			break;
		case EQUIPMENT_POS:
			// Show Equipment Intent
			Toast.makeText(this, "Equipment", Toast.LENGTH_SHORT).show();
			break;
		default:
			Toast.makeText(this, "Not sure what you clicked on...\nID: " + position, Toast.LENGTH_SHORT).show();
		}
	}

}
