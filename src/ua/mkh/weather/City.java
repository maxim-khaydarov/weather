package ua.mkh.weather;

public class City {
	 
    private int id;
    private String name;
    private String id_city;
 
    public City(){}
 
    public City(String name, String id_city) {
        super();
        
        this.name = name;
        this.id_city = id_city;
    }
 
    //getters & setters
 
    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name + ", id_city=" + id_city
                + "]";
    }

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return name;
	}
	public void setTitle(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return id_city;
	}
	public void setAuthor(String id_city) {
		this.id_city = id_city;
	}
}
