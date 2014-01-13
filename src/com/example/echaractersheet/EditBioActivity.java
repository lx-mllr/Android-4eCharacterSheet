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
import android.widget.Toast;

public class EditBioActivity extends Activity {
	
	public static final int REQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_bio);
		// Show the Up button in the action bar.
		setupActionBar();
		
		String bio, bgAndPersonality;
		Intent i = getIntent();
		bio = i.getStringExtra("Bio");
		bgAndPersonality = i.getStringExtra("Bg");
		if(bio != null || bgAndPersonality != null)
		{
			setEditViews(bio, bgAndPersonality);
		}
		else
			Toast.makeText(this, "Bio or BG is NULL in OnCreate", Toast.LENGTH_SHORT).show();
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
		getMenuInflater().inflate(R.menu.edit_bio, menu);
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

			saveBio();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed()
	{
		Log.d("CDA", "onBackPressed Called");
		
		saveBio();
	}
	
	private void setEditViews(String bio, String bg)
	{
		String[] data = bio.split(Character.SEPARATOR);
		String[] bgData = bg.split(Character.SEPARATOR);
		
		EditText nameView, titleView, raceView, levelView, expView, nextLevelView, classView, pathView, destinyView, genderView,
					ageView, sizeView, heightView, weightView, alignmentView, dietyView, 
					languagesView, backgroundView, personalityView;
		
		nameView = (EditText)findViewById(R.id.editName);
		nameView.setText(data[0]);
		titleView = (EditText)findViewById(R.id.editTitle);
		titleView.setText(data[1]);
		raceView = (EditText)findViewById(R.id.editRace);
		raceView.setText(data[2]);
		classView = (EditText)findViewById(R.id.editClass);
		classView.setText(data[3]);
		pathView = (EditText)findViewById(R.id.editPath);
		pathView.setText(data[4]);
		destinyView = (EditText)findViewById(R.id.editDestiny);
		destinyView.setText(data[5]);
		genderView = (EditText)findViewById(R.id.editGender);
		genderView.setText(data[6]);
		ageView = (EditText)findViewById(R.id.editAge);
		ageView.setText(data[7]);
		sizeView = (EditText)findViewById(R.id.editSize);
		sizeView.setText(data[8]);
		heightView = (EditText)findViewById(R.id.editHeight);
		heightView.setText(data[9]);
		weightView = (EditText)findViewById(R.id.editWeight);
		weightView.setText(data[10]);
		alignmentView = (EditText)findViewById(R.id.editAlignment);
		alignmentView.setText(data[11]);
		dietyView = (EditText)findViewById(R.id.editDeity);
		dietyView.setText(data[12]);
		languagesView = (EditText)findViewById(R.id.editLanguages);
		languagesView.setText(data[13]);
		levelView = (EditText)findViewById(R.id.editLevel);
		levelView.setText(data[14]);
		expView = (EditText)findViewById(R.id.editExp);
		expView.setText(data[15]);
		nextLevelView = (EditText)findViewById(R.id.editNextLevel);
		nextLevelView.setText(data[16]);
		
		backgroundView = (EditText)findViewById(R.id.editBackground);
		backgroundView.setText(bgData[0]);
		personalityView = (EditText)findViewById(R.id.editPersonality);
		personalityView.setText(bgData[1]);
	}
	
	public void saveBio()
	{
		// Get data out of Views
		String bio = "", bg = "";
		
		EditText nameView, titleView, raceView, levelView, expView, nextLevelView, classView, pathView, destinyView, genderView,
		ageView, sizeView, heightView, weightView, alignmentView, dietyView, 
		languagesView, backgroundView, personalityView;

		nameView = (EditText)findViewById(R.id.editName);
		bio += (nameView.getText().toString().trim().length() == 0) ? nameView.getHint() : nameView.getText();
		bio += Character.SEPARATOR;
		titleView = (EditText)findViewById(R.id.editTitle);
		bio += (titleView.getText().toString().trim().length() == 0) ? titleView.getHint() : titleView.getText();
		bio += Character.SEPARATOR;
		raceView = (EditText)findViewById(R.id.editRace);
		bio += (raceView.getText().toString().trim().length() == 0) ? raceView.getHint() : raceView.getText();
		bio += Character.SEPARATOR;
		classView = (EditText)findViewById(R.id.editClass);
		bio += (classView.getText().toString().trim().length() == 0) ? classView.getHint() : classView.getText();
		bio += Character.SEPARATOR;
		pathView = (EditText)findViewById(R.id.editPath);
		bio += (pathView.getText().toString().trim().length() == 0) ? pathView.getHint() : pathView.getText();
		bio += Character.SEPARATOR;
		destinyView = (EditText)findViewById(R.id.editDestiny);
		bio += (destinyView.getText().toString().trim().length() == 0) ? destinyView.getHint() : destinyView.getText();
		bio += Character.SEPARATOR;
		genderView = (EditText)findViewById(R.id.editGender);
		bio += (genderView.getText().toString().trim().length() == 0) ? genderView.getHint() : genderView.getText();
		bio += Character.SEPARATOR;
		ageView = (EditText)findViewById(R.id.editAge);
		bio += (ageView.getText().toString().trim().length() == 0) ? ageView.getHint() : ageView.getText();
		bio += Character.SEPARATOR;
		sizeView = (EditText)findViewById(R.id.editSize);
		bio += (sizeView.getText().toString().trim().length() == 0) ? sizeView.getHint() : sizeView.getText();
		bio += Character.SEPARATOR;
		heightView = (EditText)findViewById(R.id.editHeight);
		bio += (heightView.getText().toString().trim().length() == 0) ? heightView.getHint() : heightView.getText();
		bio += Character.SEPARATOR;
		weightView = (EditText)findViewById(R.id.editWeight);
		bio += (weightView.getText().toString().trim().length() == 0) ? weightView.getHint() : weightView.getText();
		bio += Character.SEPARATOR;
		alignmentView = (EditText)findViewById(R.id.editAlignment);
		bio += (alignmentView.getText().toString().trim().length() == 0) ? alignmentView.getHint() : alignmentView.getText();
		bio += Character.SEPARATOR;
		dietyView = (EditText)findViewById(R.id.editDeity);
		bio += (dietyView.getText().toString().trim().length() == 0) ? dietyView.getHint() : dietyView.getText();
		bio += Character.SEPARATOR;
		languagesView = (EditText)findViewById(R.id.editLanguages);
		bio += (languagesView.getText().toString().trim().length() == 0) ? languagesView.getHint() : languagesView.getText();
		bio += Character.SEPARATOR;
		levelView = (EditText)findViewById(R.id.editLevel);
		bio += (levelView.getText().toString().trim().length() == 0) ? levelView.getHint() : levelView.getText();
		bio += Character.SEPARATOR;
		expView = (EditText)findViewById(R.id.editExp);
		bio += (expView.getText().toString().trim().length() == 0) ? expView.getHint() : expView.getText();
		bio += Character.SEPARATOR;
		nextLevelView = (EditText)findViewById(R.id.editNextLevel);
		bio += (nextLevelView.getText().toString().trim().length() == 0) ? nextLevelView.getHint() : nextLevelView.getText();
		
		
		backgroundView = (EditText)findViewById(R.id.editBackground);
		bg += (backgroundView.getText().toString().trim().length() == 0) ? backgroundView.getHint() : backgroundView.getText();
		bg += Character.SEPARATOR;
		personalityView = (EditText)findViewById(R.id.editPersonality);
		bg += (personalityView.getText().toString().trim().length() == 0) ? personalityView.getHint() : personalityView.getText();
		
		// Put into intent for result
		Intent result = new Intent();
		result.putExtra("Bio", bio);
		result.putExtra("Bg", bg);
		setResult(Activity.RESULT_OK, result);
		finish();
	}

}
