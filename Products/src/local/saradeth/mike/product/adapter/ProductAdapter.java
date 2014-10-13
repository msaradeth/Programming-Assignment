package local.saradeth.mike.product.adapter;


import java.util.List;
import local.saradeth.mike.product.R;
import local.saradeth.mike.product.core.Product;
import local.saradeth.mike.product.create.ViewHolder;
import local.saradeth.mike.product.detail.LargeImageActivity;
import local.saradeth.mike.product.lib.ImageCache;
import local.saradeth.mike.product.lib.Util;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {
	private OnClickListener callback;
	private String callFrom;
        
	private Product product;
	private Activity activity;
	private LayoutInflater layoutInflater;
	private List<Product> alProduct;
	private ImageCache imageCache;
	
	//Constructor
	public ProductAdapter(Activity activity, LayoutInflater layoutInflater, List<Product> alProduct, OnClickListener callback, String callFrom, ImageCache imageCache) {		
		this.activity = activity;
		this.layoutInflater = layoutInflater; 
		this.alProduct = alProduct;
		this.callback = callback;
		this.callFrom = callFrom;
		this.imageCache = imageCache;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		int orientation = activity.getResources().getConfiguration().orientation;
		int screenSize = activity.getResources().getConfiguration().screenLayout &Configuration.SCREENLAYOUT_SIZE_MASK;
				
		
		if (convertView == null) {
			if (orientation == Configuration.ORIENTATION_PORTRAIT && screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL ) {
				convertView = layoutInflater.inflate(R.layout.create_product_row, parent, false);
			}else {
				convertView = layoutInflater.inflate(R.layout.create_product_row_one_col, parent, false);
			}
			
			//Set a click listener callback 
			if (!callFrom.equalsIgnoreCase("ProductDetailActivity")) {
				convertView.setOnClickListener(callback);
			}
			
			
			//Bind View to data
			holder.name = (TextView) convertView.findViewById(R.id.create_product_name);
			holder.regularPrice = (TextView) convertView.findViewById(R.id.create_product_regular_price);
			holder.salePrice = (TextView) convertView.findViewById(R.id.create_product_sale_price);
			holder.imageView = (ImageView) convertView.findViewById(R.id.create_product_image);
			holder.youSave = (TextView) convertView.findViewById(R.id.create_product_you_save);
	
		}else {
			holder = (ViewHolder) convertView.getTag();
		}		
		//Set the position as tag so it can be retrieved from the click listener because the click listener itself does not provide the position like done in the onItemClickListener				
		convertView.setTag(holder);			
		
		//Get product base on current position
   		product = alProduct.get(position);
   		
   		//Save data to holder
   		holder.position = position;	
   		holder.url = product.getImageUrl();
   		   	
   		
   		//Display values
   		holder.name.setText(product.getName());
   		holder.regularPrice.setText(Util.money(product.getRegularPrice()));
   		holder.regularPrice.setPaintFlags(holder.regularPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
   		holder.salePrice.setText(Util.money(product.getSalePrice()));		
   		holder.youSave.setText(getYouSave(product.getRegularPrice(), product.getSalePrice()));
   		
    	//Draw from memory cache if exist else load product image from asset folder and cache
   		imageCache.drawImage(activity, holder);   	
   		
   		final int pos = position;
   		holder.imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//Get current position	
				//int position = (Integer) view.getTag();
	
				String url = alProduct.get(pos).getImageUrl();
				Intent intent = new Intent(activity, LargeImageActivity.class);
				intent.putExtra("url", url);
											
				activity.startActivity(intent);					
			}   			
   		});   		
   		   		
   		//return view
		return convertView;
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


}
