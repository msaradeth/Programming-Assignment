package local.saradeth.mike.product.dao;


import local.saradeth.mike.product.core.Globals;
import local.saradeth.mike.product.lib.DatabaseHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseTablesDAO {

	public DatabaseTablesDAO() {
		// TODO Auto-generated constructor stub
	}

	public static void create(Context context, String action) {
		SQLiteDatabase db = DatabaseHelper.getDatabase(context, Globals.dbName);
		DatabaseTablesDAO.create(db, action);
		db.close();
	}
	
	public static void create(SQLiteDatabase db, String action) {
		DatabaseTablesDAO tables = new DatabaseTablesDAO();
		tables.createDatabaseTables(db, action);
	}
	
	
	public void clearTable(SQLiteDatabase db, String tableName, String sql) {

		if (Globals.dropTables) {
			//Drop existing table if exist
			dropTableIfExist(db, tableName);
		}
		
		if (!tableExist(db, tableName)) {
			//Execute SQL to create table
			db.execSQL(sql);
			Log.d("create table", tableName);
		}
	}
	
	
	public void createTable(SQLiteDatabase db, String tableName, String sql, String action) {

		if (action.equalsIgnoreCase("dropTableIfExist")) {
			//Drop existing table if exist
			dropTableIfExist(db, tableName);
		}
		
		
		if (!tableExist(db, tableName)) {
			//Execute SQL to create table
			db.execSQL(sql);
			Log.d("create table", tableName);
		}
	}
	
	
	
	public void dropTableIfExist(SQLiteDatabase db, String tableName) {
		String query = "drop table if exists \"" + tableName + "\"";		
		db.execSQL(query);
	}
	
	
	public boolean tableExist(SQLiteDatabase db, String tableName) {
		
		String query = "select count(*) from sqlite_master where type = 'table' and  name = '" + tableName + "'";
		Cursor cursor = db.rawQuery(query, null);
		if (!cursor.moveToFirst()) {
	        return false;
	    }
		
	    int tableCount = cursor.getInt(0);
	    cursor.close();
	    
	    return (tableCount > 0);		
	}
	
	public void createDatabaseTables(SQLiteDatabase db, String action) {
		String sql;

		//create table product and its indexes
		sql = 
			"create table product"
			  + "("
			  + "product_id integer primary key,"
			  + "id integer,"
			  + "name Text,"
			  + "description Text,"
			  + "regular_price Text,"
			  + "sale_price Text,"
			  + "image_url text"
			  + ");"
			  + "create unique index idx_product_1 on product(product_id);"
			  + "create unique index idx_product_2 on product(id);";
	    createTable(db, "product", sql, action);
	    
	    
	    //create table address and its indexes
	    sql =
	    	"create table address"
			  + "("
			  + "address_id primary key,"
			  + "product_id int,"
			  + "store_id int,"
			  + "addr text,"
			  + "city text,"
			  + "state text,"
			  + "zip text,"
			  + "phone text"
			  + ");"
			  + "create unique index idx_address_1 on address(address_id);"
			  + "create index idx_address_2 on address(product_id, store_id);";
	    createTable(db, "address", sql, action);
	
	    //create table product_color and its indexes
	    sql = 
			"create table product_color"
			  + "("
			  + "color_id primary key,"
			  + "product_id int,"
			  + "color text"
			  + ");"
			  + "create unique index idx_color_1 on color(color_id);"
			  + "create index idx_color_2 on color(product_id, color);";
	    createTable(db, "product_color", sql, action);
	    
	    //create table store and its indexes
		sql = 
			"create table store"
			+ "("
		    + "store_id primary key,"
		    + "product_id int,"
		    + "store_name text"
		    + ");"
		    + "create unique index idx_store_1 on store(store_id);"
		    + "create index idx_store_2 on store(product_id);";
		createTable(db, "store", sql, action);

	}
}
