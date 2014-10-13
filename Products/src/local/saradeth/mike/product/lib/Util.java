package local.saradeth.mike.product.lib;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import local.saradeth.mike.product.R;
import local.saradeth.mike.product.core.Globals;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Util extends Activity {
	//private static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);				
	}//onCreate()

	

	public static synchronized void getDirections(Context context, String saddr, String daddr) {
		
		//Allow user to select the Application to use to get directions			
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
				Uri.parse("http://maps.google.com/maps?saddr=" + saddr + "&daddr=" + daddr));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	
	public static synchronized void makePhoneCall(Context context, String phoneNumber) {		
		try {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)); 
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
			context.startActivity(intent);	
		} catch (ActivityNotFoundException e) {
			Log.i("ACTION_CALL", "Error in your phone call\n " + e.getMessage());
		}	    	    	    
	}
	

	
	public static synchronized Location getBestLocation(Context ctxt) {
        Location gpslocation = getLocationByProvider(LocationManager.GPS_PROVIDER, ctxt);
        Location networkLocation = getLocationByProvider(LocationManager.NETWORK_PROVIDER, ctxt);
        Location fetchedlocation = null;

        if (gpslocation!= null && networkLocation!= null) {
        	if (gpslocation.getTime() >= networkLocation.getTime()) {
        		fetchedlocation = gpslocation;
        	}else {
        		fetchedlocation = networkLocation;
        	}
        }else {
            //if we have only one location available, the choice is easy
            if (gpslocation!= null) {
                Log.i("New Location Receiver", "GPS Location available.");
                fetchedlocation = gpslocation;
            } else {
                Log.i("New Location Receiver", "No GPS Location available. Fetching Network location lat=" + networkLocation.getLatitude() +" lon ="+networkLocation.getLongitude() );
                fetchedlocation = networkLocation;
            }        	
        }

        return fetchedlocation;


    }


 

	public static synchronized String getDeviceInfo(Context context) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = null;
        String addr = "";

        Location location = Util.getBestLocation(context); 
        
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            addr = address.getAddressLine(0) + "\n"
        		+ address.getAddressLine(1);

        }
        return addr;
	}  	
	    
    

	   public static synchronized String getDeviceAddr(Location location, Context context) {
	        Geocoder geocoder = new Geocoder(context);
	        List<Address> addresses = null;
	        String addr = "";

	   
	        try {
	            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        if (addresses != null && addresses.size() > 0) {
	            Address address = addresses.get(0);
	            addr = address.getAddressLine(0) + "\n"
            		+ address.getAddressLine(1)+ "\n"
         			//+ address.getAddressLine(2)
         			;	

	        }
	        return addr;
	   }  	
	    	
	   
	   public static synchronized String getLocAddr(Location location, Context context) {
	        Geocoder geocoder = new Geocoder(context);
	        List<Address> addresses = null;
	        String addr = "";

	   
	        try {
	            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        if (addresses != null && addresses.size() > 0) {
	            Address address = addresses.get(0);
	            addr = address.getAddressLine(0) + "\n"
	            	+ address.getAddressLine(1)+ "\n"
        			//+ address.getAddressLine(2)
	            	;	

	        }
	        return addr;
	   }  	
	    	   
      
		
		public static synchronized Location getGpsLocation(Context ctxt) {
	        Location gpslocation = getLocationByProvider(LocationManager.GPS_PROVIDER, ctxt);
	        return gpslocation;
	    }

		
		public static synchronized Location getNetworkProviderLocation(Context ctxt) {	        
	        Location networkLocation = getLocationByProvider(LocationManager.NETWORK_PROVIDER, ctxt);
	        return networkLocation;
	    }

       
		
	    /**
	     * get the last known location from a specific provider (network/gps)
	     */
		public static synchronized Location getLocationByProvider(String provider,Context ctxt) {
	        Location location = null;
	        LocationManager locationManager = (LocationManager) ctxt.getSystemService(Context.LOCATION_SERVICE);

	        try {
	            if (locationManager.isProviderEnabled(provider)) {
	                location = locationManager.getLastKnownLocation(provider);
	            }
	        } catch (IllegalArgumentException e) {
	            Log.i("New Location Receiver", "Cannot acces Provider " + provider);
	        }
	        return location;
	    }   

       		
	    /**
	     * get the last known location from a specific provider (network/gps)
	     */
	    public static synchronized Location getLocationByProvider2(String provider, Context ctxt) {
	        Location location = null;
	        LocationManager locationManager = (LocationManager) ctxt.getSystemService(Context.LOCATION_SERVICE);
	        //locationManager.requestSingleUpdate(provider, intent)

	        try {
	            if (locationManager.isProviderEnabled(provider)) {
	                location = locationManager.getLastKnownLocation(provider);
	            }
	        } catch (IllegalArgumentException e) {
	            Log.i("New Location Receiver", "Cannot acces Provider " + provider);
	        }
	        return location;
	    }   
	    
		
	    public static synchronized String maxLen(String text, int len) {
	    	
		    if (len < 1) return text;
		    
		    if (text.length() > len ) {
		    	text = text.substring(0, len-1);
		    } 
		    
	        return text.trim();
	    }   
	    
	    public static synchronized void showDialog(FragmentManager fragmentManager, String title, String message, int tag) {
			DialogFragment dialogFrag = (DialogFragment) AlertDialogFragment.newInstance(title, message);
		    dialogFrag.show(fragmentManager, String.valueOf(tag));    	
	    }	    
	    
	    
	    public static synchronized void showDialog(FragmentManager fm, String title, String message, String tag) {
			DialogFragment dialogFrag = (DialogFragment) AlertDialogFragment.newInstance(title, message, tag);
		    dialogFrag.show(fm, String.valueOf(tag));    	
	    }	    
	 	
	    public static synchronized void showDialog(FragmentManager fm, Bundle args) {	    	
	    	DialogFragment dialogFrag = (DialogFragment) AlertDialogFragment.newInstance(args);
		    dialogFrag.show(fm, String.valueOf(args.getInt("tag")));    
		    
	        //MyDialogFragment dialog = MyDialogFragment.newInstance();
	        //dialog.show(getActivity().getFragmentManager(), "MyDialogFragment");			    
	    }	 
	    
    

	    public static synchronized String getFirstOfNextMonth() {		    
		    String today = Util.getCurrentDate();	
		    
		    int mm = Integer.parseInt(today.substring(0, 2));
		    int yy = Integer.parseInt(today.substring(6, 10));
		    
		    Log.i("mm", "mm=" + mm);
		    Log.i("yy", "yy=" + yy);
		    
		    if (mm == 12) {
		    	mm = 1;
		    	yy = yy + 1;		    	
		    }else {
		    	mm = mm + 1;
		    }		   
		    String firstOfNextMonth = String.format("%02d", mm) + "/01/" + String.valueOf(yy)
		    		;		    		
		    return firstOfNextMonth;
	    }
	    
	    
	    public static synchronized String getCurrentDate() {	    	
		    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    String today = sdf.format(new Date());		    
		    return today;
	    }	    

	    
	    public static synchronized boolean fcExist(Context context, String fc_id) {
	    	
	    	String query = "select count(*) from facility where fc_id = " + fc_id ;
	    	
	        SQLiteDatabase sqliteDataBase = DatabaseHelper.getDatabase(context, Globals.dbName);
		    Cursor cursor = sqliteDataBase.rawQuery(query, null);
		    int count = 0;	    	   
        	while (cursor.moveToNext()){
        		count = cursor.getInt(0);
            }          		  
        	cursor.close();
        	sqliteDataBase.close();
        	
    		if (count == 0 ) {
    			return false;	//facility not exist
    		}else {
    			return true;	//facility exist
    		}
	    }
	    
	    
	    public static synchronized boolean validFacility(Context context, String fc_id) {
	    	
	    	boolean validFac = false;
	    	
	    	if (!fc_id.isEmpty()) {	
	    		if (Util.fcExist(context, fc_id)) {
	    			validFac = true;
	    		}		    		
	    	}  
        	
	    	return validFac;
	    }	
	    
	 
	    
	    public static Boolean isTablet(Context context) {

	        if ((context.getResources().getConfiguration().screenLayout & 
	                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {

	          return true;
	      }
	        return false;
	    }	 
	    
	    
	    
/*	    xlarge screens are at least 960dp x 720dp
	    large screens are at least 640dp x 480dp
	    normal screens are at least 470dp x 320dp
	    small screens are at least 426dp x 320dp
	    */
	    public static void displayScreenSize(Activity activity) {
		    int screenSize = activity.getResources().getConfiguration().screenLayout &Configuration.SCREENLAYOUT_SIZE_MASK;
		    switch(screenSize) {
	        	case Configuration.SCREENLAYOUT_SIZE_XLARGE:
	        		Toast.makeText(activity, "XLarge screen",Toast.LENGTH_LONG).show();
		           break;		    
		        case Configuration.SCREENLAYOUT_SIZE_LARGE:
		        	Toast.makeText(activity, "Large screen",Toast.LENGTH_LONG).show();
		        	break;
		         case Configuration.SCREENLAYOUT_SIZE_NORMAL:
		            Toast.makeText(activity, "Normal screen",Toast.LENGTH_LONG).show();
		             break;
		         case Configuration.SCREENLAYOUT_SIZE_SMALL:
		             Toast.makeText(activity, "Small screen",Toast.LENGTH_LONG).show();
		             break;
		         default:
		             Toast.makeText(activity, "Screen size is neither Xlarge, large, normal or small" , Toast.LENGTH_LONG).show();
		    }
	    }	
	    
	    
	    
/*	    A set of six generalized densities:
	    	ldpi (low) ~120dpi
	    	mdpi (medium) ~160dpi
	    	hdpi (high) ~240dpi
	    	xhdpi (extra-high) ~320dpi
	    	xxhdpi (extra-extra-high) ~480dpi
	    	xxxhdpi (extra-extra-extra-high) ~640dpi
	    	*/
	    public static void displayScreenDensity(Activity activity) {
	    	
	    	int density= activity.getResources().getDisplayMetrics().densityDpi;
	    	   switch(density)
	    	  {
	    	  case DisplayMetrics.DENSITY_LOW:
	    	     Toast.makeText(activity, "LDPI", Toast.LENGTH_SHORT).show();
	    	      break;
	    	  case DisplayMetrics.DENSITY_MEDIUM:
	    	       Toast.makeText(activity, "MDPI", Toast.LENGTH_SHORT).show();
	    	      break;
	    	  case DisplayMetrics.DENSITY_HIGH:
	    	      Toast.makeText(activity, "HDPI", Toast.LENGTH_SHORT).show();
	    	      break;
	    	  case DisplayMetrics.DENSITY_XHIGH:
	    	       Toast.makeText(activity, "XHDPI", Toast.LENGTH_SHORT).show();
	    	      break;
	    	  case DisplayMetrics.DENSITY_XXHIGH:
	    	       Toast.makeText(activity, "XXHDPI", Toast.LENGTH_SHORT).show();
	    	      break;	    	      
	    	  default:
	       	   Toast.makeText(activity, "Density is neither XXHDPI, XHDPI, HDPI, MDPI, OR LDPI.  Density is " + density + " DPI", Toast.LENGTH_SHORT).show();	    	      
	    	  }
	    }
	    
	    
	    public static void displayScreenDensity2(Activity activity) {
	    	 // Determine density
	    	 DisplayMetrics metrics = new DisplayMetrics();
	    	 activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    	 int density = metrics.densityDpi;

	    	 if (density == DisplayMetrics.DENSITY_HIGH) {
	    	Toast.makeText(activity,
	    	        "DENSITY_HIGH... Density is " + String.valueOf(density),
	    	        Toast.LENGTH_LONG).show();
	    	 } else if (density == DisplayMetrics.DENSITY_MEDIUM) {
	    	Toast.makeText(activity,
	    	        "DENSITY_MEDIUM... Density is " + String.valueOf(density),
	    	        Toast.LENGTH_LONG).show();
	    	 } else if (density == DisplayMetrics.DENSITY_LOW) {
	    	Toast.makeText(activity,
	    	        "DENSITY_LOW... Density is " + String.valueOf(density),
	    	        Toast.LENGTH_LONG).show();
	    	 } else {
	    	Toast.makeText(
	    			activity,
	    	        "Density is neither HIGH, MEDIUM OR LOW.  Density is "
	    	                + String.valueOf(density), Toast.LENGTH_LONG)
	    	        .show();
	    	 }
	    }
	    
	    public static void displayUserLanguage(Activity activity) {
	    	String language = Locale.getDefault().getDisplayLanguage();
	    	 Toast.makeText(activity, "User language:  " + language, Toast.LENGTH_SHORT).show();	    	      
	    }
	    
	    
		
	    public synchronized static int getLastInsertRowId(SQLiteDatabase db, String tableName) {
	    	int lastInsertRowId = 0;
	    	
	        String sql = "select last_insert_rowid() from "+ tableName;
	        Cursor cur = db.rawQuery(sql, null);
	        cur.moveToFirst();
	        lastInsertRowId = cur.getInt(0);
	        cur.close();
	        
	    	return lastInsertRowId;
	    }
	    	    
	    
	    public synchronized static boolean recordExist(SQLiteDatabase db, int id) {
	    	int recordCount = 0;
	    	
	        String sql = "select name from product where id = " + id;
	        Cursor cursor = db.rawQuery(sql, null);
	        while (cursor.moveToNext()){
	        	recordCount = recordCount + 1;
	        	String name = cursor.getString(0);
	        	//Log.d("recordExist", "recordCount=" + recordCount + "    id = " + id + "   name = " + name);
	        	
	        }
	        cursor.close();
	        
	        //Log.d("recordCount", "Total recordCount = " + recordCount);
	        //Log.d("recordExist", sql);
	        
	        if (recordCount == 0) {	        	
	        	return false;	//record not exist
	        }else {
	        	return true;	//record exist in table	        	
	        }
	        				
	    }
	    
		
		//Toast a given message
	    public synchronized static void alert(Context context, String msg){
	        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	    }	    
	    
		//Toast a given message
	    public static String money(String money){
	    	if (money.isEmpty()) {
	    		return " ";
	    	}else {
	    		return "$" + money;
	    	}
	        
	    }	
	    
		
		public static ActionBar setActionBar(ActionBar actionBar, String title, Activity activity) {

			//actionBar = getActionBar();
	        
	        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
	        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD); 
	        
	        // Mike add the custom view to the action bar
	        actionBar.setCustomView(R.layout.actionbar_view);                
	        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);    
	        actionBar.setDisplayShowTitleEnabled(false);
	        
	        TextView tvTitle = (TextView)activity.findViewById(R.id.action_bar_title);
	        tvTitle.setText(title);	        
	        
	        //Collapses the home icon completely.        
	    	View homeIcon = activity.findViewById(android.R.id.home);
	    	((View) homeIcon.getParent()).setVisibility(View.GONE);
	    	
	    	if (!title.equalsIgnoreCase(Globals.product_detail_screen)) {
	    		//Collapse views not needed	    		
	    		LinearLayout rtDrawer = (LinearLayout)activity.findViewById(R.id.image_right_drawer_layout);
	    		LinearLayout rtDrawerDivier = (LinearLayout)activity.findViewById(R.id.image_right_drawer_layout_divider);
	    		rtDrawer.setVisibility(View.GONE);
	    		rtDrawerDivier.setVisibility(View.GONE);	    		
	    	}
	    	
	    	return actionBar;
		}	    
		
		
		public synchronized static void recycleImage(Drawable drawable) {
		    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		    Bitmap bitmap = bitmapDrawable.getBitmap();
		    bitmap.recycle();								
		}
		
		
		public synchronized static void recycleImage(ImageView imageView) {
			Drawable drawable = imageView.getDrawable();
			if (drawable instanceof BitmapDrawable) {
			    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			    Bitmap bitmap = bitmapDrawable.getBitmap();
			    bitmap.recycle();

			}					
		}		
		
		
		//Load image from asset folder and save it in memory
		public synchronized static Bitmap loadImage(Activity activity, String url) {
			Bitmap bitMap = null;
			
	   		if (url != null) {
	   	        try {
	   	            // get input stream
	   	        	InputStream is = activity.getAssets().open(url);
	   	        	bitMap = BitmapFactory.decodeStream(is); 		 
	   	         	is.close();
	   	        }
	   	        catch(IOException e) {
	   	            Log.d("ImageLoadAndCache", "I/O : " + e.getMessage());     
	   	        }    			  			
	   		} 
	   		
	   		return bitMap;
		}
		
		
		//Load images from fragment if exist on Orientation change
		public synchronized static ImageCache loadImagesOnOrientionChange(Activity activity) {		
			ImageCache imageCache = new ImageCache();
			
			RetainFragment retainFragment = 
			            RetainFragment.findOrCreateRetainFragment(activity.getFragmentManager());
			
			if (retainFragment.mRetainedCache == null) {
		    	imageCache = new ImageCache();
		        retainFragment.mRetainedCache = imageCache.getmMemoryCache();			
			}else {
				imageCache.setmMemoryCache(retainFragment.mRetainedCache);
			}
			
/*			imageCache.setmMemoryCache(retainFragment.mRetainedCache);
		    if (imageCache == null) {
		    	imageCache = new ImageCache();
		        retainFragment.mRetainedCache = imageCache.getmMemoryCache();
		    }
		*/
		    return imageCache;
		}
		
}