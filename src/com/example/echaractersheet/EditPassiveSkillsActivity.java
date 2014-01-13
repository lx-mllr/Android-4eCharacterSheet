package com.example.echaractersheet;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class EditPassiveSkillsActivity extends Activity {
	
	public static final int REQUEST_CODE = 10;
	
	private String name;
	private int skill;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_passive_skills);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent data = getIntent();
		name = data.getStringExtra("Name");
		skill = data.getIntExtra("Skill", 0);
		
		TextView nameView;
		EditText editSkill;
		
		nameView = (TextView) findViewById(R.id.editSkillsName);
		nameView.setText(name);
		editSkill = (EditText) findViewById(R.id.editSkillsSkillBonus);
		editSkill.setText(Integer.toString(skill));
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
		getMenuInflater().inflate(R.menu.edit_passive_skills, menu);
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
		EditText editSkill;
		
		editSkill = (EditText) findViewById(R.id.editSkillsSkillBonus);
		skill = (editSkill.getText().length() == 0) ? 0 : Integer.parseInt(editSkill.getText().toString());
		
		result.putExtra("Name", name);
		result.putExtra("Skill", skill);
		setResult(Activity.RESULT_OK, result);
		finish();
	}

}
