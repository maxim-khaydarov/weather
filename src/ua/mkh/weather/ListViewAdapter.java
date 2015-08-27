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
 
public class ListViewAdapter extends BaseAdapter {
 
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	//ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();
 
	public ListViewAdapter(Context context,
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
		TextView date;
		TextView icon;
		TextView temp_min;
		TextView temp_max;
		ImageView ic;
 
		String roman = "fonts/Regular.otf";
		String medium = "fonts/Medium.otf";
		String thin = "fonts/Thin.otf";
		Typeface typefaceRoman = Typeface.createFromAsset(context.getAssets(), roman);
		Typeface typefaceMedium = Typeface.createFromAsset(context.getAssets(), medium);
		Typeface typefaceThin = Typeface.createFromAsset(context.getAssets(), thin);
		
		
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View itemView = inflater.inflate(R.layout.list_item, parent, false);
		// Get the position
		resultp = data.get(position);
 
		// Locate the TextViews in listview_item.xml
		date = (TextView) itemView.findViewById(R.id.day);
		icon = (TextView) itemView.findViewById(R.id.icon);
		temp_min = (TextView) itemView.findViewById(R.id.temp_min);
		temp_max = (TextView) itemView.findViewById(R.id.temp_max);
		ic = (ImageView) itemView.findViewById(R.id.imageView1);
		
		date.setTypeface(typefaceRoman);
		temp_min.setTypeface(typefaceThin);
		temp_max.setTypeface(typefaceRoman);
 
		// Locate the ImageView in listview_item.xml
		//flag = (ImageView) itemView.findViewById(R.id.flag);
		if (resultp.get(MainActivity.ICON).contains("01d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d01d));
		}
		else if (resultp.get(MainActivity.ICON).contains("01n")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.n01n));
		}
		else if (resultp.get(MainActivity.ICON).contains("02d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d02d));
		}
		else if (resultp.get(MainActivity.ICON).contains("02n")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.n02n));
		}
		else if (resultp.get(MainActivity.ICON).contains("03n")|| resultp.get(MainActivity.ICON).contains("03d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d03d));
		}
		else if (resultp.get(MainActivity.ICON).contains("04n")|| resultp.get(MainActivity.ICON).contains("04d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d04d));
		}
		else if (resultp.get(MainActivity.ICON).contains("09n")|| resultp.get(MainActivity.ICON).contains("09d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d09d));
		}
		else if (resultp.get(MainActivity.ICON).contains("10d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d10d));
		}
		else if (resultp.get(MainActivity.ICON).contains("10n")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.n10n));
		}
		else if (resultp.get(MainActivity.ICON).contains("11n")|| resultp.get(MainActivity.ICON).contains("11d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d11d));
		}
		else if (resultp.get(MainActivity.ICON).contains("13n")|| resultp.get(MainActivity.ICON).contains("13d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d13d));
		}
		else if (resultp.get(MainActivity.ICON).contains("50n")|| resultp.get(MainActivity.ICON).contains("50d")){
			ic.setImageDrawable(context.getResources().getDrawable(R.drawable.d50d));
		}
		// Capture position and set results to the TextViews
		date.setText(resultp.get(MainActivity.DAY));
		//icon.setText(resultp.get(MainActivity.ICON));
		temp_min.setText(resultp.get(MainActivity.TEMP_MIN) + "\u00B0");
		temp_max.setText(resultp.get(MainActivity.TEMP_MAX) + "\u00B0");
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
