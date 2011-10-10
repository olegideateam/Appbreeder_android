package appbreeder.controls.gadget;

import java.util.ArrayList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import appbreeder.database.DBOpenerHelper;

public class TabsDBManager {
	private DBOpenerHelper dbhelper;
	
	public TabsDBManager(Context context){
		dbhelper = new DBOpenerHelper(context);	
	}
	
	public int updateABTab(ABTabRecord record){
		int rowid = 0;	
		try{
			SQLiteDatabase sqldb = dbhelper.getWritableDatabase();
			try{
				ContentValues updatevalues = new ContentValues();
				
				//updatevalues.put("ID", record.getID());
				updatevalues.put("ParentID", record.getParentID());
				updatevalues.put("AppID", record.getAppID());
				updatevalues.put("GadgetType", record.getGadgetType());
				updatevalues.put("Title", record.getTitle());	
				updatevalues.put("Icon", record.getIcon());	
				updatevalues.put("NavOrder", record.getNavOrder());	
				updatevalues.put("isActive", record.getIsActive());	
				updatevalues.put("ServerTimestamp", record.getServerTimeStamp());	
				updatevalues.put("Long", record.getLongitude());	
				updatevalues.put("Lat", record.getLatitude());	
				updatevalues.put("DisplayRangeMeters", record.getDisplayRangeMeters());	
				updatevalues.put("Password", record.getPassword());	
				updatevalues.put("AccesMode", record.getAccesMode());	
				updatevalues.put("CustomIcon30URL", record.getCustomIcon30URL());
				updatevalues.put("CustomIcon60URL", record.getCustomIcon60URL());
				updatevalues.put("CustomNavBarImage250URL", record.getCustomNavBarImage250URL());
				

		        rowid = sqldb.update(DBOpenerHelper.ABTAB_TABLE_NAME, updatevalues, 
		        		"ID =?",
		        		new String[]{Integer.toString(record.getID())});		
				
				sqldb.close();	
				
				return rowid;

				
			} catch (SQLException e) {
				// TODO: handle exception
				Log.e(e.toString(), e.toString());
				sqldb.close();
				
			}
			
			sqldb.close();
		
		}catch(SQLException e){
			Log.v("Insert Into table Error", e.getMessage());
		}
		
		return rowid;
	}
	
	public long insertABTabs(ABTabRecord record){
		long rowid = 0;	
		try{
			SQLiteDatabase sqldb = dbhelper.getWritableDatabase();
			try{
				ContentValues initalValues = new ContentValues();
				//initalValues.putNull("profileIndex");
				initalValues.put("ID", record.getID());
				initalValues.put("ParentID", record.getParentID());
				initalValues.put("AppID", record.getAppID());
				initalValues.put("GadgetType", record.getGadgetType());
				initalValues.put("Title", record.getTitle());	
				initalValues.put("Icon", record.getIcon());	
				initalValues.put("NavOrder", record.getNavOrder());	
				initalValues.put("isActive", record.getIsActive());	
				initalValues.put("ServerTimestamp", record.getServerTimeStamp());	
				initalValues.put("Long", record.getLongitude());	
				initalValues.put("Lat", record.getLatitude());	
				initalValues.put("DisplayRangeMeters", record.getDisplayRangeMeters());	
				initalValues.put("Password", record.getPassword());	
				initalValues.put("AccesMode", record.getAccesMode());	
				initalValues.put("CustomIcon30URL", record.getCustomIcon30URL());
				initalValues.put("CustomIcon60URL", record.getCustomIcon60URL());
				initalValues.put("CustomNavBarImage250URL", record.getCustomNavBarImage250URL());
				
				rowid = sqldb.insertOrThrow(DBOpenerHelper.ABTAB_TABLE_NAME, null, initalValues);		
				
				sqldb.close();	
				
				return rowid;

				
			} catch (SQLException e) {
				// TODO: handle exception
				Log.e(e.toString(), e.toString());
				sqldb.close();
				
			}
			
			sqldb.close();
		
		}catch(SQLException e){
			Log.v("Insert Into table Error", e.getMessage());
		}
		
		return rowid;
	}
	
	public int deleteNews(ABTabRecord record){
		int rowid = 0;
		try{
			SQLiteDatabase sqldb = dbhelper.getWritableDatabase();			
			rowid = sqldb.delete(DBOpenerHelper.ABTAB_TABLE_NAME, "ID =?",
	        		new String[]{Integer.toString(record.getID())});		
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("deleteNews SQLException" + e.getMessage());			
		}
		return rowid;
		
	}
	
	
	
	public ArrayList<ABTabRecord> getTabsRecords(String AppID){
	
		
		/*if(QRSynqSingleTon.getInstance().news.size() > 0)
			QRSynqSingleTon.getInstance().news.clear();*/
		
		ArrayList <ABTabRecord> tabs = new ArrayList <ABTabRecord>();
		
		try{			
			SQLiteDatabase sqldb = dbhelper.getReadableDatabase();	
			
			Cursor cur=null;
			
			cur = sqldb.query(DBOpenerHelper.ABTAB_TABLE_NAME, null,"AppID =?",
					new String[]{AppID},
					null,null,"NavOrder");
				
			if(cur != null){
				cur.moveToFirst();
				
				
				for (int i = 0; i < cur.getCount() ; i++)
				{
					
					ABTabRecord vo = new ABTabRecord();				
				
					vo.setID(Integer.parseInt(cur.getString(0)));
					vo.setParentID(Integer.parseInt(cur.getString(1)));
					vo.setAppID(Integer.parseInt(cur.getString(2)));
					vo.setGadgetType(cur.getString(3));
					vo.setTitle(cur.getString(4));
					vo.setIcon(cur.getString(5));
					vo.setNavOrder(cur.getString(6));
					vo.setIsActive(cur.getString(7));
					vo.setServerTimeStamp(cur.getString(8));
					vo.setLongitude(Double.parseDouble(cur.getString(9)));
					vo.setLatitude(Double.parseDouble(cur.getString(10)));
					vo.setDisplayRangeMeters(Integer.parseInt(cur.getString(11)));
					vo.setPassword(cur.getString(12));
					vo.setAccesMode(cur.getString(13));
					vo.setCustomIcon30URL(cur.getString(14));
					vo.setCustomIcon60URL(cur.getString(15));
					vo.setCustomNavBarImage250URL(cur.getString(16));
	
					
					
					tabs.add(vo);
					
					cur.moveToNext();
				}
				cur.close();
				sqldb.close();
			}
			
			return tabs;
		}
		catch (SQLException e) {			
			System.out.println("###########" + e.getMessage());
		//	Log v = e.printStackTrace();
			// TODO: handle exception
		}
		return tabs;
	}
}
