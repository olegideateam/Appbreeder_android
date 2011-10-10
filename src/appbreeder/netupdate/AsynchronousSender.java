package appbreeder.netupdate;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpRequest;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.util.Log;

public class AsynchronousSender extends Thread {

	private static DefaultHttpClient httpClient = new DefaultHttpClient();

	private HttpUriRequest request;
	private Handler handler;
	private CallbackWrapper wrapper;

	protected AsynchronousSender(HttpUriRequest request, Handler handler,
			CallbackWrapper wrapper) {
		this.request = request;
		this.handler = handler;
		this.wrapper = wrapper;
	}

	public void run() {
		try {
			final HttpResponse response;
			// SSLSocketFactory socketFactory = new
			// SSLSocketFactory(keyStore,keyStorePassword ,trustStore);
			// SchemeRegistry registry = new SchemeRegistry();
			// registry.register(new Scheme("http", new PlainSocketFactory(),
			// 80));
			// registry.register(new Scheme("https", (trustAll ? new
			// FakeSocketFactory() : SSLSocketFactory.getSocketFactory()),
			// 443));

			Log.i("getReqest.getURI()",request.getURI().getPath());
			
			
			response = getClient().execute(request);
			//String content = EntityUtils.toString(response.getEntity());
			// process response
			// Log.i("content ",content);
			// Log.v("response.getAllHeaders()"," "+response.getAllHeaders().length);
//			Log.i("headers",response.getAllHeaders().toString());
//			Header [] head=response.getAllHeaders();
//			for(int i=0;i<head.length;i++)
//			{
//				try
//				{
//					
//					Log.i("head:  "+head[i].getName(),head[i].getValue());
//				}catch (Exception e) {
//					 
//				}
//			}
//			try
//			{
//				ApplicationPDB.serverDate=ApplicationPDB.convertDateFromServer(response.getFirstHeader("Date").getValue());
//				
//			}catch (Exception e) {
//				 
//			}
			wrapper.setResponse(response);
			handler.post(wrapper);
		} catch (Exception e) {
			e.printStackTrace();
			 Log.e("Eror AsynchronousSender"," error"+e.getMessage());
			 
		} 
	}
	
	private HttpClient getClient() {
	
		HttpParams params = new BasicHttpParams();
		
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		final org.apache.http.conn.ssl.SSLSocketFactory sslSocketFactory = org.apache.http.conn.ssl.SSLSocketFactory
				.getSocketFactory();
		sslSocketFactory
				.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		registry.register( new Scheme("https", (true? new EasySSLSocketFactory():
			  sslSocketFactory ), 443));

		
		
//      javax.net.ssl.SSLProtocolException: Read error: ssl=0x305040: Failure in SSL library, usually a protocol error

//		javax.net.ssl.SSLProtocolException: Read error: ssl=0x34d208: Failure in SSL library, usually a protocol error

		  
		  ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
					params, registry);  
		//  httpClient.getConnectionManager().getSchemeRegistry().register(sch);
		 // httpClient = new DefaultHttpClient(manager, params);
		 //httpClient=new DefaultHttpClient ();
		//  httpClient.set
		return httpClient;
	}

}