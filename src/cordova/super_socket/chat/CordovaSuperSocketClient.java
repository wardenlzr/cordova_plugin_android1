package cordova.super_socket.chat;

import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;

import cordova.super_socket.chat.superSocket.ICallBack;
import cordova.super_socket.chat.superSocket.ISendResult;
import cordova.super_socket.chat.superSocket.ISocketPacket;
import cordova.super_socket.chat.superSocket.SocketClient;

/**
 * Created by yb on 2017/3/16 15:02.
 */
public class CordovaSuperSocketClient extends CordovaPlugin {
  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    String message = args.getString(0);  //HelloMobileWorld
    if (action.equals("connect")) {   //action=echo
      message = message + "这是原生代码";
      this.connect(message, callbackContext);
      return true;
    } else if(action.equals("test")){
      message = message + "这是原生test";
      this.test(message,callbackContext);
      return true;
    }else {
      callbackContext.error("这不是一个CordovaSuperSocketClient操作");
      return false;
    }
  }

  private void test(String message, final CallbackContext callback) {
    callback.success(message);
  }

  //链接
  public void connect(final String message, final CallbackContext callback) {
    if (message != null && message.length() > 0) {
//      callback.success(message);
      final SocketClient socketClient = SocketClient.getInstance();
      cordova.getThreadPool().execute(new Runnable() {
        @Override
        public void run() {
          try {
            final String connectionResult = socketClient.connection();
            cordova.getActivity().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                callback.success(message + connectionResult);
                Toast.makeText(cordova.getActivity(), message + connectionResult, Toast.LENGTH_SHORT).show();
              }
            });
          } catch (Exception e) {
//            callback.error(e.getMessage());
          }
        }
      });
    } else {
      callback.error("Expected one non-empty string argument.");
    }
  }
  //发送
//              String s = socketClient.sendData("LOGIN 1 2\r\n");


  /*//设置连接服务器监听
        socketClient.setOnConnectListener(new ICallBack() {
    @Override
    public void OnSuccess(Socket client1) {
      callback.success("ICallBack--OnSuccess-" + client1.toString());
    }

    @Override
    public void OnFailure(Exception e) {
      callback.error("ICallBack--OnFailure-" + e.getMessage());
    }
  });*/
}
