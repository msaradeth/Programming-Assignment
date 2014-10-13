package local.saradeth.mike.product.lib;


import local.saradeth.mike.product.create.ViewHolder;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;


public class ImageCache {	
	private LruCache<String, Bitmap> mMemoryCache;
	
    private static float cacheSizePercent = (float) 0.2;	//set cache size to 20% of available memory 0.2 percent of 100%    
    //Default memory cache size in kilobytes
    private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 5; // 5MB
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    
    private Activity activity;
        
    //constructor
    public ImageCache (){    	
    	mMemoryCache = new LruCache<String, Bitmap>(getCacheSize()) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }
    
    
    public void drawImage (Activity activity, ViewHolder holder) {
        this.activity = activity;        
        if (mMemoryCache.get(holder.url) != null) {        	
        	holder.imageView.setImageBitmap(mMemoryCache.get(holder.url));
        	//Log.d("drawImage", "load from MemCache " + holder.url);
        }
        else {
            new ImageTask().execute(holder);
            Log.d("drawImage", "Load bitmap from file " + holder.url);
        }
        //Log.d("mMemoryCache.size", "mMemoryCache.size() = " + mMemoryCache.size());
    }
    
    
    public void drawImage (Activity activity, String url, ImageView imageView) {
        this.activity = activity;
        
        if (mMemoryCache.get(url) != null) {        	
        	imageView.setImageBitmap(mMemoryCache.get(url));
        	//Log.d("drawImage", "ProductDetailActivity ==load from MemCache " + url);
        }
        else {
        	ViewHolder holder = new ViewHolder();
        	holder.url = url;
        	holder.imageView = imageView;
            new ImageTask().execute(holder);
            //Log.d("drawImage", "ProductDetailActivity Load bitmap from file " + url);
        }
        //Log.d("mMemoryCache.size", "mMemoryCache.size() = " + mMemoryCache.size());
    }
        

    public static int getCacheSize() {
    	int memCacheSize = 0;
    	
        if (cacheSizePercent < 0.01f || cacheSizePercent > 0.8f) {
            throw new IllegalArgumentException("setMemCacheSizePercent - percent must be "
                    + "between 0.01 and 0.8 (inclusive)");
        }
        memCacheSize = Math.round(cacheSizePercent * Runtime.getRuntime().maxMemory() / 1024);
    	
    	return memCacheSize;
        
    }
    
    
    public void clearCache() {
    	mMemoryCache.evictAll();
    }
    
    
    private class ImageTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

		@Override
		protected ViewHolder doInBackground(ViewHolder... params) {
			//load image directly
			ViewHolder holder = params[0];
			holder.bitMap =  Util.loadImage(activity, holder.url);
			
			//Resize image 
		  	int width=200;
		  	int height=200;		
		  	if (holder.bitMap!= null) {
		  		holder.bitMap = Bitmap.createScaledBitmap(holder.bitMap, width, height, true);
		  	}
			
			return holder;
		}
		
		@Override
		protected void onPostExecute(ViewHolder result) { 
			super.onPostExecute(result);
			synchronized (this) {
				if (result.bitMap != null){
					result.imageView.setImageBitmap(result.bitMap);					
					mMemoryCache.put(result.url, result.bitMap);
				}else {
					result.imageView.setImageBitmap(result.bitMap);	
				}				
			}		
		}		        
    }
    
	
    public LruCache<String, Bitmap> getmMemoryCache() {
		return mMemoryCache;
	}


	public void setmMemoryCache(LruCache<String, Bitmap> mMemoryCache) {
		this.mMemoryCache = mMemoryCache;
	}
	
}
