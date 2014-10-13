package local.saradeth.mike.product.create;


import java.util.ArrayList;
import java.util.List;

import local.saradeth.mike.product.R;
import local.saradeth.mike.product.adapter.ProductAdapter;
import local.saradeth.mike.product.adapter.ProductAdapter2;
import local.saradeth.mike.product.adapter.ProductAdapter3;
import local.saradeth.mike.product.adapter.ProductAdapter4;
import local.saradeth.mike.product.core.Globals;
import local.saradeth.mike.product.core.Product;
import local.saradeth.mike.product.dao.ProductDAO;
import local.saradeth.mike.product.detail.OnSwipeTouchListener;
import local.saradeth.mike.product.detail.ProductDetailActivity;
import local.saradeth.mike.product.lib.AlertDialogFragment;
import local.saradeth.mike.product.lib.DatabaseHelper;
import local.saradeth.mike.product.lib.ImageCache;
import local.saradeth.mike.product.lib.RetainFragment;
import local.saradeth.mike.product.lib.Util;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateProductActivity extends Activity implements AlertDialogFragment.AlertDialogListener, OnClickListener {

	private List<Product> alProduct = new ArrayList<Product>();
	
	private List<ProductTwoColumn> alProduct2 = new ArrayList<ProductTwoColumn>();	
    public class ProductTwoColumn {
	    	public Product col1 = new Product();;
	    	public Product col2 = new Product();
        }	
	
	private List<ProductThreeColumn> alProduct3 = new ArrayList<ProductThreeColumn>();
    public class ProductThreeColumn {
	    	public Product col1 = new Product();;
	    	public Product col2 = new Product();
	    	public Product col3 = new Product();
        }	    
    
	private List<ProductFourColumn> alProduct4 = new ArrayList<ProductFourColumn>();
    public class ProductFourColumn {
	    	public Product col1 = new Product();;
	    	public Product col2 = new Product();
	    	public Product col3 = new Product();
	    	public Product col4 = new Product();
    	}	     
    
	private ProductAdapter aaProduct;
	private ProductAdapter2 aaProduct2;
	private ProductAdapter3 aaProduct3;
	private ProductAdapter4 aaProduct4;
	private ListView lvProduct;
	private LayoutInflater layoutInflater;
	private Product product = new Product();
	private int position = 0;
	private String action;
	private TextView tvHeader; 
	private ImageCache imageCache = new ImageCache(); 

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_product_list);
						
		Bundle args = getIntent().getExtras();
		action = args.getString("action");
		
		//Load images from fragment cache if exist on Orientation change
		RetainFragment retainFragment = RetainFragment.findOrCreateRetainFragment(getFragmentManager());		
		if (retainFragment.mRetainedCache == null) {
	        retainFragment.mRetainedCache = imageCache.getmMemoryCache();			
		}else {
			imageCache.setmMemoryCache(retainFragment.mRetainedCache);
		}		
		
		//set title
		String title = args.getString("title");				
		Util.setActionBar(getActionBar(), title, this);
		setUpGestureListener();	//Swipe Left Next record, Swipe Right previous record
		
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	       
		if (action.equalsIgnoreCase("showProduct")) {
			//Load product from SQLite database
			alProduct = ProductDAO.loadProduct(this.getApplicationContext());		
			this.setProducts(alProduct);
		}else {
			//Load product from JSON file in asset folder
			LoadProductFromJSONFile loadProductFromFile = new LoadProductFromJSONFile(CreateProductActivity.this);		
	        try {
	        	loadProductFromFile.execute();
	        }
	        catch (Exception e){
	        	loadProductFromFile.cancel(true);
	            alert("Problem loading products from Json file.");
	        }					
		}
               			
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();		
	
	}
	
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);

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

	
	//Select all products  
	public void selectAllProducts() {
		for(int ii=0; ii<alProduct.size(); ii++) {			
			alProduct.get(ii).setCheckBoxChecked(true);		
		}
	}
	
	
	//Toast a given message
    public void alert (String msg){
        Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
	
    
    
    //setup and create list view after data is loaded
    public void setProducts(List<Product> products) {
		
        //Bind views to 
        Button btn_create_all = (Button) findViewById(R.id.btn_create_product_screen_create_all);
        LinearLayout btnLayout = (LinearLayout) findViewById(R.id.btn_create_product_layout);
		tvHeader = (TextView)findViewById(R.id.header);
		lvProduct = (ListView)findViewById(R.id.create_product_list_view);	
				
		if (action.equalsIgnoreCase("createProduct")) {
			//Remove products that are already in database form List
			products = removeExistProducts(products);
		}else {
			btnLayout.setVisibility(View.GONE);
		}
		tvHeader.setVisibility(View.VISIBLE);

		//setup ListView
    	this.alProduct = products; 
    	setProductTwoColumnArray(alProduct);
    	setProductThreeColumnArray(alProduct);
    	setProductFourColumnArray(alProduct);
    	
		layoutInflater = LayoutInflater.from(this);
		
		
		int orientation = getResources().getConfiguration().orientation;
		int screenSize = getResources().getConfiguration().screenLayout &Configuration.SCREENLAYOUT_SIZE_MASK;
		
/*		if (action.equalsIgnoreCase("createProduct")) {
			//Util.alert(this, "createProduct");
	    	aaProduct = new ProductAdapter(this, layoutInflater, alProduct, this, "CreateProductActivity", imageCache);    	
			lvProduct.setAdapter(aaProduct);					
		}else {	*/					
			if (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
				if (orientation == Configuration.ORIENTATION_PORTRAIT) {	
			    	aaProduct = new ProductAdapter(this, layoutInflater, alProduct, this, "CreateProductActivity", imageCache);    	  	
					lvProduct.setAdapter(aaProduct);				
				}else {
			    	aaProduct2 = new ProductAdapter2(this, layoutInflater, alProduct2, this, "CreateProductActivity", action, imageCache);    			    	    	
					lvProduct.setAdapter(aaProduct2);					
				}
			}
			
			if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
				//Util.alert(this, "SCREENLAYOUT_SIZE_LARGE");
				if (orientation == Configuration.ORIENTATION_PORTRAIT) {
					//Util.alert(this, "ORIENTATION_PORTRAIT");
			    	aaProduct2 = new ProductAdapter2(this, layoutInflater, alProduct2, this, "CreateProductActivity", action, imageCache);    			    	    	
					lvProduct.setAdapter(aaProduct2);				
				}else {
					//Util.alert(this, "ORIENTATION_LANDSCAPE");
			    	aaProduct3 = new ProductAdapter3(this, layoutInflater, alProduct3, this, "CreateProductActivity", action, imageCache);    			    	    	
					lvProduct.setAdapter(aaProduct3);					
				}
			}
			
			if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
				//Util.alert(this, "SCREENLAYOUT_SIZE_XLARGE");
				if (orientation == Configuration.ORIENTATION_PORTRAIT) {	
			    	aaProduct3 = new ProductAdapter3(this, layoutInflater, alProduct3, this, "CreateProductActivity", action, imageCache);    	
			    	updateHeader();    	
					lvProduct.setAdapter(aaProduct3);				
				}else {
					//Util.alert(this, "ORIENTATION_PORTRAIT");
			    	aaProduct4 = new ProductAdapter4(this, layoutInflater, alProduct4, this, "CreateProductActivity", action, imageCache);    			    	    	
					lvProduct.setAdapter(aaProduct4);					
				}
			}				
		/*}*/
		
		updateHeader();
		
		
		//Is there any product to show
		if (action.equalsIgnoreCase("showProduct") && alProduct.isEmpty()) {
			Util.alert(this, "No product in database to show");
		}		
		
		lvProduct.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub				
			}			
		});    	
		
		

        
        //Select all products
        btn_create_all.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String message = "Are you sure you want to create all products?";
				Util.showDialog(getFragmentManager(), getString(R.string.alert_dialog_create_product_title), message, "insertAll");					
						
			}        	
        });		
    	    	
    }
	
    
    public void setProductTwoColumnArray(List<Product> alProduct) {
    	alProduct2.clear();
    	for (int ii=0; ii<alProduct.size(); ii=ii+2) {
        	ProductTwoColumn myProduct = new ProductTwoColumn();
        	myProduct.col1 = alProduct.get(ii);  
        	
    		if (ii+1  < alProduct.size()) {
    			myProduct.col2 = alProduct.get(ii+1); 
    		}
    		alProduct2.add(myProduct);    		
    	}    	
    }
    
    public void setProductThreeColumnArray(List<Product> alProduct) {
    	alProduct3.clear();
    	for (int ii=0; ii<alProduct.size(); ii=ii+3) {
        	ProductThreeColumn myProduct = new ProductThreeColumn();
        	myProduct.col1 = alProduct.get(ii);  
    		if (ii+1  < alProduct.size()) {
    			myProduct.col2 = alProduct.get(ii+1); 
    		}  
    		if (ii+2  < alProduct.size()) {
    			myProduct.col3 = alProduct.get(ii+2); 
    		}      		
    		alProduct3.add(myProduct);    		
    	}
    }
        
    public void setProductFourColumnArray(List<Product> alProduct) {
    	alProduct4.clear();
    	for (int ii=0; ii<alProduct.size(); ii=ii+4) {
        	ProductFourColumn myProduct = new ProductFourColumn();
        	myProduct.col1 = alProduct.get(ii);  
    		if (ii+1  < alProduct.size()) {
    			myProduct.col2 = alProduct.get(ii+1); 
    		}  
    		if (ii+2  < alProduct.size()) {
    			myProduct.col3 = alProduct.get(ii+2); 
    		}   
    		if (ii+3  < alProduct.size()) {
    			myProduct.col4 = alProduct.get(ii+3); 
    		}        		
    		alProduct4.add(myProduct);    		
    	}
    }

    
    //Remove products that are already in database form List
    public List<Product> removeExistProducts(List<Product> products) {
    	List<Product> tmpProducts = new ArrayList<Product>();
    	Product product;
    	
    	SQLiteDatabase db = DatabaseHelper.getDatabase(getApplicationContext(), Globals.dbName);
    	
		for(int ii=0; ii<products.size(); ii++) {	
			product = products.get(ii);
			if (!Util.recordExist(db, product.getId())) {
				tmpProducts.add(product);
			}	
			
		}    	
    	db.close();
    	
    	if(tmpProducts.isEmpty()) {
    		Util.alert(getApplicationContext(), "No new product to add.");
    	}
    	
    	return tmpProducts;
    	
    }
    
    
	@Override
	public void onDialogPositiveClick(String tag) {
		int saveRecCount;
		
		Log.d("Tag", "Tag = " + tag);
		
		//If caller is not from createProduct return;
		if (!action.equalsIgnoreCase("createProduct")) {
			return;
		}
		
		//Code to handle Create product 
		if (tag.equalsIgnoreCase("insertAll")) {
			saveRecCount = ProductDAO.insertProduct(getApplicationContext(), alProduct);
			alProduct.clear();
			this.updateHeader();	
			if (aaProduct != null)
				aaProduct.notifyDataSetChanged();	
			String msg;
			if (saveRecCount > 1) {
				msg = saveRecCount + " products saved.";	
			}else{
				msg = saveRecCount + " product saved.";	
			}						
			alert(msg);	
			
			updateUI();			
		}else {
			saveRecCount = ProductDAO.insertProduct(getApplicationContext(), product);
			alProduct.remove(position);
			this.updateHeader();
			aaProduct.notifyDataSetChanged();
			//Set up toast message
			String msg;
			if (saveRecCount > 1) {
				msg = saveRecCount + " products saved.";	
			}else{
				msg = saveRecCount + " product saved.";	
			}						
			alert(msg);				
		}	
	
		
	}


	@Override
	public void onDialogNegativeClick(String tag) {
		alert("Canceled");				
	}

	//Update UI to remove records that was created from screen
	public void updateUI() {		
		setProducts(alProduct);
	}


	@Override
	public void onClick(View view) {		
		//If caller is viewProduct return;
		if (action.equalsIgnoreCase("viewProduct")) {
			return;
		}
		
		//Get position pressed and product object
		ViewHolder holder = (ViewHolder) view.getTag();
		position = holder.position; 
		product = alProduct.get(position);	
				
		
		if (action.equalsIgnoreCase("createProduct")) {
			String message = "Are you sure you want to create this product?";
			Util.showDialog(getFragmentManager(), getString(R.string.alert_dialog_create_product_title), message, "addRecord");			
		}	
		
		if (action.equalsIgnoreCase("showProduct")) {			
			Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
			intent.putExtra("productId", product.getProductId());;
										
			startActivity(intent);
		}		
				
		

	}	

	
	public void updateHeader() {
		
		tvHeader.setVisibility(View.VISIBLE);
	    int productCount = alProduct.size();
	    if (productCount > 1) {
	    	tvHeader.setText(productCount + " records");
	    }else {
	    	tvHeader.setText(productCount + " record");
	    }	
	    
	}
	
	private void setUpGestureListener() {
		View view = (View) findViewById(R.id.create_product_list_view);
		//View view = (View) findViewById(R.id.product_detail_layout);
		view.setOnTouchListener(new OnSwipeTouchListener() {
		    public void onSwipeTop() {
		    	tvHeader.setVisibility(View.GONE);
		        //Toast.makeText(getBaseContext(), "top", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeRight() {		    			    	
		    	//do nothing
		    }
		    public void onSwipeLeft() {
		    	//do nothing
		    }
		    public void onSwipeBottom() {
		    	tvHeader.setVisibility(View.VISIBLE);
		    }
		});
		
	}
		

/*public class RetainFragment extends Fragment {
    private static final String TAG = "RetainFragment";
    public LruCache<String, Bitmap> mRetainedCache;
   

    public RetainFragment() {}

    public static RetainFragment findOrCreateRetainFragment(FragmentManager fm) {
        RetainFragment fragment = (RetainFragment) fm.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new RetainFragment();
            fm.beginTransaction().add(fragment, TAG).commit();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

}
*/		
}
