package ua.mkh.weather;

import android.util.Log;

public class ItemDetails {
	
	private String city ;
	private String country;
	private String temp;
	private String time;
	private String weather;

public String getCity() {
return city;
}
public void setCity(String city) {
this.city = city;
Log.d("DD", "ADD CITY");
}
public String getCountry() {
return country;
}
public void setCountry(String country) {
this.country = country;
}
public String getTemp() {
return temp;
}
public void setTemp(String temp) {
this.temp = temp;
Log.d("DD", "ADD TEMP");
}
public String getTime() {
return time;
}
public void setTime(String time) {
this.time = time;
Log.d("DD", "ADD TIME");
}
public String getWeather() {
return weather;
}
public void setWeather(String weather) {
this.weather = weather;
}



}

