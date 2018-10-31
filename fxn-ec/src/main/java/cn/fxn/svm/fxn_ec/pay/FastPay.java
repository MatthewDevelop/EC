package cn.fxn.svm.fxn_ec.pay;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;

import cn.fxn.svm.fxn_core.delegates.EcDelegate;
import cn.fxn.svm.fxn_core.net.RestClient;
import cn.fxn.svm.fxn_core.net.callback.ISuccess;
import cn.fxn.svm.fxn_ec.R;


/**
 * @author:Matthew
 * @date:2018/10/31
 * @email:guocheng0816@163.com
 * @func:
 */
public class FastPay implements View.OnClickListener {

    //设置支付回调监听
    private IAliPayResultListener mAliPayResultListener = null;
    private Activity mActivity = null;

    private AlertDialog mDialog = null;
    private int mOrderID = -1;

    private FastPay(EcDelegate delegate) {
        this.mActivity = delegate.getProxyActivity();
        this.mDialog = new AlertDialog.Builder(delegate.getContext()).create();
    }

    public static FastPay create(EcDelegate delegate) {
        return new FastPay(delegate);
    }

    public FastPay setPayResultListener(IAliPayResultListener listener){
        this.mAliPayResultListener=listener;
        return this;
    }

    public FastPay setOrderId(int orderId){
        this.mOrderID=orderId;
        return this;
    }

    public void beginPayDailog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dailog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            //设置动画效果
            window.setWindowAnimations(R.style.anim_panel_up_from_buttom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);
            window.findViewById(R.id.icon_dialog_pay_ali_pay).setOnClickListener(this);
            window.findViewById(R.id.icon_dialog_pay_we_chat).setOnClickListener(this);
        }
    }


    /**
     * library中不能使用switch
     * @param v
     */
    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.btn_dialog_pay_cancel) {
            aliPay(mOrderID);
            mDialog.cancel();
        } else if (id == R.id.icon_dialog_pay_ali_pay) {
            mDialog.cancel();
        } else if (id == R.id.icon_dialog_pay_we_chat) {
            mDialog.cancel();
        }
    }

    private final void aliPay(int orderId){
        final String signUrl="";
        //获取签名信息字符串
        RestClient.builder()
                .url(signUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //此处是和后台约定的
                        final String paySign=JSON.parseObject(response).getString("result");
                        //调用支付宝客户端必须是异步
                        final PayAsyncTask payAsyncTask=new PayAsyncTask(mActivity, mAliPayResultListener);
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,paySign);
                    }
                })
                .build()
                .post();
    }
}
