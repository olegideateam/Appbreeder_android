package appbreeder.netupdate;
import org.apache.http.HttpResponse;
 
public interface ResponseListener {
 
	public void onResponseReceived(HttpResponse response);
 
}
