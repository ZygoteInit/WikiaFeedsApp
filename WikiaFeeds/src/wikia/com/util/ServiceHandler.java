package wikia.com.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceHandler {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
    public final static int PUT=3;
    

    public ServiceHandler() {

    }

  
    public String GetList(String url, int method) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;


            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
               // httpPost.setHeader("Authorization","Bearer "+AccessToken);
                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url


                HttpGet httpGet = new HttpGet(url);
                //httpGet.setHeader("Authorization","Bearer "+AccessToken);
                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }

    public ArrayList<String> ConvertJSONArrayToString(JSONArray jsonArray)
    {
        ArrayList<String> stringArray = new ArrayList<String>();
        //JSONArray jsonArray = new JSONArray();
        for(int i = 0, count = jsonArray.length(); i< count; i++)
        {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                stringArray.add(jsonObject.toString());
            }
            catch (JSONException e) {
                e.printStackTrace();
                return  null;
            }
        }

        return stringArray;
    }
}
