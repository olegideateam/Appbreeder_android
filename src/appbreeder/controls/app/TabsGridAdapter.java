package appbreeder.controls.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import appbreeder.activity.R;
import appbreeder.bll.BaseApplicationManager;
import appbreeder.controls.gadget.ABTabRecord;

public class TabsGridAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater mInflater;
	private ArrayList <ABTabRecord> tabsList = new ArrayList <ABTabRecord> ();
	
	public TabsGridAdapter(Context c, ArrayList <ABTabRecord> _tabsList) {
		context = c;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		tabsList = _tabsList;
    }

	public int getCount() {
		// TODO Auto-generated method stub
		return tabsList.size()-4;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
	
		if (convertView==null) {
			View tab = mInflater.inflate(R.layout.tab_button_layout, null);
			convertView = tab;
			ABTabRecord	curentTab=tabsList.get(position+4);
			View vTab=tab.findViewById(R.id.rrTabButton);
			ImageView but = (ImageView) convertView.findViewById(R.id.ibTabButton);
			
			but.setTag(curentTab.getIcon());
			BaseApplicationManager.imageLoader.DisplayImage(tabsList.get(position+4).getIcon(), (Activity)but .getContext(),
					but);
			vTab.setId(position+5);
			//tab.setId(position+5);
			vTab.setTag(curentTab);
			//but.setId(position+5);
			TextView tv = (TextView) tab.findViewById(R.id.tvTabButton);
			
			tv.setText(curentTab.getTitle());
//			convertView.setId(position+4)

		}
		
		
		//llTabsButtons.addView(tab, i);
		return convertView;
	}

}
