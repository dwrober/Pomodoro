package com.example.pomodoro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class PomodoroController extends AsyncTask<String, String, String>{
	public JSONObject data;
	
	
	@Override
    protected String doInBackground(String... uri) {
		StringEntity se = null;
		try {
			se = new StringEntity(data.toString());
			Log.d("SendPostHTTP", data.toString());
			Log.d("StringEntity", se.toString());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
        	HttpPost httpost = new HttpPost(uri[0]);
        	httpost.setHeader("Accept", "application/json");
            httpost.setHeader("Content-type", "application/json");
        	httpost.setEntity(se);
	            
            response = httpclient.execute(new HttpPost(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }

}
