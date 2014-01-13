package com.example.echaractersheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SkillsActivity extends ListActivity {

	public static final int REQUEST_CODE = 3;
	
	SimpleAdapter adapter;
	private ArrayList<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	private String[] from = { "Name", "Score" };
	private int[] to = { R.id.skillName, R.id.skillScore };
	
	private Skill[] skills;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_skills);
		// Show the Up button in the action bar.
		setupActionBar();
		

		Intent intent = getIntent();
		
		// Retrieve Values
		if(savedInstanceState == null)
		{
			Object[] skillObjs = (Object[]) intent.getSerializableExtra("Skills");
			skills = new Skill[skillObjs.length];
			for(int i = 0; i < skillObjs.length; i++)
			{
				skills[i] = (Skill) skillObjs[i];
			}
		}
		else
		{
			Object[] skillObjs = (Object[]) savedInstanceState.getSerializable("Skills");
			skills = new Skill[skillObjs.length];
			for(int i = 0; i < skillObjs.length; i++)
			{
				skills[i] = (Skill) skillObjs[i];
			}
		}
		// Populate ListView
		compileSkillData();
		
		adapter = new SkillsAdapter(this, listData, R.layout.listview_item_skill, from, to);
		setListAdapter(adapter);
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
		
		savedInstanceState.putSerializable("Skills", skills);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		
		Object[] skillObjs = (Object[]) savedInstanceState.getSerializable("Skills");
		skills = new Skill[skillObjs.length];
		for(int i = 0; i < skillObjs.length; i++)
		{
			skills[i] = (Skill) skillObjs[i];
		}
	}
	
	private void compileSkillData()
	{
		listData.clear();
		int numSkills = skills.length;
		for(int i = 0; i < numSkills; i++)
		{
			HashMap<String, String> item = new HashMap<String, String>();
			item.put("Name", skills[i].getName());
			item.put("Score", Integer.toString(skills[i].score));
			String bool = (skills[i].trained) ? "true" : "false";			// Probably a better way to do this but... 
			item.put("Trained", bool);
			listData.add(item);
		}
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
		getMenuInflater().inflate(R.menu.skills, menu);
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
		result.putExtra("Skills", skills);
		setResult(Activity.RESULT_OK, result);
		finish();	
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode == Activity.RESULT_OK)
		{
			switch(requestCode){
			case EditSkillActivity.REQUEST_CODE:
				int position = data.getIntExtra("Position", -1);
				if(position != -1)
				{
					skills[position] = (Skill) data.getSerializableExtra("Skill");
					
					compileSkillData();
					adapter.notifyDataSetChanged();
				}
				else
					Toast.makeText(this, "ERROR, POSITION NOT RETAINED", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		// Open Sub intent with appropriate information
		Intent intent = new Intent(this, EditSkillActivity.class);
		intent.putExtra("Skill", skills[position]);
		intent.putExtra("Position", position);
		startActivityForResult(intent, EditSkillActivity.REQUEST_CODE);
	}

}
