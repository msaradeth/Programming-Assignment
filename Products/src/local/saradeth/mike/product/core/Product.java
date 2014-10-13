package local.saradeth.mike.product.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;



public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 956988783833925690L;
	private int productId=0;
	private int id=0;
	private String name="";
	private String description=" ";
	private String regularPrice="";
	private String salePrice= "";
	private String imageUrl="";
	private String[] colors = null;
	private Map<String, Store> stores = new HashMap<String, Store>();

	private boolean checkBoxChecked = false ;


	//constructor
	public Product() {
	}
	
	//constructor
	public Product(int id, String name, String descr, String regPrice, String salePrice, String imageUrl, String[] colors, Map<String, Store> stores) {
		this.id = id;
		this.name = name;
		this.description = descr;
		this.regularPrice = regPrice;
		this.salePrice = salePrice;
		this.imageUrl = imageUrl;
		this.colors = colors;
		this.stores = stores;
		
	}
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRegularPrice() {
		return regularPrice;
	}
	public void setRegularPrice(String regularPrice) {
		this.regularPrice = regularPrice;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String[] getColors() {
		return colors;
	}
	public void setColors(String[] colors) {
		this.colors = colors;
	}
	public Map<String, Store> getStores() {
		return stores;
	}
	public void setStores(Map<String, Store> stores) {
		this.stores = stores;
	}	
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public boolean isCheckBoxChecked() {
		return checkBoxChecked;
	}

	public void setCheckBoxChecked(boolean checkBoxChecked) {
		this.checkBoxChecked = checkBoxChecked;
	}
	
	
	//Store data in bundle to be saved
    public static Bundle saveToBundle(Bundle args, Product product) {

    	//Store data in bundle to be saved
	    args.putInt("product_id", product.getProductId());
	    args.putInt("id", product.getId());	    
	    args.putString("name", product.getName());
	    args.putString("description", product.getDescription ());
	    args.putString("regular_price", product.getRegularPrice());
	    args.putString("sale_price", product.getSalePrice());	    
	    args.putString("image_url", product.getImageUrl());
	    args.putStringArray("colors", product.getColors());
	    
	    return args;	    
    }  		
	
	
    //Restore data from Bundle
    public static Product restoreFromBundle(Bundle args, Product product) {

    	//Get data from bundle 
	    product.setProductId(args.getInt("product_id", product.getProductId()));
	    product.setId(args.getInt("id", product.getId()));	    
	    product.setName(args.getString("name", product.getName()));
	    product.setDescription(args.getString("description", product.getDescription ()));
	    product.setRegularPrice(args.getString("regular_price", product.getRegularPrice()));
	    product.setSalePrice(args.getString("sale_price", product.getSalePrice()));	    
	    product.setImageUrl(args.getString("image_url", product.getImageUrl()));
	    product.setColors(args.getStringArray("colors"));
	    
	    return product;	    
    }      

}
