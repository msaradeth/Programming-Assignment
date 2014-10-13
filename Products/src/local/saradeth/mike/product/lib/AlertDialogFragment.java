package local.saradeth.mike.product.lib;


import local.saradeth.mike.product.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class AlertDialogFragment extends DialogFragment {
    private AlertDialog.Builder builder;
    private String title; 
    private String message;
    private String tag = " ";

	
    public static AlertDialogFragment newInstance(String title, String message) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        frag.setArguments(args);
        
        return frag;
    }	
    
    public static AlertDialogFragment newInstance(String title, String message, String tag) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        args.putString("tag", tag);
        frag.setArguments(args);
        
        return frag;
    }	    
    
    
    public static AlertDialogFragment newInstance(Bundle args) {
        AlertDialogFragment frag = new AlertDialogFragment();
        frag.setArguments(args);
        
        return frag;
    }	
            
       
    
    // Use this instance of the interface to deliver action events
    AlertDialogListener mListener;    
    
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface AlertDialogListener {
        public void onDialogPositiveClick(String tag);
        public void onDialogNegativeClick(String tag);        
    }
    

    
    // Override the Fragment.onAttach() method to instantiate the AlertDialogListener
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        setupCallback();
    }
	    
	
	
	
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        // Use the Builder class for convenient dialog construction
    	if (mListener == null) {
    		setupCallback();
    	}
    	builder = new AlertDialog.Builder(getActivity());
    	
        title = getArguments().getString("title");
        message = getArguments().getString("message");
        tag = getArguments().getString("tag");
        builder.setTitle(title);
        builder.setMessage(message);
        
        builder.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {            	   	      			

	      				// Send the positive button event back to the host activity 
            	  		mListener.onDialogPositiveClick(tag);
               		}
               })
               .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   // Send the negative button event back to the host activity
                       mListener.onDialogNegativeClick(tag);
                   }
               });        
        // Create the AlertDialog object and return it
        return builder.create();
    }

    
  
	public void setupCallback() {
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AlertDialogListener so we can send events to the host
            mListener = (AlertDialogListener) getActivity();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement AlertDialogListener");
        }
	}
	
}