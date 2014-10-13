package local.saradeth.mike.product.lib;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper{

public static String dbName; 
private SQLiteDatabase dataBase;
private final Context context;

//Constructor
public DatabaseHelper(Context context, String dbName) {
    super(context, dbName, null, 1);
    this.context = context;
    DatabaseHelper.dbName = dbName;
    
    openOrCreate();
	}


public void openOrCreate() {
	// checking database and open it if exists
    if (checkDataBase()) {
        openDataBase();
    }else {
    	//Create database
        dataBase = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        Toast.makeText(context, "Database created", Toast.LENGTH_LONG).show();       
    }
}


public void openDataBase() throws SQLException {
	
	String dbPath = context.getDatabasePath(dbName).toString();
    //String dbPath = DATABASE_PATH + dbName;
    dataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
}

private boolean checkDataBase() {
    SQLiteDatabase checkDB = null;
    boolean exist = false;
    String dbPath = "";
    try {
        //dbPath = DATABASE_PATH + dbName;
        dbPath = context.getDatabasePath(dbName).toString();
        checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
    } catch (SQLiteException e) {
        Log.v("db log", "database does't exist");
    }

    if (checkDB != null) {
        exist = true;
        Log.i("checkDataBase", "database exist " + dbPath );
        checkDB.close();
    }
    return exist;
}


//returns database 
public static SQLiteDatabase getDatabase(Context context, String dbName) {
	
	DatabaseHelper dbHelper = new DatabaseHelper(context, dbName);
	SQLiteDatabase sqliteDataBase = dbHelper.getDb();
	
	return sqliteDataBase;
		
}    




public SQLiteDatabase getDb() {
	return dataBase;
}

@Override
public void onCreate(SQLiteDatabase db) {
	// TODO Auto-generated method stub
	
}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// TODO Auto-generated method stub
	
}

}


