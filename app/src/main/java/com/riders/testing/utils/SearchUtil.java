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
	
	public static final String contrustURL( String ... szArgs ) {
		String szURL = "";
		
		for( String szArg : szArgs ) {
			szURL = szURL + szArg;
		}
		
		return szURL;
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

}