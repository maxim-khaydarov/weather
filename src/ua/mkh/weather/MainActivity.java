package ua.mkh.weather;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.devsmart.android.ui.HorizontalListView;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager.LayoutParams;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.TaskStackBuilder;

/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


@SuppressLint("NewApi")
public class MainActivity extends Activity  implements OnClickListener {

	
	public static final String APP_PREFERENCES_WEATHER = "mysettings_weather"; 
	public static final String APP_PREFERENCES_CITY = "mysettings_city"; 
	public static final String APP_PREFERENCES = "mysettings"; 
	public static final String APP_PREFERENCES_ADRESS = "adress";

	
	private TextView cityText;
	private TextView condDescr;
	private TextView temp;
	private TextView tempDay;
	private TextView tempNight;
	private TextView press;
	private TextView windSpeed;
	
	String nnn;
	
	ProgressBar prog1;
	Button button1, button2;
	
	TextView day1, day2, day3, day4, day5, day6, day1_temp, day1_icon;
	TextView textView1, textView2, textView3, TextView01, TextView03, textView8, textView9, textView10, textView11, textView12,
	textView7, textView14, textView15;
	int vs = 0;
	ImageView  img1;
	
	ListView listView1;
	HorizontalListView listview;
	ListViewAdapter adapter;
	ListViewAdapterDaily adapter_daily;
	JSONObject jsonobject;
	JSONObject jsonobject_d;
	JSONObject jsonobject_b;
	JSONArray jsonarray;
	
	ArrayList<HashMap<String, String>> arraylist_forecast;
	ArrayList<HashMap<String, String>> arraylist_daily;
	ArrayList<String> arraylist_base;
	
	static String DAY = "date";
	static String ICON = "icon";
	static String TEMP_MIN = "temp_min";
	static String TEMP_MAX = "temp_max";
	
	static String TIME = "time";
	static String IC = "ic";
	static String TEMP = "temp";
	
	
	TelephonyManager telephonyManager;
	myPhoneStateListener psListener;
	
	
    ArrayList<String> weather = new ArrayList<String>();
   

    private static String url_forecast, url_base, url_daily;
	
	ProgressDialog mProgressDialog;
	
	
	private TextView hum;
	
	Typeface typefaceRoman, typefaceMedium, typefaceBold, typefaceThin, typefaceUltra;
	LinearLayout lll;
	
	 //private String url;
	 
	 String temperature, date, condition, humidity, wind, link;
	 
	
	   SharedPreferences mSettings;

	   LinearLayout main;
	   ScrollView scrollView1;
	   
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		String roman = "fonts/Regular.otf";
		String medium = "fonts/Medium.otf";
		String bold =  "fonts/Bold.otf";
		String thin = "fonts/Thin.otf";
		String ultra = "fonts/Ultralight.otf";
		typefaceRoman = Typeface.createFromAsset(getAssets(), roman);
		typefaceMedium = Typeface.createFromAsset(getAssets(), medium);
		typefaceBold = Typeface.createFromAsset(getAssets(), bold);
		typefaceThin = Typeface.createFromAsset(getAssets(), thin);
		typefaceUltra = Typeface.createFromAsset(getAssets(), ultra);
		
		mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
		
		 psListener = new myPhoneStateListener();
		 telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		 telephonyManager.listen(psListener,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		 
		   // check_int();
		
		    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloudy_cloud);
			
			
			
		    
			
			
		listView1 = (ListView) findViewById(R.id.listView1);
		listview = (HorizontalListView) findViewById(R.id.listview);

		button2 = (Button)findViewById(R.id.button2);
		button2.setOnClickListener(this);
		
