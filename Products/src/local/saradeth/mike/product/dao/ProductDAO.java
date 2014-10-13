package local.saradeth.mike.product.dao;

import java.util.ArrayList;
import java.util.List;

import local.saradeth.mike.product.core.Globals;
import local.saradeth.mike.product.core.Product;
import local.saradeth.mike.product.lib.DatabaseHelper;
import local.saradeth.mike.product.lib.Util;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ProductDAO {

	public ProductDAO() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	//insert a product record if it is not already in the database.
	public static int insertProduct(Context context, Product product) {
		SQLiteDatabase db = DatabaseHelper.getDatabase(context, Globals.dbName);
		int saveRecCount = 0;
		
		//Begin database transaction
		db.beginTransaction();
		
		if (!Util.recordExist(db, product.getId()) && (product.getId() != 0)) {				
			saveRecCount = saveRecCount + 1;
			ProductDAO.insertProduct(db, product);
		}			
		
		//Commit and end transaction
		db.setTransactionSuccessful();
		db.endTransaction();
		
		db.close();
		
		return saveRecCount;
	}
	
	
	
	//insert a all product records if it is not already in the database.
	public static int insertProduct(Context context, List<Product> alProduct) {
		SQLiteDatabase db = DatabaseHelper.getDatabase(context, Globals.dbName);
		int saveRecCount = 0;
		Product product;
		
		//Begin database transaction
		db.beginTransaction();

		int maxSize = alProduct.size();
		for(int ii=0; ii<maxSize; ii++) {	
			product = alProduct.get(ii);
			if (!Util.recordExist(db, product.getId()) && (alProduct.get(ii).getId() != 0)) {				
				saveRecCount = saveRecCount + 1;
				ProductDAO.insertProduct(db, alProduct.get(ii));	
			}				
		}		
		
		//Commit and end transaction
		db.setTransactionSuccessful();
		db.endTransaction();
		
		db.close();
		
		return saveRecCount;
	}
	
	
	public static int insertProduct(SQLiteDatabase db, Product product) {		
		String sql;
		
		sql = "insert into product(id, name, description, regular_price, sale_price, image_url)"
	    		+ " values ("
	    		+ product.getId() + ", "  
	    		+ "\"" + product.getName() + "\", " 
	    		+ "\"" + product.getDescription() + "\", " 
				+ "\"" + product.getRegularPrice() + "\", " 
				+ "\"" + product.getSalePrice() + "\", " 
				+ "\"" + product.getImageUrl() + "\"" 
	    		+ ")"
	    		;
		
		//Log.d("sql", sql);
	    db.execSQL(sql);	
	    
	    //Get incremental value of product_id (serial ID) from product table
	    int productId = Util.getLastInsertRowId(db, "product");
	    product.setProductId(productId);
	    
	    //Insert product colors
	    ProductColorDAO.insertProductColorArray(db, product);
	    
	    //Insert stores
	    StoreDAO.insertStoreArray(db, product);
	    
	    return productId;
	}
	
	
	//Load and return alProduct ArrayList
	public static List<Product> loadProduct(Context context) {
		
		SQLiteDatabase db = DatabaseHelper.getDatabase(context, Globals.dbName);
		List<Product> alProduct = ProductDAO.loadProduct(db);
		db.close();
		
		return alProduct;
	}
	
	
	//Load and return product base on given product_id
	public static Product loadProduct(Context context, int productId) {
		
		SQLiteDatabase db = DatabaseHelper.getDatabase(context, Globals.dbName);
		Product product = ProductDAO.loadProduct(db, productId);
		db.close();
		
		return product;
	}	
	
	

	//Load products into alProduct ArrayList
	public static List<Product> loadProduct(SQLiteDatabase db) {
						
		List<Product> alProduct = new ArrayList<Product>();
		Product product;
		
		//Get products SQL
	    String query = "select product_id, id, name, description, regular_price, sale_price, image_url from product order by id";

	    Cursor cursor = db.rawQuery(query, null);
    	while (cursor.moveToNext()){
    		//Fetch data from cursor and returns product object
    		product = ProductDAO.loadProduct(db, cursor);
    		
    		//Load product colors
    		product.setColors(ProductColorDAO.loadProductColor(db, product.getProductId()));
    		
    		//Load stores and their addresses
    		product.setStores(StoreDAO.loadStore(db, product.getProductId(), product.getId()));
    		
    		//Add product to array list
			alProduct.add(product);					
        }      	
    	cursor.close();
    	
	    return alProduct;
	}
	
	
	//Load products into alProduct ArrayList
	public static Product loadProduct(SQLiteDatabase db, int productId) {
						
		Product product = new Product();
		
		//Get products SQL
	    String query = "select product_id, id, name, description, regular_price, sale_price, image_url from product where product_id = " + productId;

	    Cursor cursor = db.rawQuery(query, null);
    	while (cursor.moveToNext()){
    		//Fetch data from cursor and returns product object
    		product = ProductDAO.loadProduct(db, cursor);
    		
    		//Load product colors
    		product.setColors(ProductColorDAO.loadProductColor(db, product.getProductId()));
    		
    		//Load stores and their addresses
    		product.setStores(StoreDAO.loadStore(db, product.getProductId(), product.getId()));
    		
				
        }      	
    	cursor.close();
    	
	    return product;
	}
	
	
	
	//Fetch data from cursor and returns product object
	public static Product loadProduct(SQLiteDatabase db, Cursor cursor) {
		
		Product product = new Product();
		
		//Fetch data from cursor
		product.setProductId(cursor.getInt(0));
		product.setId(cursor.getInt(1));
		product.setName(cursor.getString(2));
		product.setDescription(cursor.getString(3));
		product.setRegularPrice(cursor.getString(4));
		product.setSalePrice(cursor.getString(5));
		product.setImageUrl(cursor.getString(6));
		
		return product;			
	}
		
	
	//Delete a product from database
	public static void delete(Context context, Product product) {
		SQLiteDatabase db = DatabaseHelper.getDatabase(context, Globals.dbName);		
		
		//Begin database transaction
		db.beginTransaction();
		
		ProductDAO.delete(db, product);
		
		//Commit and end transaction
		db.setTransactionSuccessful();
		db.endTransaction();
		
		db.close();
		
	}
		
	
	//Delete selected products from database
	public static int deleteSelectedProductArray(Context context, List<Product> alProduct) {
		SQLiteDatabase db = DatabaseHelper.getDatabase(context, Globals.dbName);
		int recCount = 0;
		
		//Begin database transaction
		db.beginTransaction();
		
		for(int ii=0; ii<alProduct.size(); ii++) {
			if (alProduct.get(ii).isCheckBoxChecked()) {	
				//delete a product record 
				ProductDAO.delete(db, alProduct.get(ii));
				
				recCount = recCount + 1;
				//alProduct.remove(ii);		
			}
		}
				
		//Commit and end transaction
		db.setTransactionSuccessful();
		db.endTransaction();
		
		db.close();
		
		return recCount;
	}
	


	//Delete a product record
	public static void delete(SQLiteDatabase db, Product product)	{
		
		//Delete a product
		String sql = "delete from product where product_id = " + product.getProductId();
	    db.execSQL(sql);	
	    
	    //Delete stores and address for a product
	    StoreDAO.delete(db, product.getProductId());
	    
	    //Delete colors for a product
	    ProductColorDAO.delete(db, product.getProductId());
	    
	}
	
	
	
	//Update a product 
	public static void update(Context context, Product product) {
		SQLiteDatabase db = DatabaseHelper.getDatabase(context, Globals.dbName);		
		
		//Begin database transaction
		db.beginTransaction();
		
		ProductDAO.update(db, product);
		
		//Commit and end transaction
		db.setTransactionSuccessful();
		db.endTransaction();
		
		db.close();
		
	}	
	
	public static void update(SQLiteDatabase db, Product product) {		
		String sql;
		
		sql = "update product set "
	    		+ " name = \"" + product.getName() + "\", " 
	    		+ " description = \"" + product.getDescription() + "\", " 
				+ " regular_price = \"" + product.getRegularPrice() + "\", " 
				+ " sale_price = \"" + product.getSalePrice() + "\" " 
				+ " where product_id = " + product.getProductId()	    		
	    		;
		
		//Log.d("sql", sql);
	    db.execSQL(sql);		    
	    
	}
		
}
