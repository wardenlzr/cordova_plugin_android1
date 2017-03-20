package cordova.super_socket.chat.superSocket;
import java.net.Socket;

/**
 * Created by yb on 2017/3/7   16:05.
 * @author 链接服务器回调
 */
public interface ICallBack {
    /**
     * 连接成功事件
     */
    void OnSuccess(Socket client);

    /**
     * 连接失败事件
     */
    void OnFailure(Exception e);

    /**
     * @author 发送接口回调
     *//*
    interface ISendResult {
        void OnSendSuccess();

        void OnSendFailure(Exception e);
    }*/

    /**
     * @author 接收消息回调
     *//*
    interface ISocketPacket {
        void SocketPacket(String msg);
    }*/

}
