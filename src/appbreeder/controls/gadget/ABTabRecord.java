package appbreeder.controls.gadget;

import android.content.Context;
import android.view.View;

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
	
	public View getTabView(Context context)
	{
		
		return null;
	}

}
