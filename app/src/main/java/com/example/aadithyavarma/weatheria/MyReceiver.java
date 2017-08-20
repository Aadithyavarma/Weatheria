package com.example.aadithyavarma.weatheria;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class MyReceiver extends BroadcastReceiver {

    public static Uri uri;
    public static String weather;
    Context myContext;
    String note = null;


    /**
     * AsyncTask to access the web service;
     */
    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                URL url=new URL(params[0]);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                String result="";

                InputStream in= httpURLConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int ch=reader.read();
                while (ch!=-1){
                    result+=(char)ch;
                    ch=reader.read();
                }
                return result;

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                String output="";
                if(s == null){
                    Toast.makeText(myContext,"City not found!!!",Toast.LENGTH_LONG).show();
                    MainActivity.weatherReport.setText("City not found!!!");
                }

                /**
                 * Getting the JSON object and parsing it to get necessary details;
                 */
                JSONObject jsonObject=new JSONObject(s);
                weather += jsonObject.getString("name");
                String res1 = jsonObject.getString("coord");
                JSONObject object = new JSONObject(res1);
                weather += "\nLatitude: "+ object.getString("lat") + "\nLongitude: " + object.getString("lon");
                String res=jsonObject.getString("weather");
                JSONArray jsonArray=new JSONArray(res);
                for (int i=0;i<jsonArray.length();i++){

                    JSONObject jsonPart=jsonArray.getJSONObject(i);
                    output+=jsonPart.getString("main")+", "+jsonPart.getString("description")+"\n";
                    uri = Uri.parse("http://openweathermap.org/img/w/"+jsonPart.getString("icon")+".png");
                    Log.i("main: ",jsonPart.getString("main"));
                    Log.i("desc: ",jsonPart.getString("description"));

                }
                weather += "\nWeather: " + output;
                String main = jsonObject.getString("main");
                JSONObject jsonPart = new JSONObject(main);
                Double temp = Double.parseDouble(jsonPart.getString("temp"));
                temp -= 273.15;

                weather += "\nTemperature: " + String.format("%.2f",temp) + " °C"
                        + "\nPressure: " + jsonPart.getString("pressure") + " Torr"
                        + "\nHumidity: " + jsonPart.getString("humidity") + "%\n";
                if(note == null)
                {
                    MainActivity.weatherReport.setText(weather);
                }
                else{
                    Intent i = new Intent(myContext,MyService.class);
                    i.putExtra("input",weather);
                    myContext.startService(i);
                }
                Glide.with(myContext)
                        .load(MyReceiver.uri)
                        .error(R.drawable.image_placeholder)
                        .into(MainActivity.image);
                Log.i("weather",weather);


                //weather.setText(output);


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * Broadcast Receiver to receive the request;
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        myContext = context;
        //Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show();
        String input = intent.getStringExtra("input");
        String val = intent.getStringExtra("note");
        if(!(val == null))
            note = val;

        weather = "";

        DownloadTask d = new DownloadTask();
        String exe = "http://api.openweathermap.org/data/2.5/weather?q="+input+"&appid=25f2bcf027233925ee8a841e850c2dcf";
        d.execute(exe);


    }
}
