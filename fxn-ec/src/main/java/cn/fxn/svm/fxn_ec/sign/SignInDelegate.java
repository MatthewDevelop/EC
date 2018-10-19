package cn.fxn.svm.fxn_ec.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.fxn.svm.fxn_core.delegates.EcDelegate;
import cn.fxn.svm.fxn_core.net.RestClient;
import cn.fxn.svm.fxn_ec.R;
import cn.fxn.svm.fxn_ec.R2;

/**
 * @author:Matthew
 * @date:2018/10/19
 * @email:guocheng0816@163.com
 * @func:
 */
public class SignInDelegate extends EcDelegate {

    @BindView(R2.id.et_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.et_sign_in_password)
    TextInputEditText mPassword = null;


    @OnClick(R2.id.bt_sign_in)
    void onClickSignIn() {
        if(checkForm()){
        }
    }

    @OnClick(R2.id.itv_wechat_sign_in)
    void onClickWeChatSignIn(){

    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLinkSignUp(){
        start(new SignUpDelegate());
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

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}