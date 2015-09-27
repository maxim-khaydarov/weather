package ua.mkh.weather;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;



public class DatabaseHelperCityMain extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "database_city_curent.db";
	public static final String DATABASE_TABLE = "city";
	private static final int DATABASE_VERSION = 1;
	
	//public static final String NAME_CITY = "name_city";
	public static final String ID_CITY = "id_city";
	public static final String KEY_ID = "id";
	
	private static final String DATABASE_CREATE_SCRIPT = "create table "
			+ DATABASE_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " +  ID_CITY + " text NOT NULL);";
	
    public DatabaseHelperCityMain(Context context, String name, SQLiteDatabase.CursorFactory factory,
            int version) {
super(context, name, factory, version);
}

public DatabaseHelperCityMain(Context context, String name, SQLiteDatabase.CursorFactory factory,
            int version, DatabaseErrorHandler errorHandler) {
super(context, name, factory, version, errorHandler);
}

DatabaseHelperCityMain(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
}

@Override
public void onCreate(SQLiteDatabase db) {
	db.execSQL(DATABASE_CREATE_SCRIPT);
}


public void addBook(City city){
    //for logging
	Log.d("addBook", city.toString()); 
	// 1. get reference to writable DB
	SQLiteDatabase db = this.getWritableDatabase();
	// 2. create ContentValues to add key "column"/value
	ContentValues values = new ContentValues();
	values.put(ID_CITY, city.getAuthor()); // get author
	// 3. insert
	db.insert(DATABASE_TABLE, // table
			null, //nullColumnHack
			values); // key/value -> keys = column names/ values = column values
	db.close(); 
}



public void deleteBook(City city) {
	 
    // 1. get reference to writable DB
    SQLiteDatabase db = this.getWritableDatabase();
    // 2. delete
    db.delete(DATABASE_TABLE, //table name
            KEY_ID+" = ?",  // selections
            new String[] { String.valueOf(city.getId()) }); //selections args
    // 3. close
    db.close();
    //log
    Log.d("deleteBook", city.toString());

}

public List<City> getAllBooks() {
    List<City> city = new LinkedList<City>();

    // 1. build the query
    String query = "SELECT  * FROM " + DATABASE_TABLE;

    // 2. get reference to writable DB
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);

    // 3. go over each row, build book and add it to list
    City book = null;
    if (cursor.moveToFirst()) {
        do {
            book = new City();
            book.setId(Integer.parseInt(cursor.getString(0)));
            book.setAuthor(cursor.getString(1));

            // Add book to books
            city.add(book);
        } while (cursor.moveToNext());
    }

    //Log.d("getAllBooks()", city.toString());

    // return books
    return city;
}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// Запишем в журнал
		Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
		
		// Удаляем старую таблицу и создаём новую
		db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
		// Создаём новую таблицу
		onCreate(db);
}


}
