package appbreeder.controls.gadget;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import appbreeder.bll.BaseApplicationManager;

public class ABTabRecord {
	
	private int _id;
	private int parent_id;
	private int app_id;
	private String gadgetType;
	private String title;
	private String icon;
	private String navOrder;
	private String isActive;
	private String serverTimeStamp;
	private double longitude;
	private double latitude;
	private int displayRangeMeters;
	private String password;
	private String accesMode;
	private String customIcon30URL;
	private String customIcon60URL;
	private String customNavBarImage250URL;
	
	public static String c_ID="ID";
	public static String c_ParentID="ParentID";
	public static String c_AppID="AppID";
	public static String c_GadgetType="GadgetType";
	public static String c_Title="Title";
	public static String c_Icon="Icon";
	public static String c_NavOrder="NavOrder";
	public static String c_IsActive="IsActive";
	public static String c_ServerTimestamp="ServerTimestamp";
	public static String c_Long="Long";
	public static String c_Lat="Lat";
	public static String c_DisplayRangeMeters="DisplayRangeMeters";
	public static String c_Password="Password";
	public static String c_AccessMode="AccessMode";
	public static String c_CustomIcon30URL="CustomIcon30URL";
	public static String c_CustomIcon60URL="CustomIcon60URL";
	public static String c_CustomNavBarImage250URL="CustomNavBarImage250URL";
	
	
	public ABTabRecord(SQLiteDatabase sqldb,Cursor cur)
	{
		setID(Integer.parseInt(getValueString(cur, c_ID)));
		setParentID(Integer.parseInt(getValueString(cur, c_ParentID)));
		setAppID(Integer.parseInt(getValueString(cur, c_AppID)));
		setGadgetType(getValueString(cur, c_GadgetType));
		setTitle(getValueString(cur, c_Title));
		setIcon(getValueString(cur, c_Icon));
		setNavOrder(getValueString(cur, c_NavOrder));
		setIsActive(getValueString(cur, c_IsActive));
		setServerTimeStamp(getValueString(cur, c_ServerTimestamp));
		setLongitude(Double.parseDouble(getValueString(cur, c_Long)));
		setLatitude(Double.parseDouble(getValueString(cur, c_Lat)));
		setDisplayRangeMeters(Integer.parseInt(getValueString(cur, c_DisplayRangeMeters)));
		setPassword(getValueString(cur, c_Password));
		setAccesMode(getValueString(cur, c_AccessMode));
		setCustomIcon30URL(getValueString(cur, c_CustomIcon30URL));
		setCustomIcon60URL(getValueString(cur, c_CustomIcon60URL));
		setCustomNavBarImage250URL(getValueString(cur, c_NavOrder));
	}
	public String getValueString(Cursor cur, String columnName)
	{
		
		return cur.getString(cur.getColumnIndex(columnName));
	}
	public int getID()
	{
		return _id;
	}
	
	public void setID(int ID)
	{
		_id = ID;
	}
	
	public int getParentID()
	{
		return parent_id;
	}
	
	public void setParentID(int ID)
	{
		parent_id = ID;
	}
	
	public int getAppID()
	{
		return app_id;
	}
	
	public void setAppID(int ID)
	{
		app_id = ID;
	}

	public String getGadgetType()
	{
		return gadgetType;
	}
	
	public void setGadgetType(String _type)
	{
		gadgetType = _type;
	}

	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String _title)
	{
		title = _title;
	}

	public String getIcon()
	{
		if(!icon.startsWith("http://"))
		{
			return BaseApplicationManager.baseDomainForResources+icon;
		}
		return icon;
	}
	
	public void setIcon(String _icon)
	{
		icon = _icon;
	}

	public String getNavOrder()
	{
		return navOrder;
	}
	
	public void setNavOrder(String _navOrder)
	{
		navOrder = _navOrder;
	}

	public String getIsActive()
	{
		return isActive;
	}
	
	public void setIsActive(String _isActive)
	{
		isActive = _isActive;
	}

	public String getServerTimeStamp()
	{
		return serverTimeStamp;
	}
	
	public void setServerTimeStamp(String _serverTimeStamp)
	{
		serverTimeStamp = _serverTimeStamp;
	}

	public double getLongitude()
	{
		return longitude;
	}
	
	public void setLongitude(double _longitude)
	{
		longitude = _longitude;
	}

	public double getLatitude()
	{
		return latitude;
	}
	
	public void setLatitude(double _latitude)
	{
		latitude = _latitude;
	}
	
	public int getDisplayRangeMeters()
	{
		return displayRangeMeters;
	}
	
	public void setDisplayRangeMeters(int _displayRangeMeters)
	{
		displayRangeMeters = _displayRangeMeters;
	}

	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String _password)
	{
		password = _password;
	}
	
	public String getAccesMode()
	{
		return accesMode;
	}
	
	public void setAccesMode(String _accesMode)
	{
		accesMode = _accesMode;
	}
	
	public String getCustomIcon30URL()
	{
		return customIcon30URL;
	}
	
	public void setCustomIcon30URL(String _customIcon30URL)
	{
		customIcon30URL = _customIcon30URL;
	}
	
	public String getCustomIcon60URL()
	{
		return customIcon60URL;
	}
	
	public void setCustomIcon60URL(String _customIcon60URL)
	{
		customIcon60URL = _customIcon60URL;
	}
	
	public String getCustomNavBarImage250URL()
	{
		return customNavBarImage250URL;
	}
	
	public void setCustomNavBarImage250URL(String _customNavBarImage250URL)
	{
		customNavBarImage250URL = _customNavBarImage250URL;
	}
	
	public Cursor getTabelCursor(SQLiteDatabase sqldb,String tName)
	{
		return sqldb.rawQuery("select * from "+tName
				+" where G_ID=?", new String[]{""+getID()});
//		cur = sqldb.rawQuery("select * from "+ DBOpenerHelper.ABTAB_TABLE_NAME +" a JOIN " 
//				+ DBOpenerHelper.ABGADGET_RSSFEED_TABLE_NAME +" b in v"+
//				" where AppID =?",
//				new String[]{AppID});
	}
	public View getTabView(Context context)
	{	
		Log.i(this.getClass().getName(), "getView");
		RelativeLayout result = new RelativeLayout(context);

		TextView tmp = new TextView(context);
		tmp.setText("This is sample "+gadgetType);
		tmp.setTextColor(Color.WHITE);
		tmp.setBackgroundColor(Color.BLACK);
		RelativeLayout.LayoutParams relParams=new RelativeLayout.LayoutParams(-2,-2);
		relParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		result.addView(tmp,relParams);
		return result;

	}

}
