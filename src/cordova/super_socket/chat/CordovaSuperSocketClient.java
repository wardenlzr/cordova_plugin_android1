package cordova.super_socket.chat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.ionicframework.starter1.R;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import cordova.super_socket.chat.superSocket.ISocketPacket;
import cordova.super_socket.chat.superSocket.SocketClient;

/**
 * Created by yb on 2017/3/16 15:02.
 */
public class CordovaSuperSocketClient extends CordovaPlugin {

  private SocketClient socketClient = SocketClient.getInstance();

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    String message = args.getString(0);  //HelloMobileWorld
    if (action.equals("connect")) {   //action=echo
      message = message + "这是原生代码";
      this.connect(message, callbackContext);
      return true;
    } else if(action.equals("sendMsg")){
      message = message + "test";
      this.sendMsg(message,callbackContext);
      return true;
    }else {
      callbackContext.error("这不是一个CordovaSuperSocketClient操作");
      return false;
    }
  }
  //链接
  public void connect(final String message, final CallbackContext callback) {
    if (message != null && message.length() > 0) {
      cordova.getThreadPool().execute(new Runnable() {
        @Override
        public void run() {
          try {
            final String connectionResult = socketClient.connection();
            cordova.getActivity().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                callback.success(message + connectionResult);
//                showNotification(connectionResult);
              }
            });
          } catch (Exception e) {
            callback.error(e.getMessage());
          }
        }
      });
    } else {
      callback.error("Expected one non-empty string argument.");
    }
  }
  private void sendMsg(final String message, final CallbackContext callback) {
    cordova.getThreadPool().execute(new Runnable() {
      @Override
      public void run() {
        if(socketClient != null) {
          socketClient.sendData(message);
        }else {
          callback.error("请连接到服务器...");
        }
      }
    });
  }

  /**
   * 在状态栏显示通知
   */
  private void showNotification(String msg) {
    Context mContext = cordova.getActivity().getApplicationContext();
    NotificationManager notificationManager = (NotificationManager)
      mContext.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
    //通过Notification.Builder来创建通知
    Notification.Builder myBuilder = new Notification.Builder(mContext);
    myBuilder.setContentTitle(msg)
      .setContentText("请您及时获取最新消息")
      .setTicker("您收到新的消息")
      .setSmallIcon(R.mipmap.icon)
      //设置默认声音和震动
      .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
      .setAutoCancel(true)//点击后取消
      .setWhen(System.currentTimeMillis())//设置通知时间
//                .setContentIntent(pendingIntent)
      .setContentIntent(PendingIntent.getActivity(mContext, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT))
      .setPriority(Notification.PRIORITY_HIGH);//高优先级
    Notification notification = myBuilder.build();
    //把Notification传递给NotificationManager
    notificationManager.notify(NOTIFICATION, notification);
    NOTIFICATION++;
  }
  private int NOTIFICATION = 1;
}
