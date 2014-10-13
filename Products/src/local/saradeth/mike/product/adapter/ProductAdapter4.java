package local.saradeth.mike.product.adapter;


import java.util.ArrayList;
import java.util.List;
import local.saradeth.mike.product.R;
import local.saradeth.mike.product.core.Product;
import local.saradeth.mike.product.create.CreateProductActivity.ProductFourColumn;
import local.saradeth.mike.product.create.CreateProductActivity.ProductThreeColumn;
import local.saradeth.mike.product.create.ViewHolder;
import local.saradeth.mike.product.create.CreateProductActivity.ProductTwoColumn;
import local.saradeth.mike.product.dao.ProductDAO;
import local.saradeth.mike.product.detail.LargeImageActivity;
import local.saradeth.mike.product.detail.ProductDetailActivity;
import local.saradeth.mike.product.lib.ImageCache;
import local.saradeth.mike.product.lib.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RelativeLayout;

public class ProductAdapter4 extends BaseAdapter {
	private OnClickListener callback;
	private String callFrom;
        
	private ProductFourColumn fourColproduct;
	private Activity activity;
	private LayoutInflater layoutInflater;
	private List<ProductFourColumn> alProduct;
	private ImageCache imageCache;
	private String action;
	private int myPosition;	
	
	//Constructor
	public ProductAdapter4(Activity activity, LayoutInflater layoutInflater, List<ProductFourColumn> alProduct, OnClickListener callback, String callFrom, String action, ImageCache imageCache) {		
		this.activity = activity;
		this.layoutInflater = layoutInflater; 
		this.alProduct = alProduct;
		this.callback = callback;
		this.callFrom = callFrom;
		this.imageCache = imageCache;
		this.action = action;
	
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		this.myPosition = position;
		List<ViewHolder> alHolder = new ArrayList<ViewHolder>(); 
		
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.create_product_row, parent, false);
			
			//Bind data to View
			ViewHolder holder = new ViewHolder(); 
			holder.name = (TextView) convertView.findViewById(R.id.create_product_name);
			holder.regularPrice = (TextView) convertView.findViewById(R.id.create_product_regular_price);
			holder.salePrice = (TextView) convertView.findViewById(R.id.create_product_sale_price);
			holder.imageView = (ImageView) convertView.findViewById(R.id.create_product_image);
			holder.youSave = (TextView) convertView.findViewById(R.id.create_product_you_save);
			holder.regularPriceLabel = (TextView) convertView.findViewById(R.id.create_product_regular_price_label);
			holder.salePriceLabel = (TextView) convertView.findViewById(R.id.create_product_sale_price_label);
			holder.product_column1_layout = (RelativeLayout) convertView.findViewById(R.id.product_column1_layout);
			holder.product_column2_layout = (RelativeLayout ) convertView.findViewById(R.id.product_column2_layout);
			holder.product_column3_layout = (RelativeLayout ) convertView.findViewById(R.id.product_column3_layout);
			holder.product_column4_layout = (RelativeLayout ) convertView.findViewById(R.id.product_column4_layout);
			alHolder.add(holder);
			
			ViewHolder holder2 = new ViewHolder(); 
			holder2.name = (TextView) convertView.findViewById(R.id.create_product_name2);
			holder2.regularPrice = (TextView) convertView.findViewById(R.id.create_product_regular_price2);
			holder2.salePrice = (TextView) convertView.findViewById(R.id.create_product_sale_price2);
			holder2.imageView = (ImageView) convertView.findViewById(R.id.create_product_image2);
			holder2.youSave = (TextView) convertView.findViewById(R.id.create_product_you_save2);	
			holder2.regularPriceLabel = (TextView) convertView.findViewById(R.id.create_product_regular_price_label2);
			holder2.salePriceLabel = (TextView) convertView.findViewById(R.id.create_product_sale_price_label2);
			holder2.product_column1_layout = (RelativeLayout) convertView.findViewById(R.id.product_column1_layout);
			holder2.product_column2_layout = (RelativeLayout ) convertView.findViewById(R.id.product_column2_layout);
			holder2.product_column3_layout = (RelativeLayout ) convertView.findViewById(R.id.product_column3_layout);
			holder2.product_column4_layout = (RelativeLayout ) convertView.findViewById(R.id.product_column4_layout);
			alHolder.add(holder2);
			
