package appbreeder.controls.app;

public class ABAppRecord {
	private int _id;
	private String appName;
	private String isActive;
	private String serverTimeStamp;
	private String demoPassword;
	
	public int getID()
	{
		return _id;
	}
	
	public void setID(int ID)
	{
		_id = ID;
	}
	
	public String getAppName()
	{
		return appName;
	}
	
	public void setAppName(String _appName)
	{
		appName = _appName;
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

	public String getDemoPassword()
	{
		return demoPassword;
	}
	
	public void setDemoPassword(String _demoPassword)
	{
		demoPassword = _demoPassword;
	}

	

}
