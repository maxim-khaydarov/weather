package ua.mkh.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import ua.mkh.weather.ItemArrayAdapter.ItemViewHolder;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;


public class PreferenceActivity extends Activity  implements OnClickListener{
	
	
	Typeface typefaceRoman, typefaceMedium, typefaceBold, typefaceThin, typefaceUltra;
	
	public static final String APP_PREFERENCES_CITY = "mysettings_city"; 
	public static final String APP_PREFERENCES = "mysettings"; 
	public static final String APP_PREFERENCES_VISITED = "visited"; 
	public static final String APP_PREFERENCES_ADRESS = "adress";

	private ArrayList<String> arraylist=new ArrayList<String>();
	
	ListView listview;
	
	   SharedPreferences mSettings;
	   
	   Button button1;

	   EditText edt;
	   TextView textView1, textView2;
	   
	   private DatabaseHelper mDatabaseHelper;
	   private SQLiteDatabase mSqLiteDatabase;
	   
	   
	   
	   List<String[]> scoreList ;
	   
	   ArrayAdapter<String> adapter;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_pref);
		
		String roman = "fonts/Regular.otf";
		String medium = "fonts/Medium.otf";
		String bold =  "fonts/Bold.otf";
		String thin = "fonts/Thin.otf";
		String ultra = "fonts/UltraLight.otf";
		typefaceRoman = Typeface.createFromAsset(getAssets(), roman);
		typefaceMedium = Typeface.createFromAsset(getAssets(), medium);
		typefaceBold = Typeface.createFromAsset(getAssets(), bold);
		typefaceThin = Typeface.createFromAsset(getAssets(), thin);
		typefaceUltra = Typeface.createFromAsset(getAssets(), ultra);
		
		edt = (EditText) findViewById(R.id.cityEdtText);
		
		adapter = new ArrayAdapter<String>(this, R.layout.list_country, arraylist)
	    		;
		 
		mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
		
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		
		listview = (ListView) findViewById(R.id.listView1);
		
		textView1.setTypeface(typefaceRoman);
		textView2.setTypeface(typefaceThin);
		button1.setTypeface(typefaceRoman);
		
		
		 mDatabaseHelper = new DatabaseHelper(this, "PrdouctDB.db", null, 1);

		   mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
		
		   
		   listview.setOnItemClickListener(new OnItemClickListener()
           {
                    // argument position gives the index of item which is clicked
                   public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
                   {
                       
                           String selectedAnimal=arraylist.get(position);
                           
                           int endIndex = selectedAnimal.lastIndexOf(",");
                           if (endIndex != -1)  
                           {
                        	   String result = selectedAnimal.split(",")[0];
                               //String newstr = selectedAnimal.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
                               
                               Editor editor = mSettings.edit();
                      		   	editor.putString(APP_PREFERENCES_CITY, result).commit();
                      		   	editor.putString(APP_PREFERENCES_ADRESS, "id").commit();
                      		   	Log.d("",result );
                           }
                           
                           
               		   	
               		   	Intent intent = new Intent(PreferenceActivity.this, MainActivity.class);
               	    	startActivity(intent);
                           //Toast.makeText(getApplicationContext(), "Animal Selected : "+selectedAnimal,   Toast.LENGTH_LONG).show();
                        }
           });
		
        edt.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				populateListview(s.toString());
				if (s.toString().length() == 0){
					textView2.setVisibility(View.VISIBLE);
					adapter.clear();
				}
				else{
					textView2.setVisibility(View.GONE);
				}
				
				
			}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
        	});
        
        
	}
	
	 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())  { 
     	
	    case R.id.button1:
	    	Editor editor = mSettings.edit();
  		   	editor.putString(APP_PREFERENCES_ADRESS, "q").commit();
		   	editor.putString(APP_PREFERENCES_CITY, edt.getText().toString()).commit();
		   	Log.d("", edt.getText().toString());
		   	Intent intent = new Intent(this, MainActivity.class);
	    	startActivity(intent);
			break;
	}
	}
	
	

	private class CopyDataBase extends AsyncTask<Void, Void, Void>  {

		
		@Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	       Log.d("DATABASE", "START");
	    }
		
		@Override
		protected Void doInBackground(Void... arg0)  {
			 Log.d("DATABASE", "DO");
			 try{
			 // COPY IF NOT EXISTS
			    AssetManager am = getApplicationContext().getAssets();
			    OutputStream os = new FileOutputStream("/data/data/"+ "ua.mkh.weather" +"/databases/PrdouctDB.db");
			    byte[] b = new byte[100];
			    int r;
			    InputStream is = am.open("PrdouctDB.db");
			    while ((r = is.read(b)) != -1) {
			         os.write(b, 0, r);
			   }
			   is.close();
			   os.close();
			 }
			 catch (Exception s){
				 
			 }

			return null;
		}
		
		
		
		@Override
		protected void onPostExecute(Void result) {		
			 Log.d("DATABASE", "END");
		}
	}
	
	 @Override
	    public void onResume()
	    {
	        super.onResume();
	
	
    // ���������, ������ �� ��� ����������� ���������
    boolean hasVisited = mSettings.getBoolean(APP_PREFERENCES_VISITED, false);
	
    if (!hasVisited) {
        // ������� ������ ����������
    	new CopyDataBase().execute();
        Editor e = mSettings.edit();
        e.putBoolean(APP_PREFERENCES_VISITED, true);
        e.apply(); // �� �������� ����������� ���������
    }
	    }
	
	
	 @Override
	    public void onDestroy()
	    {
	        super.onDestroy();
	        
	        if (mDatabaseHelper  != null) {
	        	mDatabaseHelper.close();
	        }
	       
	    }
	
	 
	
	    public void populateListview(String s){
	    	
	    try {   
	    	adapter.clear();
	    	 Cursor cmelayu=mSqLiteDatabase.rawQuery("SELECT * FROM proinfo " +
	                 "WHERE name = '"+ s +"' ;", null);
	         //-fetch record
	    	 
	    	 if(cmelayu!=null)
	    	    {
	    	        if(cmelayu.moveToFirst())
	    	        {
	    	            do {
	    	            	String name=cmelayu.getString(cmelayu.getColumnIndex("name"));
	    	                String country=cmelayu.getString(cmelayu.getColumnIndex("countryCode"));
	    	                String id=cmelayu.getString(cmelayu.getColumnIndex("id_c"));
	    	             arraylist.add(id + ", " + name + ", " + country);

	    	            } while (cmelayu.moveToNext());
	    	        }
	    	    

	    	    
	    /*
	         if(cmelayu.getCount()!=0){
	             cmelayu.moveToFirst();//go to first row
	             String name=cmelayu.getString(cmelayu.getColumnIndex("name"));
	                String country=cmelayu.getString(cmelayu.getColumnIndex("countryCode"));
	                String id=cmelayu.getString(cmelayu.getColumnIndex("id_c"));
	             arraylist.add(id + ", " + name + ", " + country);*/
	         }
	         else{
	             //display some notice here saying no data found
	        	 Log.d("ERORR", "NO INFO ");
	        	 adapter.clear();
	         }
	         
	         
	         
	         
	         
	         /*
	    Cursor c=mSqLiteDatabase.rawQuery(" SELECT * FROM proinfo WHERE name '"+ s + "';", null); 
	    Log.d("YES", "search");
	    if(c!=null)
	    {
	        if(c.moveToFirst())
	        {
	        	
	            do {
	                String name=c.getString(c.getColumnIndex("name"));
	                String country=c.getString(c.getColumnIndex("countryCode"));
	                String id=c.getString(c.getColumnIndex("id_c"));
	                arraylist.add(name + ", " + country);

	            } while (c.moveToNext());
	        }
	    }
*/
	    listview.setAdapter(adapter);
	}
	    catch(Exception e){
	    	Log.d("ERORR", " ");
	    }
	          }

}


