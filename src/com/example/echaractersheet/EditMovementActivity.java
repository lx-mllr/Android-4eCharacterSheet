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

public class EditMovementActivity extends Activity {
	
	public static final int REQUEST_CODE = 9;
	
	private int base, armor, item, misc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_movement_acitivty);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent data = getIntent();
		base = data.getIntExtra("Base", 0);
		armor = data.getIntExtra("Armor", 0);
		item = data.getIntExtra("Item", 0);
		misc = data.getIntExtra("Misc", 0);
		
		EditText editBase, editArmor, editItem, editMisc;
		
		editBase = (EditText) findViewById(R.id.editMovementBase);
		editBase.setText(Integer.toString(base));
		editArmor = (EditText) findViewById(R.id.editMovementArmor);
		editArmor.setText(Integer.toString(armor));
		editItem = (EditText) findViewById(R.id.editMovementItem);
		editItem.setText(Integer.toString(item));
		editMisc = (EditText) findViewById(R.id.editMovementMisc);
		editMisc.setText(Integer.toString(misc));
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
		getMenuInflater().inflate(R.menu.edit_movement_acitivty, menu);
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
		EditText editBase, editArmor, editItem, editMisc;
		
		editBase = (EditText) findViewById(R.id.editMovementBase);
		base = (editBase.getText().length() == 0) ? 0 : Integer.parseInt(editBase.getText().toString());
		editArmor = (EditText) findViewById(R.id.editMovementArmor);
		armor = (editArmor.getText().length() == 0) ? 0 : Integer.parseInt(editArmor.getText().toString());
		editItem = (EditText) findViewById(R.id.editMovementItem);
		item = (editItem.getText().length() == 0) ? 0 : Integer.parseInt(editItem.getText().toString());
		editMisc = (EditText) findViewById(R.id.editMovementMisc);
		misc = (editMisc.getText().length() == 0) ? 0 : Integer.parseInt(editMisc.getText().toString());
		
		result.putExtra("Base", base);
		result.putExtra("Armor", armor);
		result.putExtra("Item", item);
		result.putExtra("Misc", misc);
		setResult(Activity.RESULT_OK, result);
		finish();
	}
}
