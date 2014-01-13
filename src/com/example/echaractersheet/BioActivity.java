package com.example.echaractersheet;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class BioActivity extends Activity {
	
	public static final int REQUEST_CODE = 2;
	
	// See Character.java for explanation of what this represents
	private String bio;
	private String backgroundAndPersonality;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bio);
		// Show the Up button in the action bar.
		setupActionBar();
		
		if(savedInstanceState == null)
		{
			Intent i = getIntent();
			bio = i.getStringExtra("Bio");
			backgroundAndPersonality = i.getStringExtra("bgAndPersonality");
		}
		else
		{
			bio = savedInstanceState.getString("Bio");
			backgroundAndPersonality = savedInstanceState.getString("bgAndPersonality");
		}
		populateTextViews();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
		
		savedInstanceState.putString("Bio", bio);
		savedInstanceState.putString("bgAndPersonality", backgroundAndPersonality);
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
		getMenuInflater().inflate(R.menu.bio, menu);
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
	
	// Unified Finish method for both back and up
	private void onFinish()
	{
		Intent result = new Intent();
		result.putExtra("Bio", bio);
		result.putExtra("Bg", backgroundAndPersonality);
		setResult(Activity.RESULT_OK, result);
		finish();	
	}
	
	// Populate the TextViews with character data
	private void populateTextViews()
	{
		String[] data = bio.split(Character.SEPARATOR);
		String[] bgData = backgroundAndPersonality.split(Character.SEPARATOR);
		
		TextView nameView, titleView, raceView, levelView, expView, classView, pathView, destinyView, genderView,
					ageView, sizeView, heightView, weightView, alignmentView, dietyView, 
					languagesView, backgroundView, personalityView;
		
		// Find each view and set the text
		nameView = (TextView)findViewById(R.id.name);
		nameView.setText(data[0]);
		titleView = (TextView)findViewById(R.id.title);
		titleView.setText(data[1]);
		raceView = (TextView)findViewById(R.id.race);
		raceView.setText(data[2]);
		classView = (TextView)findViewById(R.id.clss);
		classView.setText(data[3]);
		pathView = (TextView)findViewById(R.id.path);
		pathView.setText(data[4]);
		destinyView = (TextView)findViewById(R.id.destiny);
		destinyView.setText(data[5]);
		genderView = (TextView)findViewById(R.id.gender);
		genderView.setText(data[6]);
		ageView = (TextView)findViewById(R.id.age);
		ageView.setText("Age " + data[7]);
		sizeView = (TextView)findViewById(R.id.size);
		sizeView.setText(data[8] + " sized");
		heightView = (TextView)findViewById(R.id.height);
		heightView.setText(data[9] + " tall");
		weightView = (TextView)findViewById(R.id.weight);
		weightView.setText(data[10]);
		alignmentView = (TextView)findViewById(R.id.alignment);
		alignmentView.setText(data[11]);
		dietyView = (TextView)findViewById(R.id.diety);
		dietyView.setText("Follower of " + data[12]);
		languagesView = (TextView)findViewById(R.id.languages);
		languagesView.setText("Speaks " + data[13]);
		levelView = (TextView)findViewById(R.id.levelView);
		levelView.setText("Level: " + data[14]);
		expView = (TextView)findViewById(R.id.expView);
		expView.setText(data[15] + " / " + data[16]);
		
		
		backgroundView = (TextView)findViewById(R.id.background);
		backgroundView.setText(bgData[0]);
		personalityView = (TextView)findViewById(R.id.personality);
		personalityView.setText(bgData[1]);
	}
	
	public void startEditBio(MenuItem item)
	{
		// pass in bio and bg
		Intent intent = new Intent(this, EditBioActivity.class);
		intent.putExtra("Bio", bio);
		intent.putExtra("Bg", backgroundAndPersonality);
		// Start edit activity
		startActivityForResult(intent, EditBioActivity.REQUEST_CODE);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch(requestCode){
		case EditBioActivity.REQUEST_CODE:
			if(resultCode == Activity.RESULT_OK)
			{
				// Get data back out
				bio = data.getStringExtra("Bio");
				backgroundAndPersonality = data.getStringExtra("Bg");
				populateTextViews();
			}
			break;
		}
	}

}