		prog1 = (ProgressBar) findViewById(R.id.progressBar1);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);

		cityText = (TextView) findViewById(R.id.cityText);
		condDescr = (TextView) findViewById(R.id.condDescr);
		temp = (TextView) findViewById(R.id.temp);
		hum = (TextView) findViewById(R.id.hum);
		press = (TextView) findViewById(R.id.press);
		windSpeed = (TextView) findViewById(R.id.windSpeed);
		tempDay = (TextView) findViewById(R.id.textView5);
		tempNight = (TextView) findViewById(R.id.textView6);
		
		
		TextView03 = (TextView) findViewById(R.id.TextView03);
		TextView01 = (TextView) findViewById(R.id.TextView01);
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);
		textView7 = (TextView) findViewById(R.id.textView7);
		textView8 = (TextView) findViewById(R.id.textView8);
		textView9 = (TextView) findViewById(R.id.textView9);
		textView10 = (TextView) findViewById(R.id.textView10);
		textView11 = (TextView) findViewById(R.id.textView11);
		textView12 = (TextView) findViewById(R.id.textView12);
		textView14 = (TextView) findViewById(R.id.textView14);
		textView15 = (TextView) findViewById(R.id.textView15);
		//imgView = (ImageView) findViewById(R.id.condIcon);
		
		img1 = (ImageView) findViewById(R.id.imageView1);
		
		
		main = (LinearLayout) findViewById(R.id.mainLayout);
		scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
		scrollView1.setVisibility(View.GONE);
		main.setVisibility(View.GONE);
		
		
		//Intent intent = new Intent(this, AllCityActivity.class);
    	//startActivity(intent);
		
		//JSONWeatherTask task = new JSONWeatherTask();
		//task.execute(new String[]{city,lang});
		
		//JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
		//task1.execute(new String[]{city,lang, forecastDaysNum});
		
		//mDbHelper = new CustomersDbAdapter(this);
    	//mDbHelper.open();
    	
    	//new CopyDataBase().execute();
		
		//GetBaseWeather task3 = new GetBaseWeather();
		//task3.execute();
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		String currentDateandTime = sdf.format(new Date());
		
		String upperString = currentDateandTime.substring(0,1).toUpperCase() + currentDateandTime.substring(1);
		
		textView1.setText(upperString);
		
		temp.setTypeface(typefaceUltra);
		cityText.setTypeface(typefaceThin);
		condDescr.setTypeface(typefaceThin);
		hum.setTypeface(typefaceRoman);
		press.setTypeface(typefaceRoman);
		windSpeed.setTypeface(typefaceRoman);
		textView1.setTypeface(typefaceRoman);
		textView2.setTypeface(typefaceThin);
		textView3.setTypeface(typefaceRoman);
		textView7.setTypeface(typefaceRoman);
		TextView03.setTypeface(typefaceRoman);
		TextView01.setTypeface(typefaceRoman);
		textView8.setTypeface(typefaceRoman);
		textView9.setTypeface(typefaceRoman);
		textView10.setTypeface(typefaceRoman);
		textView11.setTypeface(typefaceRoman);
		textView12.setTypeface(typefaceRoman);
		textView14.setTypeface(typefaceRoman);
		textView15.setTypeface(typefaceRoman);
		//textView4.setTypeface(typefaceThin);
		tempDay.setTypeface(typefaceRoman);
		tempNight.setTypeface(typefaceThin);
		
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())  { 
	     	
	    case R.id.button1:
	    	check_int();
	    	
			break;
	    case R.id.button2:
	    	Intent intenwt = new Intent(this, AllCityActivity.class);
	    	startActivity(intenwt);
	    	
	}
	}
	


