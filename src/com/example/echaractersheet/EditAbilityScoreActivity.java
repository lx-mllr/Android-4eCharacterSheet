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

public class EditAbilityScoreActivity extends Activity {
	
	public static final int REQUEST_CODE = 6;
	
	private String name, subheading;
	private int position, score, mod;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_ability_score_activty);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent data = getIntent();
		position = data.getIntExtra("Position", -1);
		name = data.getStringExtra("Name");
		subheading = data.getStringExtra("SubHeading");
		score = data.getIntExtra("Score", 0);
		mod = data.getIntExtra("Mod", 0);
		
		TextView abilName, abilSubHeading;
		EditText editScore, editMod;
		
		abilName = (TextView) findViewById(R.id.editAbilScoreName);
		abilName.setText(name);
		abilSubHeading = (TextView) findViewById(R.id.editAbilScoreSubHeading);
		abilSubHeading.setText(subheading);
		editScore = (EditText) findViewById(R.id.editAbilScore);
		editScore.setText(Integer.toString(score));
		editMod = (EditText) findViewById(R.id.editAbilMod);
		editMod.setText(Integer.toString(mod));
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
		getMenuInflater().inflate(R.menu.edit_ability_score_activty, menu);
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
		EditText editScore, editMod;

		editScore = (EditText) findViewById(R.id.editAbilScore);
		score = (editScore.getText().length() == 0) ? 0 : Integer.parseInt(editScore.getText().toString());
		editMod = (EditText) findViewById(R.id.editAbilMod);
		mod = (editMod.getText().length() == 0) ? 0 : Integer.parseInt(editMod.getText().toString());
		
		Intent result = new Intent();
		result.putExtra("Position", position);
		result.putExtra("Score", score);
		result.putExtra("Mod", mod);
		setResult(Activity.RESULT_OK, result);
		finish();
	}

}
