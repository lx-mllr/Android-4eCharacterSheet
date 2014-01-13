package com.example.echaractersheet;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class EditSkillActivity extends Activity {
	
	public static final int REQUEST_CODE = 4;
	
	private Skill skill;
	
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_skill);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Retrieve values
		Intent intent = getIntent();
		skill = (Skill) intent.getSerializableExtra("Skill");
		position = intent.getIntExtra("Position", -1);
		
		// Set values
		TextView skillName, skillAbilName;
		EditText editViewAbil, editViewArmor, editViewMisc;
		CheckBox traind;
		
		skillName = (TextView) findViewById(R.id.editSkillName);
		skillName.setText(skill.getName());
		skillAbilName = (TextView) findViewById(R.id.editAbilName);
		skillAbilName.setText(skill.getAbilName());
		
		traind = (CheckBox) findViewById(R.id.trainedCB);
		if(skill.trained)	traind.setChecked(true);
		
		editViewAbil = (EditText) findViewById(R.id.editAbilMod);
		editViewAbil.setText(Integer.toString(skill.abilMod));
		editViewMisc = (EditText) findViewById(R.id.editMiscMod);
		editViewMisc.setText(Integer.toString(skill.miscMod));
		

		editViewArmor = (EditText) findViewById(R.id.editArmorPen);
		if(skill.armorPen == Integer.MAX_VALUE)
		{
			editViewArmor.setFocusable(false);
			editViewArmor.setEnabled(false);
		}
		else
		{
			editViewArmor.setText(Integer.toString(skill.armorPen));
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
		getMenuInflater().inflate(R.menu.edit_skill, menu);
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
		// Get data back
		EditText editViewAbil, editViewArmor, editViewMisc;
		CheckBox traind;
		
		traind = (CheckBox) findViewById(R.id.trainedCB);
		skill.trained = traind.isChecked();
		
		// If view is empty, send back 0		
		editViewAbil = (EditText) findViewById(R.id.editAbilMod);
		skill.abilMod = (editViewAbil.getText().length() == 0) ? 0 : Integer.parseInt(editViewAbil.getText().toString());
		editViewMisc = (EditText) findViewById(R.id.editMiscMod);
		skill.miscMod = (editViewMisc.getText().length() == 0) ? 0 : Integer.parseInt(editViewMisc.getText().toString());		
		
		if(skill.armorPen != Integer.MAX_VALUE)
		{
			editViewArmor = (EditText) findViewById(R.id.editArmorPen);
			skill.armorPen = (editViewArmor.getText().length() == 0) ? 0 : Integer.parseInt(editViewArmor.getText().toString());
		}
		
		Intent result = new Intent();
		result.putExtra("Position", position);
		result.putExtra("Skill", skill);
		setResult(Activity.RESULT_OK, result);
		finish();	
	}

}
