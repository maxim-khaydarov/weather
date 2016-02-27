package ua.mkh.weather;

import java.util.ArrayList;
import java.util.HashMap;

 
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ListViewAdapterDailyHum extends BaseAdapter {
 
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	//ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();
 
	public ListViewAdapterDailyHum(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		//imageLoader = new ImageLoader(context);
	}
 
	@Override
	public int getCount() {
		return data.size();
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView time;
		TextView icon;
		TextView temp;
		ImageView ic;
		TextView hum;
 
		String roman = "fonts/Regular.otf";
		String medium = "fonts/Medium.otf";
		String thin = "fonts/Thin.otf";
		Typeface typefaceRoman = Typeface.createFromAsset(context.getAssets(), roman);
		Typeface typefaceMedium = Typeface.createFromAsset(context.getAssets(), medium);
		Typeface typefaceThin = Typeface.createFromAsset(context.getAssets(), thin);
		
		
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View itemView = inflater.inflate(R.layout.list_item_daily_hum, parent, false);
		// Get the position
		resultp = data.get(position);
 
		// Locate the TextViews in listview_item.xml
		time = (TextView) itemView.findViewById(R.id.time);
		temp = (TextView) itemView.findViewById(R.id.temp);
		hum = (TextView) itemView.findViewById(R.id.hum);
		ic = (ImageView) itemView.findViewById(R.id.imageView1);
		
		time.setTypeface(typefaceThin);
		temp.setTypeface(typefaceRoman);
		
		//int tim = Integer.parseInt(resultp.get(MainActivity.TIME));
		/*
		if (tim >= 05 && tim <= 20){
			hum.setTextColor(context.getResources().getColor(R.color.blue_day));
		}
		
		*/
 
		// Locate the ImageView in listview_item.xml
		//flag = (ImageView) itemView.findViewById(R.id.flag);
		if (resultp.get(MainActivity.IC).contains("01d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d01d));
		}
		else if (resultp.get(MainActivity.IC).contains("01n")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.n01n));
		}
		else if (resultp.get(MainActivity.IC).contains("02d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d02d));
		}
		else if (resultp.get(MainActivity.IC).contains("02n")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.n02n));
		}
		else if (resultp.get(MainActivity.IC).contains("03n")|| resultp.get(MainActivity.IC).contains("03d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d03d));
		}
		else if (resultp.get(MainActivity.IC).contains("04n")|| resultp.get(MainActivity.IC).contains("04d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d04d));
		}
		else if (resultp.get(MainActivity.IC).contains("09n")|| resultp.get(MainActivity.IC).contains("09d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d09d));
		}
		else if (resultp.get(MainActivity.IC).contains("10d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d10d));
		}
		else if (resultp.get(MainActivity.IC).contains("10n")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.n10n));
		}
		else if (resultp.get(MainActivity.IC).contains("11n")|| resultp.get(MainActivity.IC).contains("11d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d11d));
		}
		else if (resultp.get(MainActivity.IC).contains("13n")|| resultp.get(MainActivity.IC).contains("13d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d13d));
		}
		else if (resultp.get(MainActivity.IC).contains("50n")|| resultp.get(MainActivity.IC).contains("50d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d50d));
		}
		else if (resultp.get(MainActivity.IC).contains("down")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.down_sun));
		}
		else if (resultp.get(MainActivity.IC).contains("get")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.get_sun));
		}
		// Capture position and set results to the TextViews
		time.setText(resultp.get(MainActivity.TIME));
		//icon.setText(resultp.get(MainActivity.ICON));
		temp.setText(resultp.get(MainActivity.TEMP));
		
		hum.setText(resultp.get(MainActivity.HUM) );
		
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		//imageLoader.DisplayImage(resultp.get(MainActivity.FLAG), flag);
		// Capture ListView item click
		/*itemView.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, SingleItemView.class);
				// Pass all data rank
				intent.putExtra("rank", resultp.get(MainActivity.RANK));
				// Pass all data country
				intent.putExtra("country", resultp.get(MainActivity.COUNTRY));
				// Pass all data population
				intent.putExtra("population",resultp.get(MainActivity.POPULATION));
				// Pass all data flag
				intent.putExtra("flag", resultp.get(MainActivity.FLAG));
				// Start SingleItemView Class
				context.startActivity(intent);
 
			}
		});
		return itemView;*/
		return itemView;
	}
}
