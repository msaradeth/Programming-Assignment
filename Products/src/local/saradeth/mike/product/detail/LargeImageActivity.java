package local.saradeth.mike.product.detail;

import java.io.IOException;
import java.io.InputStream;

import local.saradeth.mike.product.R;
import local.saradeth.mike.product.lib.Util;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;

public class LargeImageActivity extends Activity {
	Bitmap bm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Going full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);   
        
        setContentView(R.layout.large_image);

		Bundle args = getIntent().getExtras();
		String url = args.getString("url");
		
		ImageView imageView = (ImageView)findViewById(R.id.large_image);
		Util.recycleImage(imageView);

    	//load product image from asset folder if available
   		bm = Util.loadImage(this, url);
   		if (bm != null) {   			
   			imageView.setImageBitmap(bm);
   		}else {
   			imageView.setImageResource(R.drawable.ic_launcher);	//Filler Icon
   		}
	}
	
		
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (bm != null)
			bm.recycle();
		
	}
}
