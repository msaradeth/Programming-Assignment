package local.saradeth.mike.product.detail;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import local.saradeth.mike.product.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import local.saradeth.mike.product.adapter.ProductAdapter;
import local.saradeth.mike.product.core.Product;
import local.saradeth.mike.product.dao.ProductDAO;
import local.saradeth.mike.product.lib.AlertDialogFragment;
import local.saradeth.mike.product.lib.ImageCache;
import local.saradeth.mike.product.lib.RetainFragment;
import local.saradeth.mike.product.lib.Util;
import local.saradeth.mike.product.update.ProductUpdate;

public class ProductDetailActivity extends Activity implements AlertDialogFragment.AlertDialogListener, OnClickListener {

	private Product product;
	private TextView productName;
	private boolean productDeleted = false;
	
	//Setup Right Drawer
    private DrawerLayout rightDrawerLayout;
    private ListView lvRightDrawer;
	private ProductAdapter aaProduct;
	private LayoutInflater layoutInflater;    
	private List<Product> alProduct = new ArrayList<Product>();
	private int currRecord = 0;
	private TextView tvHeader; 
	private ImageCache imageCache = new ImageCache();
	
	
	public ProductDetailActivity() {
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail);

		Bundle args = getIntent().getExtras();
		int productId;
		
		//Get current record on orientation change
		if (savedInstanceState != null) {			
			productId = savedInstanceState.getInt("productId", args.getInt("productId"));
		}else {
			productId = args.getInt("productId");											
		}
		product = ProductDAO.loadProduct(getApplicationContext(), productId);

		//Load images from fragment cache if exist on Orientation change
		RetainFragment retainFragment = RetainFragment.findOrCreateRetainFragment(getFragmentManager());		
		if (retainFragment.mRetainedCache == null) {
	        retainFragment.mRetainedCache = imageCache.getmMemoryCache();			
		}else {
			imageCache.setmMemoryCache(retainFragment.mRetainedCache);
		}		
		
		//Load products array for right drawer
		alProduct = ProductDAO.loadProduct(this.getApplicationContext());
		setUpRightDrawer();
		setUpGestureListener();	//Swipe Left Next record, Swipe Right previous record
		setCurrentProduct();	//Keep track of current record for Next and Previous
		Util.setActionBar(getActionBar(), "Product Detail", this);
		setDrawerListener();
		