private class GetForecastWeather extends AsyncTask<Void, Void, Void>  {

	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        try{
        	
        prog1.setVisibility(View.VISIBLE);
        button1.setVisibility(View.GONE);
        
        }
        catch(NullPointerException e){
        	
        }
    }
	
	@Override
	protected Void doInBackground(Void... arg0)  {
		//DAILY WEATHER
		
		arraylist_daily = new ArrayList<HashMap<String, String>>();
		// Retrieve JSON Objects from the given URL address
		jsonobject_d = JSONfunctions
				.getJSONfromURL(url_daily);


		
		 
        	 try {
        		 
        		 JSONArray  list3 = jsonobject_d.getJSONArray("list");
        		 //Loop the Array
        		 for(int i=0;i < 10; i++){ 
        			 JSONObject jDayForecast = list3.getJSONObject(i);
        			 
        			 
        			 long dt = jDayForecast.getLong("dt");
        			 
        			 
        			 Date date = new Date(dt*1000L); // *1000 is to convert seconds to milliseconds
        			 SimpleDateFormat sdf = new SimpleDateFormat("kk"); // the format of your date
        			 String formattedDate = sdf.format(date);
        			

        			 
        			 JSONObject jTempObj = jDayForecast.getJSONObject("main"); 
        			 int t = jTempObj.getInt("temp");	 
        			 int temp = Math.round(t);
        			 
        			 JSONArray jWeatherArr = jDayForecast.getJSONArray("weather");
        			 JSONObject jWeatherObj = jWeatherArr.getJSONObject(0);
        			 String icon = jWeatherObj.getString("icon");
        			 
        			 HashMap<String, String> map = new HashMap<String, String>();
        			 if (i == 0){
        			 map.put("time", getString(R.string.now));
        			 map.put("temp", Integer.toString(temp));
        			 map.put("ic", icon);
        			 }
        			 else {
        				 map.put("time", formattedDate);
            			 map.put("temp", Integer.toString(temp));
            			 map.put("ic", icon);
        			 }
        			 
        			// Log.d("date", upperString);
        			// Log.d("temp_min", Integer.toString(tmin));
        			// Log.d("temp_max", Integer.toString(tmax));
        			// Log.d("icon", icon);
        			 
        			 arraylist_daily.add(map);
        			 
        			 
        		 }
        	 }
        	 catch (JSONException e){
        		 
        	 }
        	 catch (NullPointerException w){
        		 
        	 }
		
		
		
		
		
		
	//BASE WEATHER
		ServiceHandler sh = new ServiceHandler();
		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(url_base, ServiceHandler.GET);
		
		
		
		arraylist_base = new ArrayList<String>();
		// Retrieve JSON Objects from the given URL address
		
		//jsonobject_b = JSONfunctions
		//		.getJSONfromURL(url_base);
		
		try {
			JSONObject jsonObj = new JSONObject(jsonStr);
   		 
   		 JSONArray  list = jsonObj.getJSONArray("weather");
   		String city_b = jsonObj.getString("name");
   		
   		
   		
   		
   		 //Loop the Array
   		for (int i = 0; i < list.length(); i++) {
   			JSONObject JSONWeather = list.getJSONObject(i);
   			String description_b = JSONWeather.getString("description");
   			String main_b = JSONWeather.getString("main");
   			String icon_b = JSONWeather.getString("icon").substring(2);
   			arraylist_base.add(description_b);//0
   			arraylist_base.add(main_b);//1
   			arraylist_base.add(icon_b);//2

   			
   		}
   		JSONObject sysObj = jsonObj.getJSONObject("sys");
   		String country =  sysObj.getString("country");
   		long sunrise = sysObj.getLong("sunrise");
   		long sunset = sysObj.getLong("sunset");
   		
   		Date date1 = new Date(sunrise*1000L); // *1000 is to convert seconds to milliseconds
   		Date date2 = new Date(sunset*1000L); // *1000 is to convert seconds to milliseconds

   		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // the format of your date
   		String formattedDateSunrise = sdf.format(date1);
   		String formattedDateSunset = sdf.format(date2);
   		
   		JSONObject mainObj = jsonObj.getJSONObject("main");
   		int temp_b = mainObj.getInt("temp");
   		int temp_min_b = mainObj.getInt("temp_min");
		int temp_max_b = mainObj.getInt("temp_max");
   		String pressure_b = mainObj.getString("pressure");
	 	String humidity_b = mainObj.getString("humidity");
	 	
	 	 String tmin_b = String.valueOf(Math.round(temp_min_b));
		 String tmax_b = String.valueOf(Math.round(temp_max_b));
		 String t_b = String.valueOf(Math.round(temp_b));
		  
		  
		  JSONObject windObj = jsonObj.getJSONObject("wind");
		  String wind_b = windObj.getString("speed");
   		
		  arraylist_base.add(pressure_b);//3
		  arraylist_base.add(humidity_b);//4
		  arraylist_base.add(tmin_b);//5
		  arraylist_base.add(tmax_b);//6
		  arraylist_base.add(t_b);//7
		  arraylist_base.add(wind_b);//8
		  arraylist_base.add(city_b);//9
		  arraylist_base.add(formattedDateSunrise);//10
		  arraylist_base.add(formattedDateSunset);//11
		  arraylist_base.add(country);//12
		  
		}
		catch (JSONException e){
	   		 
	   	 }
		catch (NullPointerException w){
   		 
   	 }
	//FORECAST WEATHER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
		arraylist_forecast = new ArrayList<HashMap<String, String>>();
		// Retrieve JSON Objects from the given URL address
		jsonobject = JSONfunctions
				.getJSONfromURL(url_forecast+"&cnt=11");


		
		 
        	 try {
        		 
        		 JSONArray  list2 = jsonobject.getJSONArray("list");
        		 //Loop the Array
        		 for(int i=0;i < list2.length();i++){ 
        			 JSONObject jDayForecast = list2.getJSONObject(i);
        			 
        			 ///Calendar c = Calendar.getInstance(); 
        			 
        			 long dt = jDayForecast.getLong("dt");
        			 
        			 
        			 Date date = new Date(dt*1000L); // *1000 is to convert seconds to milliseconds
        			 SimpleDateFormat sdf = new SimpleDateFormat("EEEE"); // the format of your date
        			 String formattedDate = sdf.format(date);
        			 String upperString = formattedDate.substring(0,1).toUpperCase() + formattedDate.substring(1);

        			 
        			 JSONObject jTempObj = jDayForecast.getJSONObject("temp");
        			 int temp_min = jTempObj.getInt("night");
        			 int temp_max = jTempObj.getInt("day");
        			 
        			 int tmin = Math.round(temp_min);
        			 int tmax = Math.round(temp_max);
        			 
        			 JSONArray jWeatherArr = jDayForecast.getJSONArray("weather");
        			 JSONObject jWeatherObj = jWeatherArr.getJSONObject(0);
        			 String icon = jWeatherObj.getString("icon");
        			 
        			 HashMap<String, String> map = new HashMap<String, String>();
        			 
        			 if (i == 0){
        				 
        			 }
        			 else {
        			 map.put("date", upperString);
        			 map.put("temp_min", Integer.toString(tmin));
        			 map.put("temp_max", Integer.toString(tmax));
        			 map.put("icon", icon);
        			 arraylist_forecast.add(map);
        			 }
        			// Log.d("date", upperString);
        			// Log.d("temp_min", Integer.toString(tmin));
        			// Log.d("temp_max", Integer.toString(tmax));
        			// Log.d("icon", icon);
        			 
        			 
        			 
        			 
        		 }
        	 }
        	 catch (JSONException e){
        		 
        	 }
        	 catch (NullPointerException w){
        		 
        	 }
         
		return null;
	}
	
	
	
	@Override
	protected void onPostExecute(Void result) {		
////WEATHER BASE	
		
		
		
		try{
		if (arraylist_base .get(2).contains("d")){
		
		if (arraylist_base .get(1).contains("Rain")){
			if (arraylist_base .get(0).contains("light rain")){
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.light_rain_d));
				 img1.setTag(R.drawable.d09d);
				 condDescr.setText(R.string.light_rain);
				 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.light_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
				 nnn = getString(R.string.now) + " " + getString(R.string.light_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
				 Editor editor = mSettings.edit();
				   	editor.putString(APP_PREFERENCES_WEATHER, "light_rain").commit();
			}
			else if (arraylist_base .get(0).contains("moderate rain")){
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.moderate_rain_d));
				 img1.setTag(R.drawable.d09d);
				 condDescr.setText(R.string.moderate_rain);
				 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.moderate_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
				 nnn = getString(R.string.now) + " " + getString(R.string.moderate_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
				 Editor editor = mSettings.edit();
				   	editor.putString(APP_PREFERENCES_WEATHER, "moderate_rain").commit();
			}
			else {
				img1.setImageDrawable(getResources().getDrawable(R.drawable.heavy_intensity_rain_d));
				 img1.setTag(R.drawable.d09d);
				condDescr.setText(R.string.heavy_intensity_rain);
				textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.heavy_intensity_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
				nnn = getString(R.string.now) + " " + getString(R.string.heavy_intensity_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
				Editor editor = mSettings.edit();
			   	editor.putString(APP_PREFERENCES_WEATHER, "heavy_intensity_rain").commit();
			}
			}
		else if (arraylist_base .get(1).contains("Clouds")){
				if (arraylist_base .get(0).contains("few clouds")){
					 img1.setImageDrawable(getResources().getDrawable(R.drawable.few_clouds_d));
					 img1.setTag(R.drawable.d03d);
					 condDescr.setText(R.string.few_clouds);
					 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.few_clouds) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
					 nnn = getString(R.string.now) + " " + getString(R.string.few_clouds) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
					 Editor editor = mSettings.edit();
					   	editor.putString(APP_PREFERENCES_WEATHER, "few_clouds").commit();
				}
				else if (arraylist_base .get(0).contains("scattered_clouds")){
					 img1.setImageDrawable(getResources().getDrawable(R.drawable.scattered_clouds_d));
					 img1.setTag(R.drawable.d03d);
					 condDescr.setText(R.string.scattered_clouds);
					 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.scattered_clouds) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
					 nnn = getString(R.string.now) + " " + getString(R.string.scattered_clouds) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
					 Editor editor = mSettings.edit();
					   	editor.putString(APP_PREFERENCES_WEATHER, "scattered_clouds").commit();
				}
				else {
					 img1.setImageDrawable(getResources().getDrawable(R.drawable.broken_clouds_d));
					 img1.setTag(R.drawable.d03d);
					 condDescr.setText(R.string.broken_clouds);
					 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.broken_clouds) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
					 nnn = getString(R.string.now) + " " + getString(R.string.broken_clouds) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
					 Editor editor = mSettings.edit();
					   	editor.putString(APP_PREFERENCES_WEATHER, "broken_clouds").commit();
					 
				}
			}
		else if (arraylist_base .get(1).contains("Clear")){
			 img1.setImageDrawable(getResources().getDrawable(R.drawable.clear_sky_d));
			 img1.setTag(R.drawable.d01d);
			 condDescr.setText(R.string.clear_sky);
			 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.clear_sky) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
			 nnn = getString(R.string.now) + " " + getString(R.string.clear_sky) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
			 Editor editor = mSettings.edit();
			   	editor.putString(APP_PREFERENCES_WEATHER, "clear_sky").commit();
		}	
		else if (arraylist_base .get(1).contains("Snow")){
			if (arraylist_base .get(0).contains("light snow")){
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.snow_d));
				 img1.setTag(R.drawable.d13d);
				 condDescr.setText(R.string.light_snow);
				 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.light_snow) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
				 nnn = getString(R.string.now) + " " + getString(R.string.light_snow) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
				 Editor editor = mSettings.edit();
				   	editor.putString(APP_PREFERENCES_WEATHER, "Snow").commit();
			}
			else if (arraylist_base .get(0).contains("snow")){
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.snow_d));
				 img1.setTag(R.drawable.d13d);
				 condDescr.setText(R.string.snow);
				 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.snow) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
				 nnn = getString(R.string.now) + " " + getString(R.string.snow) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
				 Editor editor = mSettings.edit();
				   	editor.putString(APP_PREFERENCES_WEATHER, "Snow").commit();
			}
			else {
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.snow_d));
				 img1.setTag(R.drawable.d13d);
				 condDescr.setText(R.string.heavy_snow);
				 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.heavy_snow) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
				 nnn = getString(R.string.now) + " " + getString(R.string.heavy_snow) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
				 Editor editor = mSettings.edit();
				   	editor.putString(APP_PREFERENCES_WEATHER, "Snow").commit();
			}
		}
		else if (arraylist_base .get(1).contains("Thunderstorm")){
			 img1.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm));
			 img1.setTag(R.drawable.d11d);
			 condDescr.setText(R.string.light_intensity_shower_rain);
			 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.light_intensity_shower_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
			 nnn = getString(R.string.now) + " " + getString(R.string.light_intensity_shower_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
			 Editor editor = mSettings.edit();
			   	editor.putString(APP_PREFERENCES_WEATHER, "Thunderstorm").commit();
		}
		else if (arraylist_base .get(1).contains("Drizzle")){
			 img1.setImageDrawable(getResources().getDrawable(R.drawable.light_rain_d));
			 img1.setTag(R.drawable.d10d);
			 condDescr.setText(R.string.drizzle);
			 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.drizzle) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
			 nnn = getString(R.string.now) + " " + getString(R.string.drizzle) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
			 Editor editor = mSettings.edit();
			   	editor.putString(APP_PREFERENCES_WEATHER, "Drizzle").commit();
		}
		else if (arraylist_base .get(1).contains("Atmosphere")){
			 img1.setImageDrawable(getResources().getDrawable(R.drawable.few_clouds_d));
			 img1.setTag(R.drawable.d03d);
			 condDescr.setText(R.string.mist);
			 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.mist) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
			 nnn = getString(R.string.now) + " " + getString(R.string.mist) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
			 Editor editor = mSettings.edit();
			   	editor.putString(APP_PREFERENCES_WEATHER, "Atmosphere").commit();
		}
		else if (arraylist_base .get(1).contains("Extreme")){
			 img1.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm));
			 img1.setTag(R.drawable.d11d);
			 condDescr.setText(R.string.mist);
			 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.light_intensity_shower_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
			 nnn = getString(R.string.now) + " " + getString(R.string.light_intensity_shower_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
			 Editor editor = mSettings.edit();
			   	editor.putString(APP_PREFERENCES_WEATHER, "Extreme").commit();
		}
		else if (arraylist_base .get(1).contains("Additional")){
			 img1.setImageDrawable(getResources().getDrawable(R.drawable.clear_sky_d));
			 img1.setTag(R.drawable.d02d);
			 condDescr.setText(R.string.mist);
			 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.clear_sky) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
			 nnn = getString(R.string.now) + " " + getString(R.string.clear_sky) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
			 Editor editor = mSettings.edit();
			   	editor.putString(APP_PREFERENCES_WEATHER, "Additional").commit();
		}
		}
		else {
			if (arraylist_base .get(1).contains("Rain")){
				if (arraylist_base .get(0).contains("light rain")){
					 img1.setImageDrawable(getResources().getDrawable(R.drawable.light_rain_n));
					 img1.setTag(R.drawable.d09d);
					 condDescr.setText(R.string.light_rain);
					 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.light_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
					 nnn = getString(R.string.now) + " " + getString(R.string.light_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
					 Editor editor = mSettings.edit();
					   	editor.putString(APP_PREFERENCES_WEATHER, "light_rain").commit();
				}
				else if (arraylist_base .get(0).contains("moderate rain")){
					 img1.setImageDrawable(getResources().getDrawable(R.drawable.moderate_rain_n));
					 img1.setTag(R.drawable.d09d);
					 condDescr.setText(R.string.moderate_rain);
					 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.moderate_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
					 nnn = getString(R.string.now) + " " + getString(R.string.moderate_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " ;
					 Editor editor = mSettings.edit();
					   	editor.putString(APP_PREFERENCES_WEATHER, "moderate_rain").commit();
				}
				else {
					img1.setImageDrawable(getResources().getDrawable(R.drawable.heavy_intensity_rain_n));
					 img1.setTag(R.drawable.d09d);
					condDescr.setText(R.string.heavy_intensity_rain);
					textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.heavy_intensity_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
					nnn = getString(R.string.now) + " " + getString(R.string.heavy_intensity_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
					Editor editor = mSettings.edit();
				   	editor.putString(APP_PREFERENCES_WEATHER, "heavy_intensity_rain").commit();
				}
				}
			else if (arraylist_base .get(1).contains("Clouds")){
					if (arraylist_base .get(0).contains(getResources().getString(R.string.few_clouds))){
						 img1.setImageDrawable(getResources().getDrawable(R.drawable.few_clouds_n));
						 img1.setTag(R.drawable.d03d);
						 condDescr.setText(R.string.few_clouds);
						 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.few_clouds) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
						 nnn = getString(R.string.now) + " " + getString(R.string.few_clouds) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
						 Editor editor = mSettings.edit();
						   	editor.putString(APP_PREFERENCES_WEATHER, "few_clouds").commit();
					}
					else if (arraylist_base .get(0).contains("scattered_clouds")){
						 img1.setImageDrawable(getResources().getDrawable(R.drawable.scattered_clouds_n));
						 img1.setTag(R.drawable.d03d);
						 condDescr.setText(R.string.scattered_clouds);
						 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.scattered_clouds) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
						 nnn = getString(R.string.now) + " " + getString(R.string.scattered_clouds) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
						 Editor editor = mSettings.edit();
						   	editor.putString(APP_PREFERENCES_WEATHER, "scattered_clouds").commit();
					}
					else {
						 img1.setImageDrawable(getResources().getDrawable(R.drawable.broken_clouds_n));
						 img1.setTag(R.drawable.d03d);
						 condDescr.setText(R.string.broken_clouds);
						 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.broken_clouds) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
						 nnn = getString(R.string.now) + " " + getString(R.string.broken_clouds) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
						 Editor editor = mSettings.edit();
						   	editor.putString(APP_PREFERENCES_WEATHER, "broken_clouds").commit();
					}
				}
			else if (arraylist_base .get(1).contains("Clear")){
					 img1.setImageDrawable(getResources().getDrawable(R.drawable.clear_sky_n));
					 img1.setTag(R.drawable.n01n);
					 condDescr.setText(R.string.clear_sky);
					 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.clear_sky) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
					 nnn = getString(R.string.now) + " " + getString(R.string.clear_sky) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
					 Editor editor = mSettings.edit();
					   	editor.putString(APP_PREFERENCES_WEATHER, "clear_sky").commit();
			}
			else if (arraylist_base .get(1).contains("Snow")){
				if (arraylist_base .get(0).contains("light snow")){
					 img1.setImageDrawable(getResources().getDrawable(R.drawable.snow_n));
					 img1.setTag(R.drawable.n13n);
					 condDescr.setText(R.string.light_snow);
					 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.light_snow) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
					 nnn = getString(R.string.now) + " " + getString(R.string.light_snow) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
					 Editor editor = mSettings.edit();
					   	editor.putString(APP_PREFERENCES_WEATHER, "Snow").commit();
				}
				else if (arraylist_base .get(0).contains("snow")){
					 img1.setImageDrawable(getResources().getDrawable(R.drawable.snow_n));
					 img1.setTag(R.drawable.n13n);
					 condDescr.setText(R.string.snow);
					 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.snow) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
					 nnn = getString(R.string.now) + " " + getString(R.string.snow) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
					 Editor editor = mSettings.edit();
					   	editor.putString(APP_PREFERENCES_WEATHER, "Snow").commit();
				}
				else {
					 img1.setImageDrawable(getResources().getDrawable(R.drawable.snow_n));
					 img1.setTag(R.drawable.n13n);
					 condDescr.setText(R.string.heavy_snow);
					 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.heavy_snow) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
					 nnn = getString(R.string.now) + " " + getString(R.string.heavy_snow) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
					 Editor editor = mSettings.edit();
					   	editor.putString(APP_PREFERENCES_WEATHER, "Snow").commit();
				}
			}
			else if (arraylist_base .get(1).contains("Thunderstorm")){
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm));
				 img1.setTag(R.drawable.n11n);
				 condDescr.setText(R.string.light_intensity_shower_rain);
				 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.light_intensity_shower_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
				 nnn = getString(R.string.now) + " " + getString(R.string.light_intensity_shower_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
				 Editor editor = mSettings.edit();
				   	editor.putString(APP_PREFERENCES_WEATHER, "Thunderstorm").commit();
			}
			else if (arraylist_base .get(1).contains("Drizzle")){
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.light_rain_n));
				 img1.setTag(R.drawable.n11n);
				 condDescr.setText(R.string.drizzle);
				 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.drizzle) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
				 nnn = getString(R.string.now) + " " + getString(R.string.drizzle) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
				 Editor editor = mSettings.edit();
				   	editor.putString(APP_PREFERENCES_WEATHER, "Drizzle").commit();
			}
			else if (arraylist_base .get(1).contains("Atmosphere")){
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.few_clouds_n));
				 img1.setTag(R.drawable.n03n);
				 condDescr.setText(R.string.mist);
				 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.mist) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
				 nnn = getString(R.string.now) + " " + getString(R.string.mist) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
				 Editor editor = mSettings.edit();
				   	editor.putString(APP_PREFERENCES_WEATHER, "Atmosphere").commit();
			}
			else if (arraylist_base .get(1).contains("Extreme")){
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm));
				 img1.setTag(R.drawable.n11n);
				 condDescr.setText(R.string.mist);
				 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.light_intensity_shower_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
				 nnn = getString(R.string.now) + " " + getString(R.string.light_intensity_shower_rain) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". ";
				 Editor editor = mSettings.edit();
				   	editor.putString(APP_PREFERENCES_WEATHER, "Extreme").commit();
			}
			else if (arraylist_base .get(1).contains("Additional")){
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.clear_sky_n));
				 img1.setTag(R.drawable.n01n);
				 condDescr.setText(R.string.mist);
				 textView3.setText(getString(R.string.today) + ": " + getString(R.string.now) + " " + getString(R.string.clear_sky) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " + getString(R.string.wind_speed_text)  + " " + arraylist_base .get(8) + " " + getString(R.string.mps) + ".");
				 nnn = getString(R.string.now) + " " + getString(R.string.clear_sky) + ". " + getString(R.string.temp_text) + " " + arraylist_base.get(7) + "\u2103" + ", " + getString(R.string.max_temp_text) + " " + arraylist_base .get(6) + "\u2103" + ". " ;
				 Editor editor = mSettings.edit();
				   	editor.putString(APP_PREFERENCES_WEATHER, "Additional").commit();
			}
			
		}
		
		
		temp.setText(arraylist_base .get(7) + "\u00B0");
		hum.setText(arraylist_base .get(4) + " %");
		press.setText(arraylist_base .get(3));
		windSpeed.setText(arraylist_base .get(8)+ " " +  getString(R.string.mps));
		cityText.setText(arraylist_base .get(9) + ", " + arraylist_base .get(12));
		tempDay.setText(arraylist_base .get(6) + "\u00B0");
		tempNight.setText(arraylist_base .get(5) + "\u00B0");
		textView9.setText(arraylist_base .get(10));
		textView11.setText(arraylist_base .get(11));
		
		//create_notif();
		
