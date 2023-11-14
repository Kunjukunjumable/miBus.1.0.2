package com.zone.android.mibus.miBus_Utility;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;


public class CallWebservice
{

    Context context;

    public void CallGetWebServices(String url, ServiceCallback svccallback )
    {

        CallGetWebServices callgetwebsvc = new CallGetWebServices();
        callgetwebsvc.IsCallSuccess = true;
        callgetwebsvc.url = url;
        callgetwebsvc.svccallback = svccallback;
        callgetwebsvc.execute();
    }

    public void CallPostWebServices(String url, String postparam, ServiceCallback svccallback )
    {

        CallPostWebServices callpostwebsvc = new CallPostWebServices();
        callpostwebsvc.url = url;
        callpostwebsvc.postPara = postparam;
        callpostwebsvc.svccallback = svccallback;
        callpostwebsvc.IsCallSuccess = true;
        callpostwebsvc.execute();
    }

    public void CallSendPostServices(String url, JSONObject jsonObj, ServiceCallback svccallback )
    {

        SendPostRequest callpostwebsvc = new SendPostRequest();
        callpostwebsvc.posturl = url;
        callpostwebsvc.postDataParams = jsonObj;
        callpostwebsvc.svccallback = svccallback;
        callpostwebsvc.IsCallSuccess = true;
        callpostwebsvc.execute();
    }


    public void CallFilePostWebServices(String url, MultipartEntityBuilder postfileparam, ServiceCallback svccallback )
    {

        CallFilePostWebServices callfilepostwebsvc = new CallFilePostWebServices();
        callfilepostwebsvc.url = url;
        callfilepostwebsvc.postFileParam = postfileparam;
        callfilepostwebsvc.svccallback = svccallback;
        callfilepostwebsvc.IsCallSuccess = true;
        callfilepostwebsvc.execute();
    }

    private class CallGetWebServices extends AsyncTask< Void, Void, String >
    {

        private String url;
        private Boolean IsCallSuccess;
        public ServiceCallback svccallback;

        protected String getASCIIContentFromEntity(HttpEntity entity ) throws IllegalStateException, IOException
        {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n = 1;
            while ( n > 0 )
            {
                byte[] b = new byte[ 4096 ];
                n = in.read( b );
                if ( n > 0 )
                    out.append( new String( b, 0, n ) );
            }
            return out.toString();
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params )
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet( this.url );
            String text = null;
            try
            {
                HttpResponse response = httpClient.execute( httpGet, localContext );
                HttpEntity entity = response.getEntity();
                text = getASCIIContentFromEntity( entity );
            }
            catch ( Exception e )
            {
                text = e.getLocalizedMessage();
                this.IsCallSuccess = false;
            }
            return text;
        }

