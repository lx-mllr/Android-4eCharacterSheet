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

public class EditInitiativeActivity extends Activity {
	
	public static final int REQUEST_CODE = 8;
	
	private int dex, halfLevel, misc, dieRoll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_initiative);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent data = getIntent();
		dex = data.getIntExtra("Dex", 0);
		halfLevel = data.getIntExtra("HalfLevel", 0);
		misc = data.getIntExtra("Misc", 0);
		dieRoll = data.getIntExtra("DieRoll", 0);
		
		EditText editDex, editHalfLevel, editMisc, editDieRoll;
		editDex = (EditText) findViewById(R.id.editInitDexMod);
		editDex.setText(Integer.toString(dex));
		editHalfLevel = (EditText) findViewById(R.id.editInitHalfLevel);
		editHalfLevel.setText(Integer.toString(halfLevel));
		editMisc = (EditText) findViewById(R.id.editInitMiscMod);
		editMisc.setText(Integer.toString(misc));
		editDieRoll = (EditText) findViewById(R.id.editInitDieRoll);
		editDieRoll.setText(Integer.toString(dieRoll));
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
		getMenuInflater().inflate(R.menu.edit_initiative, menu);
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
		EditText editDex, editHalfLevel, editMisc, editDieRoll;
		
		editDex = (EditText) findViewById(R.id.editInitDexMod);
		dex = (editDex.getText().length() == 0) ? 0 : Integer.parseInt(editDex.getText().toString());
		editHalfLevel = (EditText) findViewById(R.id.editInitHalfLevel);
		halfLevel = (editHalfLevel.getText().length() == 0) ? 0 : Integer.parseInt(editHalfLevel.getText().toString());
		editMisc = (EditText) findViewById(R.id.editInitMiscMod);
		misc = (editMisc.getText().length() == 0) ? 0 : Integer.parseInt(editMisc.getText().toString());
		editDieRoll = (EditText) findViewById(R.id.editInitDieRoll);
		dieRoll = (editDieRoll.getText().length() == 0) ? 0 : Integer.parseInt(editDieRoll.getText().toString());
		
		result.putExtra("Dex", dex);
		result.putExtra("HalfLevel", halfLevel);
		result.putExtra("Misc", misc);
		result.putExtra("DieRoll", dieRoll);
		setResult(Activity.RESULT_OK, result);
		finish();
	}
}
