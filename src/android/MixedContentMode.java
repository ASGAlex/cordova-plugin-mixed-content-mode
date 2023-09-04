/*
 * MIT License
 *
 * Copyright 2023 Carlos Bello A.K.A: CarlosBet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.carlosbet.cordova.mixedcontentmode;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;

public class MixedContentMode extends CordovaPlugin {
    private static final String LOG_TAG = "MixedContentMode";
    
    private HashMap<String, Integer> modes = new HashMap<String, Integer>(
        Map.of(
            "neverallow", WebSettings.MIXED_CONTENT_NEVER_ALLOW,
            "compatibilitymode", WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE,
            "alwaysallow", WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    ));

    private WebView androidWebView;
    private WebSettings wSettings;

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    @Override
    public void initialize(final CordovaInterface cordova, CordovaWebView cordovaWebView) {
        LOG.d(LOG_TAG, "Initialization");
        super.initialize(cordova, cordovaWebView);

        androidWebView = (WebView)cordovaWebView.getView();
        wSettings = androidWebView.getSettings(); 

        // Read 'MixedContentMode' from config.xml
        setMixedContentMode(preferences.getString("MixedContentMode", "compatibilitymode"));
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false otherwise.
     */
    @Override
    public boolean execute(final String action, final CordovaArgs args, final CallbackContext callbackContext) {
        /*
    	 * Don't run any of these if the current activity is finishing
    	 * in order to avoid android.view.WindowManager$BadTokenException
    	 * crashing the app. Just return true here since false should only
    	 * be returned in the event of an invalid action.
    	 */
    	if (this.cordova.getActivity().isFinishing()) return true;
        
        LOG.d(LOG_TAG, "Executing action set mixed content mode: " + action);
        return setMixedContentMode(action);
    }

    private boolean setMixedContentMode(final String mode) {        
        final Integer intMode = modes.get(mode.toLowerCase());
        if(intMode == null) {
            LOG.e(LOG_TAG, mode + " not recogized.\nAllowed values for this plugin are: NeverAllow, CompatibilityMode or AlwaysAllow");
            return false;
        }
        
        if (Build.VERSION.SDK_INT >= 21) {
            wSettings.setMixedContentMode(intMode);
            LOG.d(LOG_TAG, "Now mixed content mode is " + wSettings.getMixedContentMode() + ": " + mode);
        } 
        else
            LOG.d(LOG_TAG, "This plugin has no effect in builds previous 21 - Android 5.0 (Lollipop)");
        
        return true;
    }
}