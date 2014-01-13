package com.example.echaractersheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	// Easiest way to set new character IDs
	// TODO: Sort listView on ID
	private int highestCharacterId;
	private ArrayList<Character> characters = new ArrayList<Character>();
	
	// for ListView
	SimpleAdapter adapter;
	private ArrayList<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	private String[] from = {"name", "race", "class" };
	private int[] to = { R.id.chNameView, R.id.chRaceView, R.id.chClassView };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Used to ID new Characters
		// New characters are highestID + 1
		highestCharacterId = 0;
		File folder = getFilesDir();
		File[] files = folder.listFiles();
		if(files != null)
		{
			try
			{
				for(int i = 0; i < files.length; i++)
				{
					
					ObjectInputStream  ois = new ObjectInputStream(new FileInputStream(files[i]));
					
					Character c = (Character) ois.readObject();
					characters.add(c);
					int id = c.getId();
					if(id > highestCharacterId) highestCharacterId = id;
					
					ois.close();
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		
		// Set up ListView
		// Sort Characters based on ID (Newest will be lower on the list)
		Collections.sort(characters, new CharacterComparator());
		// Populate listView data
		for(int i = 0; i < characters.size(); i++)
		{
			setListViewData(characters.get(i).bio);
		}
		// Set up Adapter
		adapter = new SimpleAdapter(this, listData, R.layout.listview_item_main, from, to);
		ListView list = (ListView)findViewById(R.id.chListView);
		list.addFooterView(getLayoutInflater().inflate(R.layout.listview_footer_main, null));
		list.setAdapter(adapter);
		
		// Listeners
		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				Character c = characters.get(position);
				Intent intent = new Intent(MainActivity.this, CharacterActivity.class);
				intent.putExtra("Character", c);
				startActivity(intent);
			}
		});
		list.setOnItemLongClickListener(new OnItemLongClickListener(){
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// inner class method needs to reference final variables
				final int toRemove = position;
				AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
				adb.setTitle("Delete Character?");
				adb.setMessage("Are you sure you want to delete " + 
						listData.get(toRemove).get("name") + "?");
				adb.setNegativeButton("Cancel", null);
				adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialong, int which) {
						// Remove from list view
						listData.remove(toRemove);
						adapter.notifyDataSetChanged();
						// Delete file
						File f = new File(getFilesDir(), "Character" + 
								Integer.toString(characters.get(toRemove).getId()) + ".4ech");
						f.delete();
						// Remove Character
						characters.remove(toRemove);
					}
				});
				adb.show();
				
				return true;
			}
		});
	}
	
	private void setListViewData(String bio)
	{
		String[] data = bio.split(Character.SEPARATOR);
		
		HashMap<String, String> item = new HashMap<String, String>();
		item.put("name", data[0]);	// Name
		item.put("race", data[2]);	// Race
		item.put("class", data[3]); // Class
		listData.add(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// Add New Character Button
	@SuppressLint("UseValueOf")
	public void addNewCharacter(View view)
	{
		Character c = new Character(highestCharacterId + 1);
		Intent intent = new Intent(this, CharacterActivity.class);
		intent.putExtra("Character", c);
		startActivity(intent);
	}

}
