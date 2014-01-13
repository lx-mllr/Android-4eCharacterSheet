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

public class EditDefenseActivity extends Activity {
	
	public static final int REQUEST_CODE = 7;
	
	private int position;
	private Defense defense;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_defense);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent data = getIntent();
		position = data.getIntExtra("Position", -1);
		defense = (Defense) data.getSerializableExtra("Defense");
		
		
		TextView name = (TextView) findViewById(R.id.editDefenseName);
		name.setText(defense.getName());
		
		EditText conditionals, tenPlus, abilArmor, clss, feat, enh, misc;
		conditionals = (EditText) findViewById(R.id.editDefenseConditionals);
		conditionals.setText(defense.conditionals);
		tenPlus = (EditText) findViewById(R.id.editDefenseTenPlus);
		tenPlus.setText(Integer.toString(defense.tenPlusHalfLevel));
		abilArmor = (EditText) findViewById(R.id.editDefenseAbilArmor);
		abilArmor.setText(Integer.toString(defense.abilArmor));
		clss = (EditText) findViewById(R.id.editDefenseClass);
		clss.setText(Integer.toString(defense.classMod));
		feat = (EditText) findViewById(R.id.editDefenseFeat);
		feat.setText(Integer.toString(defense.featMod));
		enh = (EditText) findViewById(R.id.editDefenseEnh);
		enh.setText(Integer.toString(defense.enhMod));
		misc = (EditText) findViewById(R.id.editDefenseMisc);
		misc.setText(Integer.toString(defense.miscMod));
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
		getMenuInflater().inflate(R.menu.edit_defense, menu);
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
		EditText conditionals, tenPlus, abilArmor, clss, feat, enh, misc;
		conditionals = (EditText) findViewById(R.id.editDefenseConditionals);
		if(conditionals.getText().length() > 0) defense.conditionals = conditionals.getText().toString();
		tenPlus = (EditText) findViewById(R.id.editDefenseTenPlus);
		defense.tenPlusHalfLevel = (tenPlus.getText().length() == 0) ? 0 : Integer.parseInt(tenPlus.getText().toString());
		abilArmor = (EditText) findViewById(R.id.editDefenseAbilArmor);
		defense.abilArmor = (abilArmor.getText().length() == 0) ? 0 : Integer.parseInt(abilArmor.getText().toString());
		clss = (EditText) findViewById(R.id.editDefenseClass);
		defense.classMod = (clss.getText().length() == 0) ? 0 : Integer.parseInt(clss.getText().toString());
		feat = (EditText) findViewById(R.id.editDefenseFeat);
		defense.featMod = (feat.getText().length() == 0) ? 0 : Integer.parseInt(feat.getText().toString());
		enh = (EditText) findViewById(R.id.editDefenseEnh);
		defense.enhMod = (enh.getText().length() == 0) ? 0 : Integer.parseInt(enh.getText().toString());
		misc = (EditText) findViewById(R.id.editDefenseMisc);
		defense.miscMod = (misc.getText().length() == 0) ? 0 : Integer.parseInt(misc.getText().toString());
		
		defense.score = defense.tenPlusHalfLevel + defense.abilArmor + defense.classMod + defense.featMod + defense.enhMod + defense.miscMod;
		
		Intent result = new Intent();
		result.putExtra("Position", position);
		result.putExtra("Defense", defense);
		setResult(Activity.RESULT_OK, result);
		finish();
	}

}
