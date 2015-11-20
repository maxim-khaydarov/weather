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
		  if(Integer.parseInt(itemDetailsrrayList.get(position).getId()) >= 500 && Integer.parseInt(itemDetailsrrayList.get(position).getId()) < 532){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rain_d_m));
		  }
		  else if(Integer.parseInt(itemDetailsrrayList.get(position).getId()) >=200 && Integer.parseInt(itemDetailsrrayList.get(position).getId()) < 233){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.thunderstorm_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 800){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.clear_sky_d_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 801){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.few_clouds_d_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 802){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.scattered_clouds_d_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 803 || Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 804){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.broken_clouds_d_m));
		  }
		  else if(Integer.parseInt(itemDetailsrrayList.get(position).getId()) >= 300 && Integer.parseInt(itemDetailsrrayList.get(position).getId()) < 322){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rain_d_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 600){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.snow_d_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 601){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.snow_d_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) >= 602 && Integer.parseInt(itemDetailsrrayList.get(position).getId()) < 623){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.heavy_snow_d_m));
		  }
		  else{
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.scattered_clouds_d_m));
		  }
	  }
	  else{
		  if(Integer.parseInt(itemDetailsrrayList.get(position).getId()) >= 500 && Integer.parseInt(itemDetailsrrayList.get(position).getId()) < 532){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rain_n_m));
		  }
		  else if(Integer.parseInt(itemDetailsrrayList.get(position).getId()) >=200 && Integer.parseInt(itemDetailsrrayList.get(position).getId()) < 233){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.thunderstorm_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 800){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.clear_sky_n_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 801){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.few_clouds_n_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 802){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.scattered_clouds_n_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 803 || Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 804){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.broken_clouds_n_m));
		  }
		  else if(Integer.parseInt(itemDetailsrrayList.get(position).getId()) >= 300 && Integer.parseInt(itemDetailsrrayList.get(position).getId()) < 322){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rain_n_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 600){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.snow_n_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) == 601){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.snow_n_m));
		  }
		  else if (Integer.parseInt(itemDetailsrrayList.get(position).getId()) >= 602 && Integer.parseInt(itemDetailsrrayList.get(position).getId()) < 623){
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.heavy_snow_n_m));
		  }
		  else{
			  holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.scattered_clouds_n_m));
		  }
	  }

	  return convertView;
	 }

	 
	}