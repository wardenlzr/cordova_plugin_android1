package cordova.super_socket.chat.superSocket;

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
  private ICallBack callBack = null;
//  private ISocketPacket _packet = null;
  private ISendResult _Result = null;

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

  /**
   * @param callBack 设置连接服务器监听
   */
  public void setOnConnectListener(ICallBack callBack) {
    this.callBack = callBack;
  }

  protected static final int CONNECTSUCCESS = 1;//连接成功
  protected static final int CONNECTFAILURE = -1;//连接失败
  protected static final int RECEIVEMESSAGE = 2;//接收消息
  protected static final int SENDMESSAGE = 3;//发送消息
  protected static final int SENDSUCCESS = 4;//发送成功
  protected static final int SENDFAILURE = -2;//发送失败

//  private static class MyHandler extends Handler {
//    private final WeakReference<SocketClient> mActivity;
//
//    public MyHandler(SocketClient socketClient) {
//      mActivity = new WeakReference<SocketClient>(socketClient);
//    }
//
//    @Override
//    public void handleMessage(Message msg) {
//      System.out.println(msg);
//      if (mActivity.get() == null) {
//        return;
//      }
//      mActivity.get().initSocketListener(msg);
//    }
//  }

//  public void initSocketListener(Message msg) {
//    if (client != null && client.isConnected()) {
//      if (msg.what == CONNECTSUCCESS) {
//        new MsgReceiveThread(client, handler).start();
//      } else if (msg.what == CONNECTFAILURE) {
//        Exception e1 = (Exception) msg.obj;
//        if (callBack != null) {
//          callBack.OnFailure(e1);
//        }
//      } else if (msg.what == RECEIVEMESSAGE) {
//        if (_packet != null) {
//          _packet.SocketPacket(msg.obj.toString());
//        }
//      } else if (msg.what == SENDMESSAGE) {
//        new SendMsgThread(client, handler, msg.obj).start();
//      } else if (msg.what == SENDSUCCESS) {
//        if (_Result != null) {
//          _Result.OnSendSuccess();
//        }
//      } else if (msg.what == SENDFAILURE) {
//        if (_Result != null) {
//          Exception e = (Exception) msg.obj;
//          _Result.OnSendFailure(e);
//        }
//      }
//    }
//  }

  /**
   * @param _packet 设置接受消息监听
   */
 /* public void setOnReceiveListener(ISocketPacket _packet) {
    this._packet = _packet;
  }*/

  private BufferedReader in;

  /**
   * 打开连接
   */
  public String connection() {
    String line = "";
    try {
      client = new Socket(host, port);
      client.setSoTimeout(timeout * 3000);
      if (callBack != null) {
        callBack.OnSuccess(client);
      }
      Log.d("client", "Socket链接成功");
      if (client != null) {
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        line = in.readLine();
       /* while ((line = in.readLine()) != null) {
          return line;
        }*/
      }
    } catch (Exception e1) {
      e1.printStackTrace();
      Log.d("client", "连接失败");
      line = e1.getMessage();
    }
    return line;
  }

  private OutputStream serverOutput;

  /**
   * 发送数据（包括登录等）
   *
   * @param msg 对象
   */
  public String sendData(Object msg) {
    String result = "";
    try {
      byte[] data = toByteArray(msg);
      serverOutput.write(data);
      serverOutput.flush();

      if (client != null) {
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        while ((result = in.readLine()) != null) {
          System.out.print("收到服务端消息" + result);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
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