///////FORECAST WEATHER		
			
		
		adapter = new ListViewAdapter(MainActivity.this, arraylist_forecast);
		// Set the adapter to the ListView
		listView1.setAdapter(adapter);
		setListViewHeightBasedOnChildren(listView1);
		
		ScrollView scrollView1 = (ScrollView)findViewById(R.id.scrollView1);
		scrollView1.smoothScrollTo(0,0);
		
//////DAILY WEATHER		
		adapter_daily = new ListViewAdapterDaily(MainActivity.this, arraylist_daily);
		// Set the adapter to the ListView
		listview.setAdapter(adapter_daily);
		
	
		prog1.setVisibility(View.GONE);
		button1.setVisibility(View.VISIBLE);
		
//////////////
		scrollView1.setVisibility(View.VISIBLE);
		main.setVisibility(View.VISIBLE);
		
		
		
		}
		catch (IndexOutOfBoundsException e){
			//Toast.makeText(getApplicationContext(), "Ошибка получения данных!",
			//		   Toast.LENGTH_LONG).show();
			//prog1.setVisibility(View.GONE);
			check_int();
		}
		catch(NullPointerException w){
			Toast.makeText(getApplicationContext(), "Ошибка!",
					   Toast.LENGTH_LONG).show();
			prog1.setVisibility(View.GONE);
		}
		
	}

	
	
}


