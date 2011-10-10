package appbreeder.netupdate;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

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
		request.setHeader("Content-Type", "application/json");
	}

	public static HttpUriRequest buildReqest_GetSQLiteDatabase(int appID) {
		HttpUriRequest request = new HttpGet(getServiseHost(null)
				+ "update-profile");
		setRequestBaseHeader(request);
		return request;
	}
	public static HttpUriRequest buildReqest_CheckAppForUpdate(String appData, String skinData) {
		HttpUriRequest request = new HttpGet(getServiseHost(null)
				+ "update-profile");
		setRequestBaseHeader(request);
		return request;
	}
}