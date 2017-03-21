package cordova.super_socket.chat.superSocket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
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



  /**
   * 打开连接
   */
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
