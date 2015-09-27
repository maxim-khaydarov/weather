package ua.mkh.weather;

public class City {
	 
    private int id;
    private String id_city;
 
    public City(){}
 
    public City(String id_city) {
        super();
        this.id_city = id_city;
    }
 
    //getters & setters
 
    @Override
    public String toString() {
        return id_city;
    }

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAuthor() {
		return id_city;
	}
	public void setAuthor(String id_city) {
		this.id_city = id_city;
	}
}
