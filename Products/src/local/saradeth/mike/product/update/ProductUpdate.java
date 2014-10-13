package local.saradeth.mike.product.update;


import java.io.IOException;
import java.io.InputStream;
import local.saradeth.mike.product.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import local.saradeth.mike.product.core.Product;
import local.saradeth.mike.product.dao.ProductDAO;
import local.saradeth.mike.product.detail.LargeImageActivity;
import local.saradeth.mike.product.lib.AlertDialogFragment;
import local.saradeth.mike.product.lib.ImageCache;
import local.saradeth.mike.product.lib.Util;

public class ProductUpdate extends Activity implements AlertDialogFragment.AlertDialogListener {

	Product product = new Product(); 
	Product orgProduct = new Product();  
	
	EditText productName; 
	EditText regularPrice; 
	EditText salePrice;  
	EditText description;
	ImageView image;

	
	public ProductUpdate() {
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_update);
		Util.setActionBar(getActionBar(), "Product Update", this);
		Bundle args = getIntent().getExtras();
		
		if (savedInstanceState != null) {
			//Restore objects from bundle 
			product = Product.restoreFromBundle(savedInstanceState, product);			
			orgProduct = Product.restoreFromBundle(savedInstanceState, orgProduct);			
		}else {			
			//First time creating Activity
			product = ProductDAO.loadProduct(getApplicationContext(), args.getInt("productId"));
			orgProduct = product;
		}
		
		updateUI();

	}

	
	@Override
	protected void onStart() {
		super.onStart();			
	}	
	

	
	public void updateUI() {
		
		//Bind View to instance variables
		productName = (EditText)findViewById(R.id.detail_product_name);
		image = (ImageView)findViewById(R.id.detail_product_image);
		regularPrice = (EditText) findViewById (R.id.detail_product_regular_price);
		salePrice = (EditText) findViewById (R.id.product_detail_sale_price);	
		description = (EditText) findViewById (R.id.detail_product_description);	
		
		Button btnCancel = (Button) findViewById (R.id.btn_detail_product_cancel);
		Button btnSave = (Button) findViewById (R.id.btn_detail_product_update);
		
		
		//Display values
		productName.setText(product.getName());
		regularPrice.setText(product.getRegularPrice());
		salePrice.setText(product.getSalePrice());
		description.setText(product.getDescription());
		
		//recycle old image and free memory
		Util.recycleImage(image);		
    	//load product image from asset folder if available
   		Drawable dr = loadImage(product.getImageUrl());
   		if (dr != null) {
   			image.setImageDrawable(dr);
   		}else {
   			image.setImageResource(R.drawable.ic_launcher);	//Filler Icon
   		} 
   		
        //Save selected product to SQLite database 
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Util.showDialog(getFragmentManager(), "Update Product", "Are you sure you want to save changes?", 1);					
			}
        	
        });   
		
        //Save selected product to SQLite database 
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				product = orgProduct;
				updateUI();		
			}
        	
        }); 
		
   		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), LargeImageActivity.class);
				intent.putExtra("url", product.getImageUrl());;
											
				startActivity(intent);					
			}   			
   		});		
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		//Set data to be saved
		product.setName(productName.getText().toString());
		product.setRegularPrice(regularPrice.getText().toString());
		product.setSalePrice(salePrice.getText().toString());
		product.setDescription(description.getText().toString());
		
		//Save object product in bundle
		outState = Product.saveToBundle(outState, product);		
		//Save object orgProduct in bundle
		outState = Product.saveToBundle(outState, orgProduct);
		
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {		
		super.onRestoreInstanceState(savedInstanceState);
		
	}
		
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	
	
	

	
	
	@Override
	public void onDialogPositiveClick(String tag) {
		
		//Set product object with update values
		product.setName(productName.getText().toString());
		product.setRegularPrice(regularPrice.getText().toString());
		product.setSalePrice(salePrice.getText().toString());
		product.setDescription(description.getText().toString());
		
		//Update database table with product 
		ProductDAO.update(getApplicationContext(), product);
		
		alert("Product updated successfully.");	
		orgProduct = product;	//Save new updated record
		
		//Let other activity know data has been updated
		Intent returnIntent = new Intent();
		returnIntent.putExtra("productId", product.getProductId());
		setResult(RESULT_OK, returnIntent);
		finish();		
	}

	@Override
	public void onDialogNegativeClick(String tag) {
		alert("Canceled");			
	}

	
	//Returns percent that customer will save by buying the product 
	public String getYouSave(String s_regularPrice, String s_salePrice) {
		String youSave = "";
		float regPrice = Float.valueOf(s_regularPrice); 
		float salePrice = Float.valueOf(s_salePrice); 
		float result = 0;
		
		if (salePrice != 0.0 && regPrice != 0.0) {
			result = ((regPrice - salePrice)/regPrice) * 100;
		}
		//convert to percent
		youSave = "You save " + String.format("%.0f%%", result);
		
		return youSave;		
	}
	
		

	//Returns colors available for the product
	public String getColors(String[] colorsArray) {
		String colors = "";

   		if (colorsArray.length > 1) {
   			colors = "colors:  ";
   		}else {
   			colors = "color:  ";
   		}   		
   		for(int ii=0; ii<colorsArray.length; ii++) {   
   			if (colorsArray[ii] == null || colorsArray[ii].isEmpty() ) {
   				continue;
   			}
   			
   			if (ii==0) {
   				colors = colors + colorsArray[ii];
   			}else {
   				colors = colors + ", " + colorsArray[ii];
   			}   			
   		}
   		
		return colors;		
	}
		
	
	
	//Toast a given message
    public void alert (String msg){
        Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
		
	//Load image from asset folder and save it in memory
	public Drawable loadImage(String url) {
		Drawable image = null;
		
   		if (url != null) {
   	        try {
   	            // get input stream
   	            InputStream is = getAssets().open(url);
   	            // load image as Drawable   	            
   	         	image = Drawable.createFromStream(is, null);   	
   	         	is.close();
   	        }
   	        catch(IOException e) {
   	            Log.d("ImageLoadAndCache", "I/O : " + e.getMessage());     
   	        }    			  			
   		} 	
   		
   		return image;
	}    
	
	

}
