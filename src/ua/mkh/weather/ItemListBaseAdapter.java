package ua.mkh.weather;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class ItemListBaseAdapter extends BaseAdapter {
	private static ArrayList<ItemDetails> itemDetailsrrayList;
	 
	 
	 
	 private LayoutInflater l_Inflater;

	 public ItemListBaseAdapter(Context context, ArrayList<ItemDetails> results) {
	  itemDetailsrrayList = results;
	  l_Inflater = LayoutInflater.from(context);
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

	   convertView.setTag(holder);
	  } else {
	   holder = (ViewHolder) convertView.getTag();
	  }
	  
	  holder.txt_itemCity.setText(itemDetailsrrayList.get(position).getCity());
	  holder.txt_itemCountry.setText(itemDetailsrrayList.get(position).getCountry());
	  holder.txt_itemTemp.setText(itemDetailsrrayList.get(position).getTemp());
	  holder.txt_itemTime.setText(itemDetailsrrayList.get(position).getTime());
	  
	  String s = itemDetailsrrayList.get(position).getWeather();

	  return convertView;
	 }

	 
	}