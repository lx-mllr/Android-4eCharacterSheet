package com.example.echaractersheet;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SkillsAdapter extends SimpleAdapter {

	int layoutResourceId;
	Context contxt;
	List<Map<String, String>> dta;
	
	@SuppressWarnings("unchecked")
	public SkillsAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		
		this.layoutResourceId = resource;
		this.contxt = context;
		this.dta = (List<Map<String, String>>) data;
	}
	
	static class SkillHolder
	{
		TextView nameText;
		TextView scoreText;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		SkillHolder holder = null;
		
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)contxt).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder = new SkillHolder();
			holder.nameText = (TextView) row.findViewById(R.id.skillName);
			holder.scoreText = (TextView) row.findViewById(R.id.skillScore);
			
			row.setTag(holder);
		}
		else
		{
			holder = (SkillHolder) row.getTag();
		}
		
		Map<String, String> skill = dta.get(position);
		holder.nameText.setText(skill.get("Name"));
		if(skill.get("Trained").equals("true")) {
			holder.nameText.setTypeface(null, Typeface.BOLD);
		}
		else {		// For some reason, Android will re-use this holder later in the list and the text will be bold by default
					// Setting back to normal fixes the issue.
			holder.nameText.setTypeface(null, Typeface.NORMAL);		
		}
		holder.scoreText.setText(skill.get("Score"));
		
		return row;
	}

}
