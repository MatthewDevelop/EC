package cn.fxn.svm.ec.pay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;

import cn.fxn.svm.core.ui.loader.EcLoader;

/**
 * @author:Matthew
 * @date:2018/10/31
 * @email:guocheng0816@163.com
 * @func:
 */
public class PayAsyncTask extends AsyncTask<String, Void, String> {

    //订单支付成功
    public static final String AL_PAY_STATUS_SUCCESS = "9000";
    //订单正在支付
    public static final String AL_PAY_STATUS_PAYING = "8000";
    //订单支付失败
    public static final String AL_PAY_STATUS_FAIL = "4000";
    //订单取消
    public static final String AL_PAY_STATUS_CANCEL = "6001";
    //网络错误
    public static final String AL_PAY_STATUS_CONNECT_ERROR = "6002";

    private final Activity ACTIVITY;
    private final IAliPayResultListener LISTENER;

    public PayAsyncTask(Activity activity, IAliPayResultListener listener) {
        ACTIVITY = activity;
        LISTENER = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        final String aliPaySign = params[0];
        final PayTask payTask = new PayTask(ACTIVITY);
        return payTask.pay(aliPaySign, true);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        EcLoader.stopLoading();
        final PayResult payResult = new PayResult(result);
        //支付宝返回此次支付结果及加签，建议对支付宝签名信息那签约时支付宝提供的公钥做验签
        final String resultInfo = payResult.getResult();
        final String resultStatus = payResult.getResultStatus();

        switch (resultStatus) {
            case AL_PAY_STATUS_SUCCESS:
                if (LISTENER != null) {
                    LISTENER.onPaySuccess();
                }
                break;
            case AL_PAY_STATUS_PAYING:
                if (LISTENER != null) {
                    LISTENER.onPaying();
                }
                break;
            case AL_PAY_STATUS_FAIL:
                if (LISTENER != null) {
                    LISTENER.onPayFailed();
                }
                break;
            case AL_PAY_STATUS_CANCEL:
                if (LISTENER != null) {
                    LISTENER.onPayCancel();
                }
                break;
            case AL_PAY_STATUS_CONNECT_ERROR:
                if (LISTENER != null) {
                    LISTENER.onPayConnectError();
                }
                break;
            default:
                break;
        }
    }
}
