package local.saradeth.mike.product.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import local.saradeth.mike.product.core.Address;
import local.saradeth.mike.product.core.Product;
import local.saradeth.mike.product.core.Store;
import local.saradeth.mike.product.lib.Util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StoreDAO {

	public StoreDAO() {
		// TODO Auto-generated constructor stub
	}
	
	//Loop thru the list and insert to tables store and address
	public static void insertStoreArray(SQLiteDatabase db, Product product) {		
		Map<String, Store> stores = product.getStores();
		
		//iterating over values only
		for (Store store : stores.values()) {
			StoreDAO.insertStore(db, product.getProductId(), store);
		}		    	   		
	}
		
	
	
	//Insert to tables store and its address
	public static void insertStore(SQLiteDatabase db, int productId, Store store) {
		String sql = "insert into store(product_id, store_name)"
	    		+ " values ("
	    		+ productId + ", "  
	    		+ "\"" + store.getStoreName() + "\""
	    		+ ")"
	    		;

	    db.execSQL(sql);
	    
	    int store_id = Util.getLastInsertRowId(db, "store");
	    store.setStoreId(store_id); 
	    
	    //Insert store address
	    AddressDAO.insertAddress(db, productId, store_id, store.getAddress());
	}
	
	
	
	//Load and return store
	public static Map<String, Store> loadStore(SQLiteDatabase db, int productId, int id) {
						
		Map<String, Store> stores = new HashMap<String, Store>();

		//Build SQL
	    String query = "select store_id, store_name from store where product_id = " 
	    		+ productId + " order by store_name";

	    Cursor cursor = db.rawQuery(query, null);
    	while (cursor.moveToNext()){
    		//Fetch data from cursor 
    		Store store = new Store();
    		
    		store.setStoreId(cursor.getInt(0));	
    		store.setStoreName(cursor.getString(1));
    		
    		//Get store address
    		Address address = AddressDAO.loadAddress(db, store.getStoreId());
    		store.setAddress(address);
    		
    		//Add store record to HashMap
    		stores.put(id + store.getStoreName(), store); //user ID and Store name as key
    		
        }      	
    	cursor.close();

	    return stores;
	}		
	
	
	//Delete stores for a product
	public static void delete(SQLiteDatabase db, int product_id)	{
		
		String sql = "delete from store where product_id = " + product_id;
	    db.execSQL(sql);
	    
	    
		sql = "delete from address where product_id = " + product_id;
	    db.execSQL(sql);
	    
	}
	
	
}