		updateUI();		
	}


	
	
	public void setDrawerListener() {
		ImageView imageRightDrawer = (ImageView) findViewById(R.id.image_right_drawer); 
		imageRightDrawer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if  (rightDrawerLayout.isDrawerOpen(lvRightDrawer)) {
					rightDrawerLayout.closeDrawer(lvRightDrawer);
				} else {
					rightDrawerLayout.openDrawer(lvRightDrawer);
				}				
			}			
		});	
	}
		
	
	
	
	public void updateUI() {
		
		//Bind View to instance variables
		tvHeader = (TextView)findViewById(R.id.header);
		productName = (TextView)findViewById(R.id.detail_product_name);
		ImageView imageView = (ImageView)findViewById(R.id.detail_product_image);
		TextView colors = (TextView) findViewById (R.id.detail_product_colors);
		TextView regularPrice = (TextView) findViewById (R.id.detail_product_regular_price);
		TextView salePrice = (TextView) findViewById (R.id.product_detail_sale_price);
		TextView youSave = (TextView) findViewById (R.id.detail_product_you_save);	
		TextView description = (TextView) findViewById (R.id.detail_product_description);	
		Button btnDelete = (Button) findViewById (R.id.btn_detail_product_delete);
		Button btnUpdate = (Button) findViewById (R.id.btn_detail_product_update);
		
		
		//Display values
		productName.setText(product.getName());
		regularPrice.setText(Util.money(product.getRegularPrice()));
		regularPrice.setPaintFlags(regularPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		salePrice.setText(Util.money(product.getSalePrice()));
		youSave.setText(getYouSave(product.getRegularPrice(), product.getSalePrice()));
		colors.setText(getColors(product.getColors()));
		description.setText(product.getDescription());
		
		//Update header - current record
		updateHeader();
		
		
    	//Draw from memory cache if exist else load product image from asset folder and cache
   		imageCache.drawImage(this, product.getImageUrl(), imageView); 		
		
/*    	//load product image from asset folder if available
   		Drawable dr = loadImage(product.getImageUrl());
   		if (dr != null) {
   			image.setImageDrawable(dr);
   		}else {
   			image.setImageResource(R.drawable.ic_launcher);	//Filler Icon
   		}   
   		*/
   		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), LargeImageActivity.class);
				intent.putExtra("url", product.getImageUrl());;
											
				startActivity(intent);					
			}
   			
   		});
   		
   		
        //Delete selected product from SQLite database 
   		btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (productDeleted) {
					alert("This product has been deleted!");
					return;
				} 
				Util.showDialog(getFragmentManager(), "Delete Product", "Are you sure you want to delete this product?", 1);					
			}
        	
        });    
   		
   		//Update product information
   		btnUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (productDeleted) {
					alert("This product has been deleted!");
					return;
				}
				
				Intent intent = new Intent(getApplicationContext(), ProductUpdate.class);
				intent.putExtra("productId", product.getProductId());;
		
				startActivityForResult(intent, 1);					
			}
        	
        });        		
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
   	        }
   	        catch(IOException e) {
   	            Log.d("ImageLoadAndCache", "I/O : " + e.getMessage());     
   	        }    			  			
   		} 	
   		
   		return image;
	}	
	
	public void setUpRightDrawer() {

		layoutInflater = LayoutInflater.from(this);
		
        rightDrawerLayout = (DrawerLayout) findViewById(R.id.right_drawer_layout);	        
        lvRightDrawer = (ListView) findViewById(R.id.right_drawer);
        lvRightDrawer.setTag("right_drawer");
        
        // set a custom shadow that overlays the main content when the drawer opens
        rightDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener	       
        //aaProduct = new RightDrawerAdapter(this, layoutInflater, alProduct, imageCache);
        aaProduct = new ProductAdapter(this, layoutInflater, alProduct, this, "ProductDetailActivity", imageCache);
    	                
		lvRightDrawer.setAdapter(aaProduct);
        lvRightDrawer.setOnItemClickListener(new DrawerItemClickListener());
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
		
		imageCache.clearCache();
		System.gc();
	}
	
	
	
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		//Save data for current record
		outState.putInt("productId", product.getProductId());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);

	}
	
	
	
	@Override
	public void onDialogPositiveClick(String tag) {
		//Delete product from database.
		ProductDAO.delete(getApplicationContext(), product);					
		alert("Product deleted successfully.");
		alProduct.remove(currRecord-1);//zero base array, subtract 1
		aaProduct.notifyDataSetChanged();
		
		
		//Load products array for right drawer
		//alProduct = ProductDAO.loadProduct(this.getApplicationContext());
		if (alProduct.size() > 0) {
			//set new current record
			if (currRecord > alProduct.size()) {
				currRecord = alProduct.size();				
			}			
			product = alProduct.get(currRecord-1);  //zero base array, subtract 1
				
		}else {
			//Strike product name and make it red to indicate it has been delete.
			productName.setPaintFlags(productName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			productName.setTextColor(Color.RED);
			productDeleted = true;	
			currRecord = 0;
			//product = new Product(); //No more record left.  Set to empty record
		}		
		updateUI();			
		
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
		
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            //Update UI with new data
	        	int productId  = data.getIntExtra("ProductId", product.getProductId());
	        	product = ProductDAO.loadProduct(getApplicationContext(), productId);
	        	this.updateUI();	            
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Do nothing to UI
	        }
	    }		
	}


	
	
	//Toast a given message
    public void alert (String msg){
        Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	int tabPosition = 0;
        	int row = (int) id;
        	
 			if (row == -1) { 
 				return;	//Do nothing when select header
 			}
 			        	
        	//Process Right Drawer Selection
        	if (parent.getTag().toString().equalsIgnoreCase("right_drawer")) {
        		Log.i("right_drawer clicked", "row = " + id);	
        		product = alProduct.get(row);        		        	
        		currRecord = row+1;  
        		rightDrawerLayout.closeDrawer(lvRightDrawer);
        		updateUI();
        	} 
        	
        }
    }
	
	private void setUpGestureListener() {
		View view = (View) findViewById(R.id.scrollViewLayOut);
		view.setOnTouchListener(new OnSwipeTouchListener() {
		    public void onSwipeTop() {
		        //Toast.makeText(getBaseContext(), "top", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeRight() {		    			    	
		    	previousRecord();
		        //Toast.makeText(getBaseContext(), "right", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeLeft() {
		    	nextRecord();		    	
		        //Toast.makeText(getBaseContext(), "left", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeBottom() {
		    	//previousRecord();
		        //Toast.makeText(getBaseContext(), "bottom", Toast.LENGTH_SHORT).show();
		    }
		});
		
	}
	

	public void nextRecord() {

		if (currRecord == alProduct.size()) {
			Toast.makeText(this, "End of list", Toast.LENGTH_SHORT).show();			
		}else {
			currRecord = currRecord +1;
			product = alProduct.get(currRecord-1);	//zero base array, subtract 1
			updateUI();  
		}	
		
	}
	
	public void previousRecord() {
		if (currRecord == 1) {
			Toast.makeText(this, "End of list", Toast.LENGTH_SHORT).show();			
		}else {
			currRecord = currRecord -1;
			product = alProduct.get(currRecord-1); 
			updateUI();
		}
		
	}
	
	
	private void setCurrentProduct() {		
		for (int position=0; position<alProduct.size();  position++) {
			if ( product.getProductId() == alProduct.get(position).getProductId()) {						
				currRecord = position + 1;
			}
		}			
	}	
	
	public void updateHeader() {		
		tvHeader.setVisibility(View.VISIBLE);
	    int productCount = alProduct.size();
	    tvHeader.setText(currRecord + " of " + productCount);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
