package com.riders.testing.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class SearchUtil {
	
	private static final String TAG = SearchUtil.class.getSimpleName();
	
	public static final String contrustURL( String ... szArgs )
	{
		String szURL = "";
		
		for( String szArg : szArgs )
		{
			szURL = szURL + szArg;
		}
		
		return szURL;
	}
	
	public static void showToast(Context context, String message){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	
	/**
	 * Hide the keyboard
	 * @param view
	 */
	public static void hideKeyboard(Context context, View view){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	
	
	/**
     * Show an alertDialog
     * @param
     * @param title
     * @param message
     * @param negativeMessage
     * @param positiveMessage
     */
    public static void showAlertDialog(final Activity activity, final Context context, String title, String message, final String negativeMessage, final String positiveMessage){
    	Log.i("Activity - AlertDialog", "Show alert dialog");
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);
        alertDialog.setNegativeButton(negativeMessage, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				SearchUtil.showToast(context, negativeMessage);
				if(negativeMessage.equalsIgnoreCase("Réessayer")){
					//launchActivity(context, MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED|Intent.FLAG_ACTIVITY_NEW_TASK, null, null);
				}
				if ( negativeMessage.equalsIgnoreCase("Réessayer") && SearchUtil.isConnected(context) ){
					dialog.dismiss();
				}
			}
		});
        alertDialog.setPositiveButton(positiveMessage, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				SearchUtil.showToast(context, positiveMessage);
				if (activity != null)
					activity.onBackPressed();				
				if (negativeMessage.equalsIgnoreCase("Quitter")){
					activity.finish();
				}
									
			}
		});
        
        alertDialog.setCancelable(false);
        
        // Showing Alert Message
        alertDialog.show();
    }
    
    
    /**
	 * Launch a new activity with bundle or not
	 * @param context
	 * @param aimedClass
	 * @param bundleKey
	 * @param bundleValue
	 */
	public static void launchActivity(Context context, Class<? extends Activity> aimedClass, int intentFlags, Object bundleKey, Object bundleValue) throws RuntimeException{
    	
    	Log.i(TAG, "in launchActivity");
    	
    	if( bundleKey == null && bundleValue ==null ){
    		Log.d(TAG, "launchActivity - bundle is null");
    		context.startActivity(new Intent(context, aimedClass));
    	}
    	if( intentFlags != 0 ){
    		Intent intent = new Intent(context, aimedClass);
    		intent.setFlags(intentFlags);
    		
    		context.startActivity(intent);
    	}
    	if( bundleKey != null && bundleValue !=null ){
    		Log.d(TAG, "launchActivity - bundle is not null");
    		
    		Intent intent = new Intent(context, aimedClass);
    		intent.putExtra((String)bundleKey, (String)bundleValue);
    		
    		context.startActivity(intent);
    	}
    	if( intentFlags != 0 && bundleKey != null && bundleValue !=null ){
    		Log.d(TAG, "launchActivity - flags & bundle are not null");
    		
    		Intent intent = new Intent(context, aimedClass);
    		intent.setFlags(intentFlags);
    		intent.putExtra((String)bundleKey, (String)bundleValue);
    		
    		context.startActivity(intent);
    	}
    }
	
	
	/**
	 * Check the Internet connection
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;    
    }
}