private void create_notif() {
	// TODO Auto-generated method stub
	// Prepare intent which is triggered if the
    // notification is selected
	String tittle= textView3.getText().toString();
    String subject=textView3.getText().toString();
    String body="";
    /*
    NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    Notification notify=new Notification((Integer)img1.getTag(),tittle,System.currentTimeMillis());
    PendingIntent pending= PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);
    
    notify.setLatestEventInfo(getApplicationContext(),subject,body,pending);
    notif.notify(0, notify);
    
    
    final NotificationManager mgr=
            (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification note=new Notification((Integer)img1.getTag(),
        		"",
                                                        System.currentTimeMillis());
         
        // This pending intent will open after notification click
        PendingIntent i=PendingIntent.getActivity(this, 0,
                                                new Intent(),
                                                0);
         
        note.setLatestEventInfo(this, tittle,
        		"", i);
         
        //After uncomment this line you will see number of notification arrived
        //note.number=2;
        mgr.notify(1, note);
        
        
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Tiket")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Title")
                .setContentText("Context Text")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
*/
    
    
    RemoteViews remoteViews = new RemoteViews(getPackageName(),  
            R.layout.widget);  
  NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(  
            this).setSmallIcon(R.drawable.ic_launcher).setContent(  
            remoteViews);  
  // Creates an explicit intent for an Activity in your app  
  Intent resultIntent = new Intent(this, MainActivity.class);  
  // The stack builder object will contain an artificial back stack for  
  // the  
  // started Activity.  
  // This ensures that navigating backward from the Activity leads out of  
  // your application to the Home screen.  
  TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);  
  // Adds the back stack for the Intent (but not the Intent itself)  
  stackBuilder.addParentStack(MainActivity.class);  
  // Adds the Intent that starts the Activity to the top of the stack  
  stackBuilder.addNextIntent(resultIntent);  
  PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,  
            PendingIntent.FLAG_UPDATE_CURRENT);  
  remoteViews.setOnClickPendingIntent(R.id.button1, resultPendingIntent); 
  remoteViews.setTextViewText(R.id.textView1, nnn);
  remoteViews.setTextColor(R.id.textView1, getResources().getColor(R.color.white));
  remoteViews.setImageViewResource(R.id.imageView1, (Integer)img1.getTag());
  NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
  // mId allows you to update the notification later on.  
  mNotificationManager.notify(100, mBuilder.build()); 
  
  }



	

