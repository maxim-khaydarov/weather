package ua.mkh.weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;

public class AllCityActivity extends Activity  implements OnClickListener{
	
	
	Typeface typefaceRoman, typefaceMedium, typefaceBold, typefaceThin, typefaceUltra;
	public static final String APP_PREFERENCES = "mysettings"; 
	SharedPreferences mSettings;
	ListView lvMain;
	Button btn_plus, btn_c_f;
	
	private DatabaseHelperCityMain mDatabaseHelper;
	//private SQLiteDatabase mSqLiteDatabase;
	
	final ArrayList<String> catnames = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	List<City> list;
	String u = null;
	
	JSONObject jsonobject;
	ArrayList <HashMap<String, String>> arraylist;
	ProgressBar prog1;
	
	ArrayList<ItemDetails> results;
	
	ArrayAdapter<HashMap<String, String>> array_adapter;
	  // ArrayList <String> NewsArrayList;
	   HashMap<String, String> map;
	   SimpleAdapter adapters;
	
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
		
		btn_plus = (Button) findViewById(R.id.button2);
		btn_plus.setOnClickListener(this);
		btn_c_f = (Button) findViewById(R.id.button1);
		btn_c_f.setOnClickListener(this);
		
		mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
		
		 mDatabaseHelper = new DatabaseHelperCityMain(this);
		 prog1 = (ProgressBar) findViewById(R.id.progressBar1);
		 
		 arraylist = new ArrayList<HashMap<String, String>>();
		 
		 top_bar();

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
			
			check_int();
			
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
				Toast.makeText(AllCityActivity.this, "You have chosen : " + " " + obj_itemDetails.getCity(), Toast.LENGTH_LONG).show();
				
				}
				
				});


		 //load_base();
	}
	
	private void load_base() {
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
	    	
	   
	}
	}
	
	
	private class GetForecastWeather extends AsyncTask<Void, Void, Void>  {

		
		@Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        try{
	        	
	        prog1.setVisibility(View.VISIBLE);
	        
	        
	        }
	        catch(NullPointerException e){
	        	
	        }
	    }
	@Override
	protected Void doInBackground(Void... arg0)  {
		//DAILY WEATHER
		
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
		
		String url = "http://api.openweathermap.org/data/2.5/group?id=" + u + "&units=metric&APPID=0e9e2449bc7a756fad899235dfae7206";

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
   		 
   		results = new ArrayList<ItemDetails>();

   		

   		 //Loop the Array
   		for (int i = 0; i < list.length(); i++) {
   			JSONObject JSONWeather = list.getJSONObject(i);
   			ItemDetails item_details = new ItemDetails();
   			map = new HashMap<String, String>();
   			
   			JSONObject cordObj = JSONWeather.getJSONObject("coord");
   			int lon = cordObj.getInt("lon");
   			
   			
   			JSONObject sysObj = JSONWeather.getJSONObject("sys");
   			String country = sysObj.getString("country");
   			
   			
   			
   			
   			JSONArray sysObjWeather = JSONWeather.getJSONArray("weather");
   			
   			for (int k = 0; k < sysObjWeather.length(); k++){
   			JSONObject JSONWeather2 = sysObjWeather.getJSONObject(k);
   			String description = JSONWeather2.getString("description");
   			String main = JSONWeather2.getString("main");
   			map.put("description", description);
   			item_details.setWeather(description);
   			map.put("main", main);
   			Log.e("main", main);
   			}
   			
   			
   			
   			
   			JSONObject sysObjMain = JSONWeather.getJSONObject("main");
   			String temp = Integer.toString(Math.round(sysObjMain.getInt("temp")));
   			
			 Log.d("temp", temp);
   			
   			
   			String city_b = JSONWeather.getString("name");
   			Log.e("name", city_b);
      		 long dt = JSONWeather.getLong("dt");
      		 
   	 
      		 
      		 
      		 boolean mine = false;
      		 int dodatkovo = 0;
      		 if (lon < 0){
      			mine = true;
      			lon = lon * -1;
      		 }
      		 
      		 if(lon < 7.5 ){
      			 dodatkovo = 0;
      		 }
      		 else if (lon > 7.5 && lon < 22.5){
      			 dodatkovo = 1;
      		 }
      		 else if (lon > 22.5 && lon < 37.5){
      			 dodatkovo = 2;
      		 }
      		 else if (lon > 37.5 && lon < 52.5){
      			 dodatkovo = 3;
      		 }
      		 else if(lon > 52.5 && lon < 67.5){
      			 dodatkovo = 4;
      		 }
      		 else if(lon > 67.5 && lon < 82.5){
      			 dodatkovo = 5;
      		 }
      		 else if (lon > 82.5 && lon < 97.5){
      			 dodatkovo = 6;
      		 }
      		 else if (lon >97.5 && lon < 105){
      			 dodatkovo = 7;
      		 }
      		 else if(lon > 105 && lon < 127.5){
      			 dodatkovo = 8;
      		 }
      		 else if (lon > 127.5 && lon < 142.5){
      			 dodatkovo = 9;
      		 }
      		 else if (lon > 142.5 && lon < 157.5){
      			 dodatkovo = 10;
      		 }
      		 else if(lon > 157.5 && lon < 172.5){
      			 dodatkovo = 11;
      		 }
      		 else if (lon > 172.5 && lon < 180){
      			 dodatkovo = 12;
      		 }
      			 
      			 
      		Date date = new Date(dt*1000L);
      		
      		
      		 SimpleDateFormat hourformat = new SimpleDateFormat("HH"); // the format of your date
      		 SimpleDateFormat minformat = new SimpleDateFormat("mm");
      		String hour;
      		 if (mine == true){
      		 hour = String.valueOf(Integer.parseInt(hourformat.format(date)) - dodatkovo);
      		 }
      		 else{
      			 hour = String.valueOf(Integer.parseInt(hourformat.format(date)) + dodatkovo);
      		 }
      		String minute = minformat.format(date);
   		 
      		int time = Integer.parseInt(hour + minute);
      		if (time > 2359){
      			time = time - 24;
      		}
      		
      		map.put("name", city_b);
      		map.put("date", String.valueOf(time));
   			map.put("country", country);
   			map.put("temp", temp);
   			item_details.setCity(city_b);
   			item_details.setTime(String.valueOf(time));
   			item_details.setCountry(country);
   			item_details.setTemp(temp);
   			results.add(item_details);

   			
   			arraylist.add(map);
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
	
	
	
	private String ValueOf(int round) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void onPostExecute(Void result) {		
////WEATHER BASE	
		try{
		adapters = new SimpleAdapter(AllCityActivity.this, arraylist,
	            R.layout.row_all_city, new String[] { "temp", "date",
	                    "name", "country" }, new int[] { R.id.temp, R.id.time,
	                    R.id.city, R.id.country });
		
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
		//textView14.setText(currentDateandTime);
		
		////OPERATOR
		TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		   //textView7.setText(tManager.getSimOperatorName());
		
	}
	
	
	
	 
}
