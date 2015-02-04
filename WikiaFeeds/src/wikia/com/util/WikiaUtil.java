package wikia.com.util;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.util.Log;

public class WikiaUtil {
	
	
	public static boolean isNetworkAvailable(Context ctx) {
		
      boolean haveConnectedWifi = false;
      boolean haveConnectedMobile = false;

      ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo[] netInfo = cm.getAllNetworkInfo();
      for (NetworkInfo ni : netInfo) {
          if (ni.getTypeName().equalsIgnoreCase("WIFI"))
              if (ni.isConnected())
                  haveConnectedWifi = true;
          if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
              if (ni.isConnected())
                  haveConnectedMobile = true;
      }
      return haveConnectedWifi || haveConnectedMobile;
  }
	
	  public static void CopyStream(InputStream is, OutputStream os)
	    {
	        final int buffer_size=1024;
	        try
	        {
	             
	            byte[] bytes=new byte[buffer_size];
	            for(;;)
	            {
	              //Read byte from input stream
	                 
	              int count=is.read(bytes, 0, buffer_size);
	              if(count==-1)
	                  break;
	               
	              //Write byte from output stream
	              os.write(bytes, 0, count);
	            }
	        }
	        catch(Exception ex){}
	    }
	  
	  public static void ShowDialogueAndStartActivity(Context ctx,String Titlemsg,String msg)
	    {

	        new AlertDialog.Builder(ctx)
	                .setTitle(Titlemsg)
	                .setMessage(msg)
	                .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        
	                    }
	                }).show();
	    }


}
