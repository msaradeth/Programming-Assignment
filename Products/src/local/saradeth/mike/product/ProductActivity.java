package local.saradeth.mike.product;


import local.saradeth.mike.product.create.CreateProductActivity;
import local.saradeth.mike.product.dao.DatabaseTablesDAO;
import local.saradeth.mike.product.detail.ProductDetailActivity;
import local.saradeth.mike.product.lib.AlertDialogFragment;
import local.saradeth.mike.product.lib.Util;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProductActivity extends Activity implements AlertDialogFragment.AlertDialogListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);

		//Create database and tables if not exist
		DatabaseTablesDAO.create(getApplicationContext(), "keepExistingData");
		//set up action bar
		ActionBar actionBar = Util.setActionBar(getActionBar(), "Products", this);
		//actionBar.setBackgroundDrawable(new ColorDrawable(R.color.vpi__background_holo_dark)); // set color
		
		Button btnShowProduct = (Button) findViewById(R.id.btn_show_product);
		Button btnCreateProduct = (Button) findViewById(R.id.btn_create_product);
		Button btnViewProduct = (Button) findViewById(R.id.btn_view_product_from_file);
		Button btnClearTables = (Button) findViewById(R.id.btn_clear_database_tables);		

		btnShowProduct.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CreateProductActivity.class);
				intent.putExtra("action", "showProduct");
				intent.putExtra("title", "Show Product");
				startActivity(intent);				
			}							
	    });
		
		btnCreateProduct.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CreateProductActivity.class);
				intent.putExtra("action", "createProduct");
				intent.putExtra("title", "Create Product");
				startActivity(intent);
			}							
	    });	
		
		btnViewProduct.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CreateProductActivity.class);				
				intent.putExtra("action", "viewProduct");
				intent.putExtra("title", "View Product");
				startActivity(intent);
			}							
	    });	
		
		btnClearTables.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String title = "Clear Database Tables";
				String message = "Are you sure you want to drop and recreate tables?";
				Util.showDialog(getFragmentManager(), title, message, 1);
			
			}							
	    });			
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onDialogPositiveClick(String tag) {		
		//Drop and recreate tables to clear data
		DatabaseTablesDAO.create(getApplicationContext(), "dropTableIfExist");	
		Util.alert(getApplicationContext(), "Tables are successfully dropped and recreated.");	
	}


	@Override
	public void onDialogNegativeClick(String tag) {
		// TODO Auto-generated method stub
		Util.alert(getApplicationContext(), "Canceled");	
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		/////bug finish();
	}
		
}