        protected void onPostExecute( String results )
        {
            if ( results != null )
            {

                if ( this.IsCallSuccess )
                {
                    try
                    {
                        this.svccallback.SuccessCallbak( results );
                    }
                    catch ( JSONException e )
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return;
                }
                else
                {
                    this.svccallback.ErrorCallbak( results );
                    return;
                }

            }
            this.svccallback.ErrorCallbak( "null" );
        }
    }


    private class CallPostWebServices extends AsyncTask< Void, Void, String >
    {

        private String url;

        private String postPara;

        private Boolean IsCallSuccess;

        public ServiceCallback svccallback;

        protected String getASCIIContentFromEntity(HttpEntity entity )

        throws IllegalStateException, IOException
        {

            InputStream in = entity.getContent();

            StringBuffer out = new StringBuffer();

            int n = 1;
             int i=0;
             try {
            	  while ( n > 0 )
                  {

                      byte[] b = new byte[ 4096 ];

                      n = in.read( b );

                      if ( n > 0 )

                          out.append( new String( b, 0, n ) );
                      Log.e("", "vall "+i++);
                      

                  }
				
			} catch (Exception e) {
				// TODO: handle exception
			}
          
            Log.e("", "vall finn "+i);
            return out.toString();

        }

        @Override
        protected void onPreExecute()
        {

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params )
        {

            HttpClient httpClient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout( httpClient.getParams(),5000 );

            HttpPost httppost = new HttpPost( this.url );
            httppost.setHeader( "Content-type", "application/json;charset=utf-8" );
            httppost.setHeader( "Accept", "application/json" );
         //   httppost.setHeader( "crossDomain", true );
            String text = null;
            try
            {
                httppost.setEntity(new StringEntity(this.postPara));
                HttpResponse response = httpClient.execute(httppost);
                HttpEntity entity = response.getEntity();
                System.out.println( "response code"
                + response.getStatusLine().getStatusCode() );
                text = getASCIIContentFromEntity(entity);
            }
            catch ( Exception e )
            {
                text = e.getLocalizedMessage();
                this.IsCallSuccess = false;
            }
            return text;
        }

        protected void onPostExecute( String results )
        {

            if ( results != null )
            {

                if ( this.IsCallSuccess )
                {

                    try
                    {
                        this.svccallback.SuccessCallbak( results );
                    }
                    catch ( JSONException e )
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    return;

                }
                else
                {

                    this.svccallback.ErrorCallbak( results );

                    return;

                }

            }

            this.svccallback.ErrorCallbak( "null" );

        }

    }







    private class CallPostWebServices1 extends AsyncTask< Void, Void, String >
    {

        private String url;
        private String postPara;
        private Boolean IsCallSuccess;
        public ServiceCallback svccallback;

        protected String getASCIIContentFromEntity(HttpEntity entity ) throws IllegalStateException, IOException
        {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n = 1;
            while ( n > 0 )
            {
                byte[] b = new byte[ 4096 ];
                n = in.read( b );
                if ( n > 0 )
                    out.append( new String( b, 0, n ) );
            }
            return out.toString();
        }

        @Override
        protected void onPreExecute()
        {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params )
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout( httpClient.getParams(), 5000 );
            HttpContext localContext = new BasicHttpContext();
            HttpPost httppost = new HttpPost( this.url );
            httppost.setHeader( "Content-type", "application/json" );
            httppost.setHeader( "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" );
            String text = null;
            try
            {

                StringEntity se = new StringEntity( this.postPara );
                se.setContentEncoding( new BasicHeader( HTTP.CONTENT_TYPE,
                        "application/x-www-form-urlencoded; charset=UTF-8" ) );
                httppost.setEntity( se );
                HttpResponse response = httpClient.execute( httppost, localContext );
                HttpEntity entity = response.getEntity();
                System.out.println( "response code" + response.getStatusLine().getStatusCode() );
                text = getASCIIContentFromEntity( entity );
            }
            catch ( Exception e )
            {
                text = e.getLocalizedMessage();
                this.IsCallSuccess = false;
            }
            Log.e( "text", "text" + text );
            return text;
        }

        protected void onPostExecute( String results )
        {
            if ( results != null )
            {
                if ( this.IsCallSuccess )
                {
                    try
                    {
                        this.svccallback.SuccessCallbak( results );
                    }
                    catch ( JSONException e )
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return;
                }
                else
                {
                    this.svccallback.ErrorCallbak( results );
                    return;
                }
            }
            this.svccallback.ErrorCallbak( "null" );
        }
    }


    public static  class SendPostRequest extends AsyncTask<String, Void, String> {

        private String posturl;

        JSONObject postDataParams;

        private Boolean IsCallSuccess;

        public ServiceCallback svccallback;

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(posturl); // here is your URL path

                Log.e("params",postDataParams.toString());


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                this.IsCallSuccess = false;
                return new String("Exception: " + e.getMessage());

            }

        }

        @Override
        protected void onPostExecute(String result) {

            Log.e("resutlllt ","cccccc "+result);


            if ( result != null )
            {
                if ( this.IsCallSuccess )
                {
                    try
                    {
                        this.svccallback.SuccessCallbak( result );
                    }
                    catch ( JSONException e )
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return;
                }
                else
                {
                    this.svccallback.ErrorCallbak( result );
                    return;
                }
            }
            this.svccallback.ErrorCallbak( "null" );
        }

        }


    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }



    private class CallFilePostWebServices extends AsyncTask< Void, Void, String >
    {

        private String url;
        private MultipartEntityBuilder postFileParam;
        private Boolean IsCallSuccess;
        public ServiceCallback         svccallback;

        protected String getASCIIContentFromEntity(HttpEntity entity ) throws IllegalStateException, IOException
        {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n = 1;
            while ( n > 0 )
            {
                byte[] b = new byte[ 4096 ];
                n = in.read( b );
                if ( n > 0 )
                    out.append( new String( b, 0, n ) );
            }
            return out.toString();
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params )
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout( httpClient.getParams(), 5000 );
            HttpContext localContext = new BasicHttpContext();
            HttpPost httppost = new HttpPost( this.url );
            String text = null;
            try
            {
                httppost.setHeader( "Accept", "application/json" );
                httppost.setEntity( this.postFileParam.build() );
                HttpResponse response = httpClient.execute( httppost, localContext );
                HttpEntity entity = response.getEntity();
                text = getASCIIContentFromEntity( entity );
            }
            catch ( Exception e )
            {
                text = e.getLocalizedMessage();
                this.IsCallSuccess = false;
            }
            return text;
        }

        protected void onPostExecute( String results )
        {
            this.postFileParam = null;
            if ( results != null )
            {
                if ( this.IsCallSuccess )
                {
                    try
                    {
                        this.svccallback.SuccessCallbak( results );
                    }
                    catch ( JSONException e )
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return;
                }
                else
                {
                    this.svccallback.ErrorCallbak( results );
                    return;
                }
            }
            this.svccallback.ErrorCallbak( "null" );
        }
    }

}