public static void setListViewHeightBasedOnChildren(ListView lv) {
    ListAdapter listAdapter = lv.getAdapter();
    if (listAdapter == null)
        return;

    int desiredWidth = MeasureSpec.makeMeasureSpec(lv.getWidth(), MeasureSpec.UNSPECIFIED);
    int totalHeight = 0;
    View view = null;
    for (int i = 0; i < listAdapter.getCount(); i++) {
        view = listAdapter.getView(i, view, lv);
        if (i == 0)
            view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));

        view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
        totalHeight += view.getMeasuredHeight();
    }
    ViewGroup.LayoutParams params = lv.getLayoutParams();
    params.height = totalHeight + (lv.getDividerHeight() * (listAdapter.getCount() - 1));
    lv.setLayoutParams(params);
    lv.requestLayout();
}

protected void onResume() {
    super.onResume();
    
    this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
   
    	 String weather = mSettings.getString(APP_PREFERENCES_WEATHER, "Clear");
    	 	if (weather.contains("light_rain")){
    	 		img1.setImageDrawable(getResources().getDrawable(R.drawable.light_rain_d));
    	 	}
    	 	else if(weather.contains("moderate rain")){
    	 		img1.setImageDrawable(getResources().getDrawable(R.drawable.moderate_rain_d));
    	 	}
    	 	else if(weather.contains("heavy_intensity_rain")){
    	 		img1.setImageDrawable(getResources().getDrawable(R.drawable.heavy_intensity_rain_d));
    	 	}
    	 	else if (weather.contains("few_clouds")){
    	 		img1.setImageDrawable(getResources().getDrawable(R.drawable.few_clouds_d));
    	 	}
    	 	else if(weather.contains("scattered_clouds")){
    	 		img1.setImageDrawable(getResources().getDrawable(R.drawable.scattered_clouds_d));
    	 	}
    	 	else if (weather.contains("broken_clouds")){
    	 		img1.setImageDrawable(getResources().getDrawable(R.drawable.broken_clouds_d));
    	 	}
    	 	else if(weather.contains("Clear")){
    	 		img1.setImageDrawable(getResources().getDrawable(R.drawable.clear_sky_d));
    	 	}
    	 	else if(weather.contains("Snow")){
    	 		img1.setImageDrawable(getResources().getDrawable(R.drawable.snow_d));
    	 	}
    	 	else if(weather.contains("Thunderstorm")){
    	 		img1.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm));
    	 	}
    	 	else if(weather.contains("Drizzle")){
    	 		img1.setImageDrawable(getResources().getDrawable(R.drawable.light_rain_d));
    	 	}
    	 	else if(weather.contains("Atmosphere")){
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.few_clouds_d));
    	 	}
    	 	else if(weather.contains("Extreme")){
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm));
    	 	}
    	 	else if(weather.contains("Additional")){
				 img1.setImageDrawable(getResources().getDrawable(R.drawable.clear_sky_d));
    	 	}
    	 	
    	 	String city = mSettings.getString(APP_PREFERENCES_CITY, "Kyiv");
			city = city.replace(" ","_");
			Log.i("", city);
			
			String adress = mSettings.getString(APP_PREFERENCES_ADRESS, "q");
			Log.d("", adress);
			if(adress.contains("id")){
			url_forecast = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&id=" + city + "&units=metric";
			url_base = "http://api.openweathermap.org/data/2.5/weather?id=" + city + "&units=metric";
			url_daily = "http://api.openweathermap.org/data/2.5/forecast?id=" + city + "&units=metric";
			}
			else {
				url_forecast = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&q=" + city + "&units=metric";
				url_base = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric";
				url_daily = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&units=metric";
			}
    	 	check_int();
    	 	
    	 	top_bar();
    	 	
}
	
