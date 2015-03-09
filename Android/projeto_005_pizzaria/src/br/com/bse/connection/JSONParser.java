package br.com.bse.connection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.bse.tags.Tag;

import android.util.Log;
 
public class JSONParser {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
 
    public JSONParser() {
    
    }
    
    public JSONObject getJSONFromUrl(final String url) {
        // Criando a conexão HTTP
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            
            is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser 1", "Error parsing data " + e.toString());
        }

        return jObj;
    }
    
    public JSONObject makeHttpRequest(String url, int method, List<NameValuePair> params) {
        /*
         * POST = INSERT, DELETE, UPDATE
         * GET = SELECT
        */
    	
        try {
            if(method == Tag.POST) {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
 
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                
                is = httpEntity.getContent();
            } else if(method == Tag.GET) {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
 
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }         
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
        	e.printStackTrace();
        	return null;
        }
 
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            
            is.close();
            json = sb.toString();
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
 
        try {
            if(method == Tag.POST) {
            	jObj = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
            } else {
            	jObj = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
            }
        } catch (JSONException e) {
        	e.printStackTrace();
        	return null;
        }
 
        return jObj;
    }
}