package cn.fxn.svm.fxn_ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.fxn.svm.fxn_core.delegates.EcDelegate;
import cn.fxn.svm.fxn_core.net.RestClient;
import cn.fxn.svm.fxn_core.net.callback.IRequest;
import cn.fxn.svm.fxn_core.net.callback.ISuccess;
import cn.fxn.svm.fxn_core.util.log.EcLogger;
import cn.fxn.svm.fxn_ec.R;
import cn.fxn.svm.fxn_ec.R2;

/**
 * @author:Matthew
 * @date:2018/10/19
 * @email:guocheng0816@163.com
 * @func:
 */
public class SignUpDelegate extends EcDelegate {

    private static final String TAG = "SignUpDelegate";

    @BindView(R2.id.et_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.et_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.et_sign_up_phone)
    TextInputEditText mPhone = null;
    @BindView(R2.id.et_sign_up_password)
    TextInputEditText mPassword = null;
    @BindView(R2.id.et_sign_up_repeat_password)
    TextInputEditText mRePassword = null;

    private ISignListener mISignListener=null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ISignListener){
            mISignListener= (ISignListener) activity;
        }
    }

    @OnClick(R2.id.bt_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
            RestClient.builder()
                    .url("user_profile.json")
                    .params("name", mName.getText().toString())
                    .params("email", mEmail.getText().toString())
                    .params("phone", mPhone.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            EcLogger.json(TAG, response);
                            SignHandler.onSignUp(response,mISignListener);
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
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();

        boolean isPass = true;

        if (name.isEmpty()) {
            mName.setError("请输入姓名！");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("请输入正确的邮箱格式！");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误！");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少六位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || rePassword.length() < 6 || !rePassword.equals(password)) {
            mRePassword.setError("密码不一致");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }

        return isPass;
    }

    @OnClick(R2.id.tv_link_sign_in)
    void onClickLinkSignUp() {
        getSupportDelegate().startWithPop(new SignInDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
