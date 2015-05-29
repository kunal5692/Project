package drishti.assisted.com.drishti;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity {
    TextView text;
    EditText edit1,edit2,edit3;
    Button btn;
    TextToSpeech ttsobj;
    WifiManager wifiManager;
    Context context;
    WifiInfo mywifiInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textId);
        text.setMovementMethod(new ScrollingMovementMethod());
        btn = (Button) findViewById(R.id.buttonId);
        edit1 = (EditText) findViewById(R.id.editId1);
        edit2 = (EditText) findViewById(R.id.editId2);
        edit3 = (EditText) findViewById(R.id.editId3);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mywifiInfo = wifiManager.getConnectionInfo();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new connection().execute();
            }
        });
        /*
        ttsobj=new TextToSpeech(getApplicationContext(),new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {

                if(status != TextToSpeech.ERROR){
                    ttsobj.setLanguage(Locale.UK);
                }
            }
        });


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
        */
        Toast.makeText(getApplicationContext(),"before Wifi",Toast.LENGTH_SHORT).show();

        scanAccessPoints();

    }

    public class connection extends AsyncTask<Void,Void,String>{
        JSONObject jsonobject = new JSONObject();

        @Override
        protected String doInBackground(Void... params) {
           // String resultstring = "";
            JSONObject jsonobject = new JSONObject();

            HttpResponse httpresponse = null;
            String url = "http://lamp-test212.appspot.com";
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost(url);

            try{

                jsonobject.put("rss1",edit1.getText().toString());
                jsonobject.put("rss2",edit2.getText().toString());
                jsonobject.put("rss3",edit3.getText().toString());

            }catch(JSONException e){
                e.printStackTrace();
            }


            StringEntity se = null;
            try {
                se = new StringEntity(jsonobject.toString());
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            se.setContentType("application/json;charset=UTF-8");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
            httppostreq.setEntity(se);
            httppostreq.setHeader("Content-type", "application/json");
            httppostreq.setHeader("Accept","application/json");

            try {
                httpresponse = httpclient.execute(httppostreq);
            } catch (IOException e) {
                e.printStackTrace();
            }


            HttpEntity resultentity = httpresponse.getEntity();
            InputStream inputstream = null;
            try {
                inputstream = resultentity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String resultstring = convertStreamToString(inputstream);
            try {
                inputstream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            resultstring = resultstring.substring(1,resultstring.length()-1);
            return resultstring;
        }

        private String convertStreamToString(InputStream is) {
            String line = "";
            StringBuilder total = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            try {
                while ((line = rd.readLine()) != null) {
                    total.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return total.toString();
        }

        /*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            JSONObject jsonobject = new JSONObject();
            try{
                jsonobject.put("content","message");
                httppostreq.setHeader("Content-Type", "application/json");
                httppostreq.setHeader("Accept", "application/json");

            }catch(JSONException e){
                Toast.makeText(getApplicationContext(),"wrong json object",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    */

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(s);
            text.setText(result);
        }
    }
/*
    public void speakOut(){
        String utterance="";
        String toSpeak = text.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        ttsobj.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,utterance);

    }
*/
    public void scanAccessPoints(){
        String[] access_points = {"AndroidAP", "Manish", "Vipul-Ubuntu"};
        int maxCount=3;
        int apCount = 0;
        int value;
        Toast.makeText(getApplicationContext(),"in WifiScan",Toast.LENGTH_SHORT).show();
        //int myRSSI = mywifiInfo.getRssi();

        if (wifiManager.isWifiEnabled()) {
            Toast.makeText(getApplicationContext(),"in Wifi",Toast.LENGTH_SHORT).show();
            if (wifiManager.startScan()) {
                List<ScanResult> scanResults = wifiManager.getScanResults();
                if (scanResults != null) {
                    //filter by AP name
                    for (int index = 0; index < scanResults.size(); index++) {
                        ScanResult scan_result = scanResults.get(index);
                        //if((scan_result.SSID).contains(ACCESS_POINT_FILTER_NAME)) {
                        for (String temp: access_points) {
                            if ((scan_result.SSID).contains(temp)) {
                                //stringBuffer.append("" + scan_result.SSID + ":" + scan_result.level);
                                if(apCount==0) {
                                    value = scan_result.level;
                                    edit1.setText(String.valueOf(value));
                                    Toast.makeText(getApplicationContext(),"SSID "+scan_result.SSID,Toast.LENGTH_SHORT).show();
                                }
                                    else if(apCount==1) {
                                    value = scan_result.level;
                                    edit2.setText(String.valueOf(value));
                                    Toast.makeText(getApplicationContext(),"SSID "+scan_result.SSID,Toast.LENGTH_SHORT).show();

                                }
                                    else if(apCount==2) {
                                    value = scan_result.level;
                                    edit3.setText(String.valueOf(value));
                                    Toast.makeText(getApplicationContext(),"SSID "+scan_result.SSID,Toast.LENGTH_SHORT).show();

                                }
                                    apCount++;
                                if (apCount == maxCount)
                                    break;

                            }
                        }
                    }
                }
            }

        }
    }
    @Override
    protected void onPause() {
        if(ttsobj !=null){
            ttsobj.stop();
            ttsobj.shutdown();
        }
        super.onPause();
    }
}
