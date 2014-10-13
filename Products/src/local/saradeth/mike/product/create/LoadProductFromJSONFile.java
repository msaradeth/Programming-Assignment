package local.saradeth.mike.product.create;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import local.saradeth.mike.product.R;
import local.saradeth.mike.product.core.Globals;
import local.saradeth.mike.product.core.Product;
import local.saradeth.mike.product.core.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;


public class LoadProductFromJSONFile extends AsyncTask<String, Integer, String>{
	private CreateProductActivity activity;
	private ProgressDialog progDialog;
	private Context context;		
	
	//Constructor 
	public LoadProductFromJSONFile(CreateProductActivity activity) {
		this.activity = activity;
		context = activity.getApplicationContext();
	}

	
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progDialog = ProgressDialog.show(this.activity, activity.getString(R.string.progress_dialog_title_products), activity.getString(R.string.progress_dialog_message_products), true, false);

	}

	
	@Override
	protected String doInBackground(String... params) {
		String fileName = Globals.fileName;
		AssetManager assetManager = activity.getAssets();
	    String json = "";
	    try {
	    	InputStream input = assetManager.open(fileName);

	        int size = input.available();
	        byte[] buffer = new byte[size];
	        input.read(buffer);
	        input.close();

	        //byte buffer into a string
	        json = new String(buffer, "UTF-8");
	    } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
	    }
		
	    return json;
	    
	}
	
	@Override
	protected void onPostExecute(String result) {
		
		List<Product> alProduct = new ArrayList<Product>();
    	
    	progDialog.dismiss();
        if (result.length() == 0) {
            activity.alert("JSON file is empty.");
            return;
        }
        
        //Parse JSON product file and load product array list
        try {
			JSONObject allProductObj = new JSONObject(result);
			JSONArray productOjbArray = allProductObj.getJSONArray("productArray");
			for(int ii=0; ii<productOjbArray.length(); ii++) {
				JSONObject productObj = productOjbArray.getJSONObject(ii);
				Product product = new Product();
				product.setId(productObj.getInt("id"));
				product.setName(productObj.getString("name"));
				product.setDescription(productObj.getString("description"));
				product.setRegularPrice(productObj.getString("regularPrice"));
				product.setSalePrice(productObj.getString("salePrice"));
				product.setImageUrl(productObj.getString("productPhoto"));
				
				//Load color array
				JSONArray colorObjArray = productObj.getJSONArray("colors");
				String[] colors = new String[colorObjArray.length()];
				for(int jj=0; jj<colorObjArray.length(); jj++) {										
					JSONObject colorObj = colorObjArray.getJSONObject(jj);
					colors[jj] = colorObj.getString("colorName");
					
				}
				product.setColors(colors);
				
				//Load Store array
				JSONArray storeObjArray = productObj.getJSONArray("stores");
				Map<String, Store> stores = new HashMap<String, Store>();
				for(int kk=0; kk<storeObjArray.length(); kk++) {
					Store store = new Store();			
					JSONObject storeObj = storeObjArray.getJSONObject(kk);
					store.setStoreName(storeObj.getString("name"));
					store.getAddress().setAddr(storeObj.getString("addr"));
					store.getAddress().setCity(storeObj.getString("city"));
					store.getAddress().setState(storeObj.getString("state"));
					store.getAddress().setZip(storeObj.getString("zip"));
					store.getAddress().setPhone(storeObj.getString("tel"));
					
					stores.put(product.getId() + store.getStoreName(), store); //user ID and Store name as key
				}
				product.setStores(stores);
				
				alProduct.add(product);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
        //Set product array and ListView
		activity.setProducts(alProduct);
	}	


}
