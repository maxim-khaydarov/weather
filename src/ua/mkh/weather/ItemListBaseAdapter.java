package ua.mkh.weather;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class ItemListBaseAdapter extends BaseAdapter {
	private static ArrayList<ItemDetails> itemDetailsrrayList;
	 
	 
	 
	 private LayoutInflater l_Inflater;
	 Context mContext;

	 public ItemListBaseAdapter(Context context, ArrayList<ItemDetails> results) {
	  itemDetailsrrayList = results;
	  l_Inflater = LayoutInflater.from(context);
	  mContext = context;
	 }

	 public int getCount() {
	  return itemDetailsrrayList.size();
	 }

	 public Object getItem(int position) {
	  return itemDetailsrrayList.get(position);
	 }

	 public long getItemId(int position) {
	  return position;
	 }
	 
	 static class ViewHolder {
		  TextView txt_itemCity;
		  TextView txt_itemCountry;
		  TextView txt_itemTemp;
		  TextView txt_itemTime;
		  ImageView img;
		 }

	 public View getView(int position, View convertView, ViewGroup parent) {
	  ViewHolder holder;
	  if (convertView == null) {
	   convertView = l_Inflater.inflate(R.layout.row_all_city, null);
	   holder = new ViewHolder();
	   holder.txt_itemCity = (TextView) convertView.findViewById(R.id.city);
	   holder.txt_itemCountry = (TextView) convertView.findViewById(R.id.country);
	   holder.txt_itemTemp = (TextView) convertView.findViewById(R.id.temp);
	   holder.txt_itemTime = (TextView) convertView.findViewById(R.id.time);
	   holder.img = (ImageView) convertView.findViewById(R.id.imageView1);

	   String roman = "fonts/Regular.otf";
		String medium = "fonts/Medium.otf";
		String bold =  "fonts/Bold.otf";
		String thin = "fonts/Thin.otf";
		String ultra = "fonts/Ultralight.otf";
		
		//Typeface typefaceThin = Typeface.createFromAsset(getAssets(), thin);
		
		Typeface type = Typeface.createFromAsset(mContext.getAssets(),
               "fonts/Thin.otf"); 
		Typeface typeRoman = Typeface.createFromAsset(mContext.getAssets(),
	               "fonts/Regular.otf"); 
		
	   holder.txt_itemCity.setTypeface(typeRoman);
	   holder.txt_itemCountry.setTypeface(typeRoman);
	   holder.txt_itemTemp.setTypeface(type);
	   holder.txt_itemTime.setTypeface(typeRoman);
	   
	 
	   
	   
	   
	   convertView.setTag(holder);
	  } else {
	   holder = (ViewHolder) convertView.getTag();
	  }
	  
	  holder.txt_itemCity.setText(itemDetailsrrayList.get(position).getCity());
	  holder.txt_itemCountry.setText(itemDetailsrrayList.get(position).getCountry());
	  holder.txt_itemTemp.setText(itemDetailsrrayList.get(position).getTemp());
	  holder.txt_itemTime.setText(itemDetailsrrayList.get(position).getTime());
	  
	  String s = itemDetailsrrayList.get(position).getWeather();
	  
	  if (itemDetailsrrayList.get(position).getIcon().contains("d")){
		  if(itemDetailsrrayList.get(position).getIcon().contains("500")){
			  
		  }
		  else if(itemDetailsrrayList.get(position).getIcon().contains("501")){
			  
		  }
		  else if (itemDetailsrrayList.get(position).getIcon().contains("800")){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.clear_sky_d_m));
		  }
	  }
	  else{
		  
	  }

	  return convertView;
	 }

	 
	}