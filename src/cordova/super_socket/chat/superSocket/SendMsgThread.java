package cordova.super_socket.chat.superSocket;

import android.os.Handler;
import android.os.Message;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by yb on 2017/3/7   16:08.
 * @author 发送消息线程
 */
public class SendMsgThread extends Thread implements Runnable {
    private Handler handler;
    private OutputStream serverOutput;
    private Object msg = null;

    public SendMsgThread(Socket Client, Handler handler, Object msg) {
        try {
            serverOutput = Client.getOutputStream();
            this.msg = msg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.handler = handler;
    }


    @Override
    public void run() {
        super.run();
        Message message = new Message();
        try {
            byte[] data = toByteArray(msg);
            serverOutput.write(data);
            serverOutput.flush();
            message.what = SocketClient.SENDSUCCESS;
        } catch (Exception ste) {
            message.what = SocketClient.SENDFAILURE;
            message.obj = ste;
        }
        handler.sendMessage(message);
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
