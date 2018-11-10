package cn.fxn.svm.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.core.net.RestClient;
import cn.fxn.svm.core.net.callback.IFailure;
import cn.fxn.svm.core.net.callback.ISuccess;
import cn.fxn.svm.core.util.log.EcLogger;
import cn.fxn.svm.core.wechat.EcWeChat;
import cn.fxn.svm.core.wechat.callbacks.IWeChatSignInCallback;
import cn.fxn.svm.ec.R;
import cn.fxn.svm.ec.R2;

/**
 * @author:Matthew
 * @date:2018/10/19
 * @email:guocheng0816@163.com
 * @func:
 */
public class SignInDelegate extends EcDelegate {
    private static final String TAG = "SignInDelegate";

    @BindView(R2.id.et_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.et_sign_in_password)
    TextInputEditText mPassword = null;

    private ISignListener mISignListener = null;

    @OnClick(R2.id.bt_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
            EcLogger.d("POST");
            RestClient.builder()
                    .url("user_profile.json")
                    .params("email", mEmail.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            EcLogger.json(TAG, response);
                            SignHandler.onSignIn(response, mISignListener);
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            EcLogger.d(throwable.getMessage());
                        }
                    })
                    .build()
                    .post();
        }
    }

    /**
     * 检查表单
     *
     * @return
     */
    private boolean checkForm() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("请输入正确的邮箱格式！");
            isPass = false;
        } else {
            mEmail.setError(null);
        }


        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少六位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @OnClick(R2.id.itv_wechat_sign_in)
    void onClickWeChatSignIn() {
        EcWeChat.getInstance()
                .onSignInSuccess(new IWeChatSignInCallback() {
                    @Override
                    public void onSignInSuccess(String userInfo) {
                        //微信登录成功回调
                    }
                })
                .signIn();
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLinkSignUp() {
        getSupportDelegate().startWithPop(new SignUpDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }
}
