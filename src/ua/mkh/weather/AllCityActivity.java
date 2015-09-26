package ua.mkh.weather;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;

public class AllCityActivity extends Activity  implements OnClickListener{
	
	
	Typeface typefaceRoman, typefaceMedium, typefaceBold, typefaceThin, typefaceUltra;
	public static final String APP_PREFERENCES = "mysettings"; 
	SharedPreferences mSettings;
	ListView lvMain;
	
	private DatabaseHelperCityMain mDatabaseHelper;
	private SQLiteDatabase mSqLiteDatabase;
	
	final ArrayList<String> catnames = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	List<City> list;
	
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
		
		mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
		
		 mDatabaseHelper = new DatabaseHelperCityMain(this);

		    mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
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
			
			load_base();
			
			
			
			// get all books
			//mDatabaseHelper.getAllBooks();
			
			
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

		 //load_base();
	}
	
	private void load_base() {
		list = mDatabaseHelper.getAllBooks();
			
			String[] stringArray = new String[list.size()];
			for (int i=0; i < list.size(); i++) {
			   stringArray[i] = list.get(i).toString();
			}
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, stringArray);

	    lvMain.setAdapter(adapter);
	    setListViewHeightBasedOnChildren(lvMain);
	    
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
	     	
	   
	}
	}

}
