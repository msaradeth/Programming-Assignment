package local.saradeth.mike.product.dao;

import java.util.HashMap;
import java.util.Map;

import local.saradeth.mike.product.core.Address;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AddressDAO {

	public AddressDAO() {
		// TODO Auto-generated constructor stub
	}

		
	
	//Insert into table address
	public static void insertAddress(SQLiteDatabase db, int productId, int storeId, Address address) {		
		String sql;
		
		sql = "insert into address(product_id , store_id , addr , city , state, zip, phone)"
	    		+ " values ("
	    		+ productId + ", "  
	    		+ storeId + ", "
	    		+ "\"" + address.getAddr() + "\", " 
	    		+ "\"" + address.getCity() + "\", " 
				+ "\"" + address.getState() + "\", " 
				+ "\"" + address.getZip() + "\", " 
				+ "\"" + address.getPhone() + "\"" 
	    		+ ")"
	    		;
	    db.execSQL(sql);		    
	}
	
	
	//Load and return Address object for a given store_id
	public static Address loadAddress(SQLiteDatabase db, int storeId) {
						
		Address address = new Address();

		//Build SQL
	    String query = "select address_id, product_id, store_id, addr, city, state, zip, phone from address"
	    			+ " where store_id = " + storeId;

	    Cursor cursor = db.rawQuery(query, null);
    	while (cursor.moveToNext()){
    		//Fetch data from cursor and returns product object
    		address = AddressDAO.loadAddress(db, cursor);    		
        }      	
    	cursor.close();

	    return address;
	}	
	
	
	//Fetch data from cursor and returns Address object
	public static Address loadAddress(SQLiteDatabase db, Cursor cursor) {
		
		Address address = new Address();
		
		//Fetch data from cursor
		address.setAddress_id(cursor.getInt(0));
		address.setProduct_id(cursor.getInt(1));
		address.setStore_id(cursor.getInt(2));			
		address.setAddr(cursor.getString(3));
		address.setCity(cursor.getString(4));
		address.setState(cursor.getString(5));
		address.setZip(cursor.getString(6));
		address.setPhone(cursor.getString(7));
					
		return address;			
	}	
	
}
	
