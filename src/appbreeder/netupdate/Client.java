package appbreeder.netupdate;
import android.os.Handler;
 
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
 
public class Client {
 
	public static void sendRequest(final HttpUriRequest request,
			ResponseListener callback) {
				(new AsynchronousSender(request, new Handler(),	new CallbackWrapper(callback))).start();
		
	}
 
}