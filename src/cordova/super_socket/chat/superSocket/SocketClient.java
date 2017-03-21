package cordova.super_socket.chat.superSocket;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ionicframework.starter1.R;

import org.apache.cordova.CallbackContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by yb on 2017/3/7   16:09.
 * android Socket客户端封装
 */
public class SocketClient {
  private Socket client;
  private static SocketClient instance;
  private static final String host = "10.32.2.77";
  private static final int port = 2000;
  private int timeout = 6;
  private BufferedReader in;

  private SocketClient() {
  }

  /**
   */
  public static SocketClient getInstance() {
    if (instance == null) {
      synchronized (SocketClient.class) {
        if (instance == null) {
          instance = new SocketClient();
        }
      }
    }
    return instance;
  }

  public static final int CONNECTSUCCESS = 1;//连接成功
  public static final int CONNECTFAILURE = -1;//连接失败
  public static final int RECEIVEMESSAGE = 2;//接收消息
  public static final int SENDMESSAGE = 3;//发送消息
  public static final int SENDSUCCESS = 4;//发送成功
  public static final int SENDFAILURE = -2;//发送失败

  public void getReceivemessage(final Activity activity, final CallbackContext callback){
    String line = "";
    if (client != null) {
      try {
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        while((line = in.readLine()) != null){
          callback.success(line);
          activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              Toast.makeText(activity.getApplicationContext(), "getReceivemessage...", Toast.LENGTH_SHORT).show();
            }
          });
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }
  /**
   * 打开连接
   */
  public void connection(Activity activity, final CallbackContext callback) {
    String line = "";
    try {
      client = new Socket(host, port);
      client.setSoTimeout(timeout * 3000);
      if (client != null) {
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        while((line = in.readLine()) != null){
          callback.success(line);
          showNotification(line,activity);
        }
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }
/*
  public String connection() {
    String line = "";
    try {
      client = new Socket(host, port);
      client.setSoTimeout(timeout * 3000);

      if (client != null) {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        line = in.readLine();
      }
    } catch (Exception e1) {
      e1.printStackTrace();
      line = e1.getMessage();
    }
    return line;
  }
*/
  /**
   * 在状态栏显示通知
   */
  private void showNotification(final String msg, final Activity mContext) {
    mContext.runOnUiThread(new Runnable() {
      @Override
      public void run() {
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
      }
    });
    NOTIFICATION++;
  }
  private int NOTIFICATION = 1;
  /**
   * 发送数据（包括登录等）
   *
   * @param msg 对象
   */
  public void sendData(Object msg) {
    try {
      byte[] data = toByteArray(msg);
      if (client != null) {
        OutputStream serverOutput = client.getOutputStream();
        serverOutput.write(data);
        serverOutput.flush();

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @param obj 转换byte
   * @return
   */
  public byte[] toByteArray(Object obj) {
    try {
      return obj.toString().getBytes("utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    }
  }
}
