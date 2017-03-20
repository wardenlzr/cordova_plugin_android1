package cordova.super_socket.chat.superSocket;

/**
 * Created by yb on 2017/3/13 15:50.
 */

public interface ISendResult {
    void OnSendSuccess();

    void OnSendFailure(Exception e);
}
