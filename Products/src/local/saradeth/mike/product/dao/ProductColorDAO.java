package local.saradeth.mike.product.dao;

import java.util.ArrayList;
import java.util.List;

import local.saradeth.mike.product.core.Globals;
import local.saradeth.mike.product.core.Product;
import local.saradeth.mike.product.lib.DatabaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
public class ProductColorDAO {

	public ProductColorDAO() {
		// TODO Auto-generated constructor stub
	}

	
	public static void insertProductColorArray(SQLiteDatabase db, Product product) {
		
		String[] colors = product.getColors();
		
		for(int ii=0; ii<colors.length; ii++) {		

			ProductColorDAO.insertProductColor(db, product.getProductId(), colors[ii]);
		}				
	}
		
	//insert into product_color table
	public static void insertProductColor(SQLiteDatabase db, int productId, String color) {
		String sql = "insert into product_color(product_id, color)"
	    		+ " values ("
	    		+ productId + ", "  
	    		+ "\"" + color + "\""
	    		+ ")"
	    		;
	    db.execSQL(sql);		    
	}
	
	
	
	//Load and return product color in String[] 
	public static String[] loadProductColor(SQLiteDatabase db, int productId) {
						
		List<String> colors = new ArrayList<String>();

		//Get products SQL
	    String query = "select color from product_color where product_id = " 
	    		+ productId + " order by color";
	    
	    Cursor cursor = db.rawQuery(query, null);
    	while (cursor.moveToNext()){
    		//Fetch data from cursor 
    		colors.add(cursor.getString(0));
        }      	
    	cursor.close();
    	
    	//Convert to array of String
    	String[] colorArray = new String[colors.size()];
    	for(int ii = 0; ii < colors.size(); ii++) {
    		colorArray[ii] = colors.get(ii);    		
    	}
    	
	    return colorArray;
	}	
	
	//Delete colors for a product
	public static void delete(SQLiteDatabase db, int product_id)	{
		
		String sql = "delete from product_color where product_id = " + product_id;
	    db.execSQL(sql);
	    
	}	
	
		
}
