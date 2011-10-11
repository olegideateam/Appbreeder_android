package appbreeder.netupdate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import appbreeder.activity.R;

public class RequestBuilder {
	private static String serviceHost;

	public static String getServiseHost(Context mContext) {
		if (serviceHost == null || serviceHost.length() == 0) {
			if (mContext != null) {
				serviceHost = mContext.getResources().getString(
						R.string.service_host);
			} else
				serviceHost = "";
		}

		return serviceHost;
	}

	public static void setRequestBaseHeader(HttpUriRequest request) {
		
		 request.setHeader("Accept", "application/json");
		 request.setHeader("Content-Type", "application/json; charset=UTF-8");
	}
	 public  static HttpUriRequest TryProduct(String productID)
	 {		 
		 HttpPost postRequest=new HttpPost(getServiseHost(null)+"try");//?subscription_id="+androidID);		
		 
		 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
         nameValuePairs.add(new BasicNameValuePair("subscription_id", ""+1));
         nameValuePairs.add(new BasicNameValuePair("product_id", productID));
         try {
         	postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
         	
 		} catch (UnsupportedEncodingException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}      
		return postRequest;
	 }
	public static HttpUriRequest buildReqest_GetSQLiteDatabase(int appID) {
		HttpPost request = new HttpPost(getServiseHost(null)
			//	+ "/GetAllApp");//
				+"/GetSQLiteDatabase");//?ID="+appID);
		setRequestBaseHeader(request);
		
		JSONObject json = new JSONObject();
		   try {
			json .put("ID", appID);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   try {
			request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      //  nameValuePairs.add(new BasicNameValuePair("product_id", productID));
        
		return request;
	}
	public static HttpUriRequest buildReqest_CheckAppForUpdate(String appData, String skinData) {
		HttpUriRequest request = new HttpGet(getServiseHost(null)
				+ "update-profile");
		setRequestBaseHeader(request);
		return request;
	}
}