			ViewHolder holder3 = new ViewHolder(); 
			holder3.name = (TextView) convertView.findViewById(R.id.create_product_name3);
			holder3.regularPrice = (TextView) convertView.findViewById(R.id.create_product_regular_price3);
			holder3.salePrice = (TextView) convertView.findViewById(R.id.create_product_sale_price3);
			holder3.imageView = (ImageView) convertView.findViewById(R.id.create_product_image3);
			holder3.youSave = (TextView) convertView.findViewById(R.id.create_product_you_save3);	
			holder3.regularPriceLabel = (TextView) convertView.findViewById(R.id.create_product_regular_price_label3);
			holder3.salePriceLabel = (TextView) convertView.findViewById(R.id.create_product_sale_price_label3);
			holder3.product_column1_layout = (RelativeLayout) convertView.findViewById(R.id.product_column1_layout);
			holder3.product_column2_layout = (RelativeLayout ) convertView.findViewById(R.id.product_column2_layout);
			holder3.product_column3_layout = (RelativeLayout ) convertView.findViewById(R.id.product_column3_layout);
			holder3.product_column4_layout = (RelativeLayout ) convertView.findViewById(R.id.product_column4_layout);
			alHolder.add(holder3);	
			
			ViewHolder holder4 = new ViewHolder(); 
			holder4.name = (TextView) convertView.findViewById(R.id.create_product_name4);
			holder4.regularPrice = (TextView) convertView.findViewById(R.id.create_product_regular_price4);
			holder4.salePrice = (TextView) convertView.findViewById(R.id.create_product_sale_price4);
			holder4.imageView = (ImageView) convertView.findViewById(R.id.create_product_image4);
			holder4.youSave = (TextView) convertView.findViewById(R.id.create_product_you_save4);	
			holder4.regularPriceLabel = (TextView) convertView.findViewById(R.id.create_product_regular_price_label4);
			holder4.salePriceLabel = (TextView) convertView.findViewById(R.id.create_product_sale_price_label4);
			holder4.product_column1_layout = (RelativeLayout) convertView.findViewById(R.id.product_column1_layout);
			holder4.product_column2_layout = (RelativeLayout ) convertView.findViewById(R.id.product_column2_layout);
			holder4.product_column3_layout = (RelativeLayout ) convertView.findViewById(R.id.product_column3_layout);
			holder4.product_column4_layout = (RelativeLayout ) convertView.findViewById(R.id.product_column4_layout);
			alHolder.add(holder4);			
			
		}else {
			alHolder = (List<ViewHolder>) convertView.getTag();
		}		
		//Set the position as tag so it can be retrieved from the click listener because the click listener itself does not provide the position like done in the onItemClickListener				
		convertView.setTag(alHolder);			
		
		//Get product base on current position
		fourColproduct = alProduct.get(position);
   		
    	for (int ii=0; ii<alHolder.size(); ii=ii+1) { 
       		//Save data to holder
       		ViewHolder holder = alHolder.get(ii);
       		holder.position = position;
       		Product product = new Product();
       		
       		switch (ii) {
       			case 0:
       				product = fourColproduct.col1;
       				break;
       			case 1: 
       				product = fourColproduct.col2;
       				break;  
       			case 2: 
       				product = fourColproduct.col3;
       				break;
       			case 3: 
       				product = fourColproduct.col4;
       				break;         				
       		}      
       		if (product != null) {
           		holder.url = product.getImageUrl();
           		//Display data to view
           		displayData(holder, product, fourColproduct);         			
       		}
 
    	}
    	   		   		
   		//return view
		return convertView;
	}
	
	
	
	//Display data to view
	public void displayData(ViewHolder holder, Product product, ProductFourColumn fourColproduct) {

   		holder.name.setText(product.getName());   		
   		holder.regularPrice.setText(Util.money(product.getRegularPrice()));   	
   		holder.regularPrice.setPaintFlags(holder.regularPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
   		holder.salePrice.setText(Util.money(product.getSalePrice()));		
   		holder.youSave.setText(getYouSave(product));
   		
   		//Clear label on null record
   		if (product.getId()==0){
   			holder.salePriceLabel.setText("");
   			holder.regularPriceLabel.setText("");
   		}
   		
    	//Draw from memory cache if exist else load product image from asset folder and cache it
   		if (holder.url.isEmpty() || product.getId()==0) {
   			holder.imageView.setImageDrawable(null);	//clear image
   			
   		}else {
   			imageCache.drawImage(activity, holder);
   		}
   		
   		final String tmpUrl = product.getImageUrl();
   		holder.imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {				
				String url = tmpUrl;
				if (url.isEmpty()) return;
				
				Intent intent = new Intent(activity, LargeImageActivity.class);
				intent.putExtra("url", url);
											
				activity.startActivity(intent);					
			}   			
   		}); 
   		
   		final ViewHolder tmpHolder = holder;
   		final Product tmpProduct = fourColproduct.col1;
        holder.product_column1_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	doOnclick(tmpHolder, tmpProduct, 1);
            }
        });
        
        final Product tmpProduct2 = fourColproduct.col2;
        holder.product_column2_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	doOnclick(tmpHolder, tmpProduct2, 2);
            }
        });   
        
        
        final Product tmpProduct3 = fourColproduct.col3;
        holder.product_column3_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	doOnclick(tmpHolder, tmpProduct3, 3);
            }
        });          
            
        final Product tmpProduct4 = fourColproduct.col4;
        holder.product_column4_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	doOnclick(tmpHolder, tmpProduct4, 4);
            }
        });          
                    
   		   		
	}
		
	
	public void doOnclick(ViewHolder holder, Product product, int col)  {
		if(product.getId() ==  0)
			return;
		
		
		//If caller is viewProduct return;
		if (action.equalsIgnoreCase("viewProduct")) {
			return;
		}

		if (action.equalsIgnoreCase("createProduct")) {
			String message = "Are you sure you want to create this product?";
			areYouSure(activity.getString(R.string.alert_dialog_create_product_title), message, col, holder.position);
			notifyDataSetChanged();
		}	
		
		if (action.equalsIgnoreCase("showProduct")) {			
			Intent intent = new Intent(activity.getApplicationContext(), ProductDetailActivity.class);
			intent.putExtra("productId", product.getProductId());;
										
			activity.startActivity(intent);
		}		
					
	}
	
		
	
	
	
	//Returns percent that customer will save by buying the product 
	//public String getYouSave(String s_regularPrice, String s_salePrice) {
	public String getYouSave(Product product) {
		String youSave = "";
		if (product.getRegularPrice().isEmpty() || product.getId()==0) 
			return "";
		
		String s_regularPrice = product.getRegularPrice();
		String s_salePrice = product.getSalePrice();
		
		if (s_regularPrice.isEmpty() || s_salePrice.isEmpty())
			return "";
		
		if (s_regularPrice == null || s_salePrice == null )
			return "";		
		
		float regPrice = Float.valueOf(s_regularPrice); 
		float salePrice = Float.valueOf(s_salePrice); 
		float result = 0;
		
		if (regPrice == 0.0)
			return "";		
				
		
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
   			if (ii==0) {
   				colors = colors + colorsArray[ii];
   			}else {
   				colors = colors + ", " + colorsArray[ii];
   			}   			
   		}
   		
		return colors;		
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alProduct.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

    @Override
    public boolean areAllItemsEnabled () {
    	return true;
    }	
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	
	public void areYouSure(String title, String message, int col, int position) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    	
        builder.setTitle(title);
        builder.setMessage(message);
        
        final int column = col;
        final int pos = position;
        builder.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {    
            	   switch (column) {
	            	   case 1: 
	            		   ProductDAO.insertProduct(activity.getApplicationContext(), alProduct.get(pos).col1);
	            		   Util.alert(activity, "product saved.");
	            		   alProduct.get(pos).col1 = new Product(); //clear record and UI
	            		   break;
	            	   case 2: 
	            		   ProductDAO.insertProduct(activity.getApplicationContext(), alProduct.get(pos).col2);
	            		   Util.alert(activity, "product saved.");
	            		   alProduct.get(pos).col2 = new Product(); //clear record and UI
	            		   break;   
	            		   
	            	   case 3: 
	            		   ProductDAO.insertProduct(activity.getApplicationContext(), alProduct.get(pos).col3);
	            		   Util.alert(activity, "product saved.");
	            		   alProduct.get(pos).col3 = new Product(); //clear record and UI
	            		   notifyDataSetChanged();
	            		   break;	
	            		   
	            	   case 4: 
	            		   ProductDAO.insertProduct(activity.getApplicationContext(), alProduct.get(pos).col4);
	            		   Util.alert(activity, "product saved.");
	            		   alProduct.get(pos).col4 = new Product(); //clear record and UI
	            		   notifyDataSetChanged();
	            		   break;	  	            		   
            	   }
            	   notifyDataSetChanged();
           		}
               })
               .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   Util.alert(activity, "Canceled");
                   }
               });        

        builder.create().show();
	
	}	
	

}
