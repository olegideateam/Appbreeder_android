package appbreeder.database;

import java.util.ArrayList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import appbreeder.controls.app.ABAppRecord;

public class AppDBManager {
private DBOpenerHelper dbhelper;
	
	public AppDBManager(Context context){
		dbhelper = new DBOpenerHelper(context);	
	}
	
	public int updateABApp(ABAppRecord record){
		int rowid = 0;	
		try{
			SQLiteDatabase sqldb = dbhelper.getWritableDatabase();
			try{
				ContentValues updatevalues = new ContentValues();
				
				updatevalues.put("AppName", record.getAppName());
				updatevalues.put("isActive", record.getIsActive());	
				updatevalues.put("ServerTimestamp", record.getServerTimeStamp());	
				updatevalues.put("DemoPassword", record.getDemoPassword());
				

		        rowid = sqldb.update(DBOpenerHelper.ABAPP_TABLE_NAME, updatevalues, 
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
	
	public void insertABApp(ABAppRecord record){
		
		SQLiteDatabase sqldb = dbhelper.getWritableDatabase();
			try{
				ContentValues initalValues = new ContentValues();
				//initalValues.putNull("profileIndex");
				initalValues.put("ID", record.getID());
				initalValues.put("AppName", record.getAppName());
				initalValues.put("isActive", record.getIsActive());	
				initalValues.put("ServerTimestamp", record.getServerTimeStamp());	
				initalValues.put("DemoPassword", record.getDemoPassword());
				
				sqldb.insertOrThrow(DBOpenerHelper.ABAPP_TABLE_NAME, null, initalValues);		
				
				sqldb.close();				

				
			} catch (SQLException e) {
				// TODO: handle exception
				Log.e(e.toString(), e.toString());
				sqldb.close();
				
			}			
		
	}
	
	public int deleteNews(ABAppRecord record){
		int rowid = 0;
		try{
			SQLiteDatabase sqldb = dbhelper.getWritableDatabase();			
			rowid = sqldb.delete(DBOpenerHelper.ABAPP_TABLE_NAME, "ID =?",
	        		new String[]{Integer.toString(record.getID())});		
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("deleteNews SQLException" + e.getMessage());			
		}
		return rowid;
		
	}
	
		
	public ArrayList<ABAppRecord> getAppRecords(){
	
		
		/*if(QRSynqSingleTon.getInstance().news.size() > 0)
			QRSynqSingleTon.getInstance().news.clear();*/
		
		ArrayList <ABAppRecord> apps = new ArrayList <ABAppRecord>();
		
		try{			
			SQLiteDatabase sqldb = dbhelper.getReadableDatabase();	
			
			Cursor cur=null;
			
			cur = sqldb.query(DBOpenerHelper.ABAPP_TABLE_NAME, null,null,
					null,null,null,null);
				
			if(cur != null){
				cur.moveToFirst();
				
				
				for (int i = 0; i < cur.getCount() ; i++)
				{
					
					ABAppRecord vo = new ABAppRecord();				
				
					vo.setID(Integer.parseInt(cur.getString(0)));
					vo.setAppName(cur.getString(1));
					vo.setIsActive(cur.getString(2));
					vo.setServerTimeStamp(cur.getString(3));
					vo.setDemoPassword(cur.getString(4));	
					
					apps.add(vo);
					
					cur.moveToNext();
				}
				cur.close();
				sqldb.close();
			}
			
			return apps;
		}
		catch (SQLException e) {			
			System.out.println("###########" + e.getMessage());
		//	Log v = e.printStackTrace();
			// TODO: handle exception
		}
		return apps;
	}
}
