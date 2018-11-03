package cn.fxn.svm.ec.pay;

/**
 * @author:Matthew
 * @date:2018/10/31
 * @email:guocheng0816@163.com
 * @func:
 */
public interface IAliPayResultListener {

    void onPaySuccess();

    void onPaying();

    void onPayFailed();

    void onPayCancel();

    void onPayConnectError();
}