private void top_bar() {
	/////TIME
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	String currentDateandTime = sdf.format(new Date());
	textView14.setText(currentDateandTime);
	
	////OPERATOR
	TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	   textView7.setText(tManager.getSimOperatorName());
	
}


public void check_int(){
	ConnectivityManager conMgr  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); 
	 NetworkInfo info = conMgr.getActiveNetworkInfo(); 

	if(info != null && info.isConnected()) 
	{
		GetForecastWeather task2 = new GetForecastWeather();
		task2.execute();
	}
	else
	{
		Toast.makeText(getApplicationContext(), getString(R.string.no_internet),
				   Toast.LENGTH_LONG).show();
	}
	
}


@Override
public boolean onKeyDown(int keycode, KeyEvent e) {
    switch(keycode) {
        case KeyEvent.KEYCODE_BACK:
        	Intent intent = new Intent(Intent.ACTION_MAIN);
        	intent.addCategory(Intent.CATEGORY_HOME);
        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(intent);
        	return true;
    }
    return super.onKeyDown(keycode, e);
}


public class myPhoneStateListener extends PhoneStateListener {
    public int signalStrengthValue;

    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        if (signalStrength.isGsm()) {
            if (signalStrength.getGsmSignalStrength() != 99)
                signalStrengthValue = signalStrength.getGsmSignalStrength() * 2 - 113;
            else
                signalStrengthValue = signalStrength.getGsmSignalStrength();
        } else {
            signalStrengthValue = signalStrength.getCdmaDbm();
        }
        
