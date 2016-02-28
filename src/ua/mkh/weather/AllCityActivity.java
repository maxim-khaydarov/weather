package ua.mkh.weather;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import ua.mkh.weather.MainActivity.myPhoneStateListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;

public class AllCityActivity extends Activity  implements OnClickListener{
	
	
	Typeface typefaceRoman, typefaceMedium, typefaceBold, typefaceThin, typefaceUltra;
	public static final String APP_PREFERENCES = "mysettings"; 
	public static final String APP_PREFERENCES_CITY = "mysettings_city";
	public static final String APP_PREFERENCES_TEMP = "temp";
	SharedPreferences mSettings;
	ListView lvMain;
	Button btn_plus;
	Button btn_c_f;
	private DatabaseHelperCityMain mDatabaseHelper;
	//private SQLiteDatabase mSqLiteDatabase;
	
	MyProgressBarGreen battery_green;
	MyProgressBarWhite battery_white;
	MyProgressBarYellow battery_yellow;
	
	final ArrayList<String> catnames = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	List<City> list;
	String u = null;
	
	String temp_n;
	
	
	JSONObject jsonobject;
	ArrayList <HashMap<String, String>> arraylist;
	ProgressBar prog1;
	
	ArrayList<ItemDetails> results;
	String[] stringArray;
	
	ArrayAdapter<HashMap<String, String>> array_adapter;
	  // ArrayList <String> NewsArrayList;
	   HashMap<String, String> map;
	   SimpleAdapter adapters;
	   
	   TelephonyManager telephonyManager;
		myPhoneStateListener psListener;
		
