package org.apache.cordova.echo;

import  org.apache.cordova.CallbackContext;
import  org.apache.cordova.CordovaPlugin;
import  org.apache.cordova.PluginResult;
import  org.json.JSONArray;
import  org.json.JSONException;
import  org.json.JSONObject;
/**
 * Created by yb on 2017/3/16 15:02.
 */
public class Echo extends CordovaPlugin {
    @Override
    public boolean execute(String action,  JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("echo"))  {   //action=echo
            String message = args.getString(0);  //HelloMobileWorld
            message=message+"这是原生代码";
            this.echo(message,  callbackContext);
            return true;
        }else{
            callbackContext.error("这不是一个echo操作");
            return  false;
        }
    }
    private void echo(String message,  CallbackContext callbackContext) {
        if (message != null &&  message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string  argument.");
        }
    }
}