        ImageView i = (ImageView) findViewById(R.id.imageView3);
        
        int signalStrengthValue2 = signalStrengthValue * -1;
        
        if(signalStrengthValue2 > 85){
        	i.setImageDrawable(getResources().getDrawable(R.drawable.s5));
        }
        else if (signalStrengthValue2 > 75){
        	i.setImageDrawable(getResources().getDrawable(R.drawable.s4));
        }
        else if (signalStrengthValue2 > 50){
        	i.setImageDrawable(getResources().getDrawable(R.drawable.s3));
        }
        else if (signalStrengthValue2 > 25){
        	i.setImageDrawable(getResources().getDrawable(R.drawable.s2));
        }
        else {
        	i.setImageDrawable(getResources().getDrawable(R.drawable.s1));
        }
        
    }
}


private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
    @Override
    public void onReceive(Context ctxt, Intent intent) {
      int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
      textView15.setText(String.valueOf(level) + " %");
      
      ImageView im4 = (ImageView) findViewById(R.id.imageView4);
      if (level == 100){
    	  im4.setImageDrawable(getResources().getDrawable(R.drawable.b_100));
      }
      else if (level > 90){
    	  im4.setImageDrawable(getResources().getDrawable(R.drawable.b_90));
      }
      else if (level > 70){
    	  im4.setImageDrawable(getResources().getDrawable(R.drawable.b_70));
      }
      else if (level > 50){
    	  im4.setImageDrawable(getResources().getDrawable(R.drawable.b_50));
      }
      else if (level > 30){
    	  im4.setImageDrawable(getResources().getDrawable(R.drawable.b_30));
      }
      else{
    	  im4.setImageDrawable(getResources().getDrawable(R.drawable.b_10));
      }
      
      int status = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
      
     if (status == BatteryManager.BATTERY_STATUS_CHARGING){
    	 im4.setImageDrawable(getResources().getDrawable(R.drawable.b_ch));
      } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING){
     
      } else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING){
    
      } else if (status == BatteryManager.BATTERY_STATUS_FULL){
     
     } else {
    
      }
    }
  };
  
  protected void onPause() {
	    super.onPause();
	    this.unregisterReceiver(mBatInfoReceiver);
  }
  

}