		TextView textView7, textView14, textView15;
		 
	
		int a;
		String c = "<b>" + "°C" + "</b> " + " / °F"; 
		String f = "°C / " + "<b>" + "°F" + "</b>"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_all_city);
		
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
		
		textView14 = (TextView) findViewById(R.id.textView14);
		textView15 = (TextView) findViewById(R.id.textView15);
		textView7 = (TextView) findViewById(R.id.textView7);
		
		btn_plus = (Button) findViewById(R.id.button2);
		btn_plus.setOnClickListener(this);
		btn_c_f = (Button) findViewById(R.id.button1);
		btn_c_f.setOnClickListener(this);
		
		btn_c_f.setTypeface(typefaceThin);
		btn_plus.setTypeface(typefaceThin);
		
		battery_green = (MyProgressBarGreen) findViewById(R.id.progressBarGreen);
	    battery_white = (MyProgressBarWhite) findViewById(R.id.progressBarWhite);
	    battery_yellow = (MyProgressBarYellow) findViewById(R.id.progressBarYellow);
		
	    battery_green.setVisibility(View.INVISIBLE);
	    battery_white.setVisibility(View.VISIBLE);
	    battery_yellow.setVisibility(View.INVISIBLE);
		
		
		
		
		mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
		
		 mDatabaseHelper = new DatabaseHelperCityMain(this);
		 prog1 = (ProgressBar) findViewById(R.id.progressBar1);
		 
		 arraylist = new ArrayList<HashMap<String, String>>();
		 
		 //top_bar();
		 results = new ArrayList<ItemDetails>();
		
		 
		 psListener = new myPhoneStateListener();
		 telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		 telephonyManager.listen(psListener,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		
		 check_int();

		   // mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
/*
		    ContentValues newValues = new ContentValues();
		    // Задайте значения для каждого столбца
		    newValues.put(DatabaseHelperCityMain.NAME_CITY, "Рыжик");
		    newValues.put(DatabaseHelperCityMain.ID_CITY, "3");
		    // Вставляем данные в таблицу
		    mSqLiteDatabase.insert(DatabaseHelperCityMain.DATABASE_TABLE, null, newValues);
		    
		    
		    mDatabaseHelper.addBook(new City("Kremenchug", "123")); 
			mDatabaseHelper.addBook(new City("Kiev", "321"));
			mDatabaseHelper.addBook(new City("Lviv", "132"));
			*/
		 // get all books
		    
		 			
			lvMain = (ListView) findViewById(R.id.listView1);
			//lvMain.setAdapter(adapters);
			
			//load_base();
			
			
			
			// get all books
			//mDatabaseHelper.getAllBooks();
			
			/*
			lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
						long id) {
					TextView textView = (TextView) itemClicked;
					String strText = textView.getText().toString(); // получаем текст нажатого элемента
					
					Log.d("CLICK", String.valueOf(position) + " - " + strText);
					// delete one book
					mDatabaseHelper.deleteBook(list.get(position));
					load_base();
				}
			});
			*/
			lvMain.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				Object o = lvMain.getItemAtPosition(position);
				
				ItemDetails obj_itemDetails = (ItemDetails)o;
				//Toast.makeText(AllCityActivity.this, "You have chosen : " + " " + obj_itemDetails.getCityId(), Toast.LENGTH_LONG).show();
				Editor editor = mSettings.edit();
			   	editor.putString(APP_PREFERENCES_CITY, obj_itemDetails.getCityId()).commit();
			   	Intent intent = new Intent(AllCityActivity.this, MainActivity.class);
       	    	startActivity(intent);
				}
				
				});
			
			lvMain.setOnItemLongClickListener(new OnItemLongClickListener() {

	            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
	                    int pos, long id) {
	                // TODO Auto-generated method stub

	            	//Toast.makeText(AllCityActivity.this, pos, Toast.LENGTH_LONG).show();
	            	
	            	
	            	mDatabaseHelper.deleteBook(list.get(pos));
	            	results.remove(pos);
	            	lvMain.setAdapter(new ItemListBaseAdapter(AllCityActivity.this, results));
	                return true;
	            }
	        }); 


		 //load_base();
	}
	
	private void load_base() {
		
		 list = mDatabaseHelper.getAllBooks();
			
		 stringArray = new String[list.size()];
		 
			for (int i=0; i < list.size(); i++) {
			   stringArray[i] = list.get(i).toString();
			   if (u == null){
				   u = list.get(i).toString();
			   }
			   else{
			   u = u + "," + list.get(i).toString();
			   }
			   Log.d("LOAD", u);
			}
			//adapter = new ArrayAdapter<String>(this,
			//		android.R.layout.simple_list_item_1, stringArray);

	    //lvMain.setAdapter(adapter);
	   // setListViewHeightBasedOnChildren(lvMain);
	    
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())  { 
	     	
		case R.id.button2:
			Intent intent = new Intent(this, PreferenceActivity.class);
	    	startActivity(intent);
	    	break;
	    	
		case R.id.button1:
			if(a == 0){
				btn_c_f.setText(Html.fromHtml(c));
				Editor editor = mSettings.edit();
			   	editor.putString(APP_PREFERENCES_TEMP, "c").commit();
				a=1;} 
				else{
				btn_c_f.setText(Html.fromHtml(f));
				Editor editor = mSettings.edit();
			   	editor.putString(APP_PREFERENCES_TEMP, "f").commit();
				a=0; }
			Intent intentg = getIntent();
			finish();
			startActivity(intentg);
	   break;
	}
	}
	
	
	private class GetForecastWeather extends AsyncTask<Void, Void, Void>  {

		
		@Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        try{
	        	
	        prog1.setVisibility(View.VISIBLE);
	        btn_c_f.setVisibility(View.GONE);
	        btn_plus.setVisibility(View.GONE);
	        
	        }
	        catch(NullPointerException e){
	        	
	        }
	    }
	@Override
	protected Void doInBackground(Void... arg0)  {
		//DAILY WEATHER
		
		
		
		/*
		Log.e("START", "!!!!!!!!!!");
		try{
		list = mDatabaseHelper.getAllBooks();
		
		String[] stringArray = new String[list.size()];
		for (int i=0; i < list.size(); i++) {
		   stringArray[i] = list.get(i).toString();
		   if (u == null){
			   u = list.get(i).toString();
		   }
		   else{
		   u = u + "," + list.get(i).toString();
		   }
		   Log.d("LOAD", u);
		}
		
		}
		catch (NullPointerException e){
			
		}
		/////////////////////////
		
		*/
		
		load_base();
		
		
		String url = "http://api.openweathermap.org/data/2.5/group?id=" + u + temp_n + "&APPID=0e9e2449bc7a756fad899235dfae7206";

		Log.e("START", url);
	//BASE WEATHER
		ServiceHandler sh = new ServiceHandler();
		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
		
		
		
		
		// Retrieve JSON Objects from the given URL address
		
		//jsonobject_b = JSONfunctions
		//		.getJSONfromURL(url_base);
		
		try {
			
			//map = new HashMap<String, String>();
			
			JSONObject jsonObj = new JSONObject(jsonStr);
   		 
   		 JSONArray  list = jsonObj.getJSONArray("list");
   		 
   		
   		

   		 //Loop the Array
   		for (int i = 0; i < list.length(); i++) {
   			JSONObject JSONWeather = list.getJSONObject(i);
   			ItemDetails item_details = new ItemDetails();
   			map = new HashMap<String, String>();
   			
   			JSONObject cordObj = JSONWeather.getJSONObject("coord");
   			String lon = cordObj.getString("lon");
   			Log.e("LON", lon);
   			
   			
   			JSONObject sysObj = JSONWeather.getJSONObject("sys");
   			String country = sysObj.getString("country");
   			
   			
   			
   			
   			JSONArray sysObjWeather = JSONWeather.getJSONArray("weather");
   			
   			for (int k = 0; k < sysObjWeather.length(); k++){
   			JSONObject JSONWeather2 = sysObjWeather.getJSONObject(k);
   			String description = JSONWeather2.getString("description");
   			String main = JSONWeather2.getString("main");
   			int id = JSONWeather2.getInt("id");
   			String icon = JSONWeather2.getString("icon").substring(2);
   			//map.put("description", description);
   			item_details.setWeather(description);
   			item_details.setIcon(icon);
   			item_details.setId(String.valueOf(id));
   			//map.put("main", main);
   			Log.e("main", main);
   			}
   			
   			
   			
   			
   			JSONObject sysObjMain = JSONWeather.getJSONObject("main");
   			String temp = Integer.toString(Math.round(sysObjMain.getInt("temp")));
   			
			 Log.d("temp", temp);
   			
   			
   			String city_b = JSONWeather.getString("name");
   			String city_id = JSONWeather.getString("id");
   			Log.e("name", city_b);
      		// long dt = JSONWeather.getLong("dt");
      		 
   			boolean y = TimeZone.getDefault().inDaylightTime( new Date() );
   			
   			
   			
   			
   			
   			
   			
   			final Date gmt = new Timestamp(System.currentTimeMillis()
   		            - Calendar.getInstance().getTimeZone()
   		                    .getOffset(System.currentTimeMillis()));
   			
   			Calendar calendar = Calendar.getInstance();
   			calendar.setTime(gmt);
   			
   			
   			
   			
   			
      		 
      		 boolean mine = false;
      		float ll = Float.parseFloat(lon);
      		 if (ll < 0){
      			mine = true;
      			ll = ll * -1;
      		 }
      		 
      		 if(ll < 7.5 ){
      			//cal.add(Calendar.HOUR, 0);
      			calendar.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
      		 }
      		 else if (ll > 7.6 && ll < 22.5){
      			if (mine == true){
      				//cal.setTimeZone(TimeZone.getTimeZone("GMT-01:00"));
      				calendar.add(Calendar.HOUR, 23);
         		 }
      			else
      				//cal.setTimeZone(TimeZone.getTimeZone("GMT+01:00"));
      				calendar.add(Calendar.HOUR, 1);
      		 }
      		 else if (ll > 22.6 && ll < 37.5){
      			 Log.d("LON", "> 22.6 and <37.5");
      			if (mine == true){
      				calendar.add(Calendar.HOUR, 22);
         		 }
      			else
      				calendar.add(Calendar.HOUR, 2);
      		 }
      		 else if (ll > 37.6 && ll < 52.5){
      			 Log.d("LON", "> 37.6 and <52.5");
      			if (mine == true){
      				calendar.add(Calendar.HOUR, 21);
         		 }
      			else
      				calendar.add(Calendar.HOUR, 3);
      		 }
      		 else if(ll > 52.6 && ll < 67.5){
      			if (mine == true){
      				calendar.add(Calendar.HOUR, 20);
         		 }
      			else
      				calendar.add(Calendar.HOUR, 4);
      		 }
      		 else if(ll > 67.6 && ll < 82.5){
      			if (mine == true){
      				calendar.add(Calendar.HOUR, 19);
         		 }
      			else
      				calendar.add(Calendar.HOUR, 5);
      		 }
      		 else if (ll > 82.6 && ll < 97.5){
      			if (mine == true){
      				calendar.add(Calendar.HOUR, 18);
         		 }
      			else
      				calendar.add(Calendar.HOUR, 6);
      		 }
      		 else if (ll >97.6 && ll < 105){
      			if (mine == true){
      				calendar.add(Calendar.HOUR, 17);
         		 }
      			else
      				calendar.add(Calendar.HOUR, 7);
      		 }
      		 else if(ll > 105.1 && ll < 127.5){
      			if (mine == true){
      				calendar.add(Calendar.HOUR, 16);
         		 }
      			else
      				calendar.add(Calendar.HOUR, 8);
      		 }
      		 else if (ll > 127.6 && ll < 142.5){
      			if (mine == true){
      				calendar.add(Calendar.HOUR, 15);
         		 }
      			else
      				calendar.add(Calendar.HOUR, 9);
      		 }
      		 else if (ll > 142.6 && ll < 157.5){
      			if (mine == true){
      				calendar.add(Calendar.HOUR, 14);
         		 }
      			else
      				calendar.add(Calendar.HOUR, 10);
      		 }
      		 else if(ll > 157.6 && ll < 172.5){
      			if (mine == true){
      				calendar.add(Calendar.HOUR, 13);
         		 }
      			else
      				calendar.add(Calendar.HOUR, 11);
      		 }
      		 else if (ll > 172.6 && ll < 180){
      			if (mine == true){
      				calendar.add(Calendar.HOUR, 12);
         		 }
      			else
      				calendar.add(Calendar.HOUR, 12);
      		 }
      		 /*
      		if (y == true){
      			calendar.add(Calendar.HOUR, 1);}*/
      			 
      		Date date = calendar.getTime();
   			String date1 = (new SimpleDateFormat("HH:mm")).format(date);
      		
      		
   		
      		/*
      		map.put("name", city_b);
      		map.put("date", date1);
   			map.put("country", country);
   			map.put("temp", temp+ "\u00B0");*/
   			item_details.setCity(city_b);
   			item_details.setTime(date1);
   			item_details.setCountry(country);
   			item_details.setTemp(temp+ "\u00B0");
   			item_details.setCityId(city_id);
   			results.add(item_details);

   			
   			//arraylist.add(map);
   		}
   		
   		
		  
		}
		catch (JSONException e){
			e.printStackTrace();
	   	 }
		catch (NullPointerException w){
			Log.e("Error", "NULL");
   	 }
	
		return null;
	}
	
	
	
	
	@Override
	protected void onPostExecute(Void result) {		
////WEATHER BASE	
		try{
			 btn_c_f.setVisibility(View.VISIBLE);
		        btn_plus.setVisibility(View.VISIBLE);
		/*adapters = new SimpleAdapter(AllCityActivity.this, arraylist,
	            R.layout.row_all_city, new String[] { "temp", "date",
	                    "name", "country" }, new int[] { R.id.temp, R.id.time,
	                    R.id.city, R.id.country });
		*/
		lvMain.setAdapter(new ItemListBaseAdapter(AllCityActivity.this, results));
		//lvMain.setAdapter(adapters);
		setListViewHeightBasedOnChildren(lvMain);
		}
		catch (NullPointerException r){
			
		}
		prog1.setVisibility(View.GONE);
	
	}
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

	private void top_bar() {
		/////TIME
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String currentDateandTime = sdf.format(new Date());
		textView14.setText(currentDateandTime);
		
		////OPERATOR
		TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		   textView7.setText(tManager.getSimOperatorName());
		   if(textView7.getText().toString().length() == 0){
			   textView7.setText(R.string.no_sim);
			   ImageView i = (ImageView) findViewById(R.id.imageView3);
			   i.setVisibility(View.GONE);
		   }
		
	}
	
	
	 @Override
	    public boolean onKeyDown(int keycode, KeyEvent e) {
	        switch(keycode) {
	            
	            case KeyEvent.KEYCODE_BACK:
	            	Intent intent18 = new Intent(this, MainActivity.class);
	             	 startActivity(intent18);

	                return true;
	            
	        }
	        return super.onKeyDown(keycode, e);
	   }
	 
	 private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
		    @Override
		    public void onReceive(Context ctxt, Intent intent) {
		      int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		      textView15.setText(String.valueOf(level) + " %");
		      
		      
		      
		      
		      /*
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
		      */
		      
		      ImageView img4 = (ImageView) findViewById(R.id.imageView4);
		      int status = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
		      
		     if (status == BatteryManager.BATTERY_STATUS_CHARGING){
		    	 battery_green.setVisibility(View.VISIBLE);
				    battery_white.setVisibility(View.INVISIBLE);
				    battery_yellow.setVisibility(View.INVISIBLE);
					battery_green.setProgress(level);
					img4.setVisibility(View.VISIBLE);
					
		      } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING){
		     
		    	  battery_green.setVisibility(View.INVISIBLE);
				    battery_white.setVisibility(View.VISIBLE);
				    battery_yellow.setVisibility(View.INVISIBLE);
					battery_white.setProgress(level);
					img4.setVisibility(View.GONE);
					
		      } else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING){
				    
		    	  battery_green.setVisibility(View.INVISIBLE);
				    battery_white.setVisibility(View.VISIBLE);
				    battery_yellow.setVisibility(View.INVISIBLE);
					battery_white.setProgress(level);
					img4.setVisibility(View.GONE);
					
		      } else if (status == BatteryManager.BATTERY_STATUS_FULL){
		    	  battery_green.setVisibility(View.INVISIBLE);
				    battery_white.setVisibility(View.VISIBLE);
				    battery_yellow.setVisibility(View.INVISIBLE);
					battery_white.setProgress(level);
					img4.setVisibility(View.GONE);
		     } else {
		    
		      }
		    }
		  };
		  
		  protected void onPause() {
			    super.onPause();
			    this.unregisterReceiver(mBatInfoReceiver);
			    this.unregisterReceiver(mTimeInfoReceiver);
		  }
		  
		  protected void onResume() {
			    super.onResume();
			    
			    
			    this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			   
			    IntentFilter filtertime = new IntentFilter();
			    filtertime.addAction("android.intent.action.TIME_TICK");
			    registerReceiver(mTimeInfoReceiver, filtertime);
			    /*
			    if(a == 1)
					btn_c_f.setText(Html.fromHtml(c));
					else
					btn_c_f.setText(Html.fromHtml(f));
			   	*/
			   	String temp = mSettings.getString(APP_PREFERENCES_TEMP, "c");
			   	
			   	if (temp.contains("c")){
			   		temp_n = "&units=metric";
			   		btn_c_f.setText(Html.fromHtml(c));
			   		a = 1;
			   	}
			   	else{
			   		temp_n = "&units=imperial";
			   		a = 0;
			   		btn_c_f.setText(Html.fromHtml(f));
			   	}
			   	/*
			   	if(a == 1)
					btn_c_f.setText(Html.fromHtml(c));
					else
					btn_c_f.setText(Html.fromHtml(f));
			   	*/
			    top_bar();
			    //check_int();
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
			        
			        if(signalStrengthValue2 > 50){
			        	i.setImageDrawable(getResources().getDrawable(R.drawable.s5));
			        }
			        else if (signalStrengthValue2 > 40){
			        	i.setImageDrawable(getResources().getDrawable(R.drawable.s4));
			        }
			        else if (signalStrengthValue2 > 30){
			        	i.setImageDrawable(getResources().getDrawable(R.drawable.s3));
			        }
			        else if (signalStrengthValue2 > 15){
			        	i.setImageDrawable(getResources().getDrawable(R.drawable.s2));
			        }
			        else if (signalStrengthValue2 > 0){
			        	i.setImageDrawable(getResources().getDrawable(R.drawable.s1));
			        }
			        
			        else {
			        	i.setVisibility(View.GONE);
			        }
			        
			    }
			}
		  
		  private BroadcastReceiver mTimeInfoReceiver = new BroadcastReceiver(){
			    @Override
			    public void onReceive(Context ctxt, Intent intent) {
			    	
			    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			   	 String currentDateandTime4 = sdf.format(new Date());
			   	 
						textView14.setText(currentDateandTime4);
					
			  }
			};

	 
}
