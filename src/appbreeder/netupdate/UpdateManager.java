package appbreeder.netupdate;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import appbreeder.bll.BaseApplicationManager;

public class UpdateManager {
	public static void updateDataBase(int appID,final Handler callbackHandler)
	{
		Client.sendRequest(RequestBuilder.buildReqest_GetSQLiteDatabase(appID),
				new ResponseListener() {
					@Override
					public void onResponseReceived(final HttpResponse response) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								
									JSONObject json = BaseApplicationManager.getJsonOnHttpResponse(response);
									try {
										InputStream in=response.getEntity().getContent();
									} catch (IllegalStateException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									Message msg = new Message();
									if(callbackHandler!=null)
									{
										
//										if(in)
//										{
//										
//										}
										callbackHandler.sendMessage(msg);
									}
								
							}
						}).start();
					}
				});
	}
	public static void updateAppInformaion()
	{
		
	}
	public static void updateGadgetInformaion()
	{
		
	}
	public static void updateGadgetInformaionByType()
	{
		
	}
}
