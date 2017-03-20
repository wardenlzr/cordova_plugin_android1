package cordova.super_socket.chat.superSocket;

/**
 * Created by yb on 2017/3/7   16:06.
 */

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author 接收消息线程
 */
public class MsgReceiveThread extends Thread implements Runnable {
    private Socket _Client;
    private Handler handler;
    private BufferedReader in;

    public MsgReceiveThread(Socket _Client, Handler handler) {
        this._Client = _Client;
        this.handler = handler;
    }


    @Override
    public void run() {
        super.run();
        try {
            String line = "";
            if (_Client != null) {
                in = new BufferedReader(new InputStreamReader(_Client.getInputStream()));
                while ((line = in.readLine()) != null) {
                    System.out.print("收到服务端消息" + line);
                    Message message = new Message();
                    message.what = SocketClient.RECEIVEMESSAGE;
                    message.obj = line;
                    handler.sendMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
//            if(e instanceof SocketTimeoutException){
//                SocketClient.getInstance().connection();
//            }
        }
    }
}
