package cn.fxn.svm.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import cn.fxn.svm.core.activities.ProxyActivity;
import cn.fxn.svm.core.app.EC;
import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.ui.launcher.ILauncherListener;
import cn.fxn.svm.ui.launcher.OnLauncherFinishTag;
import cn.fxn.svm.ec.launcher.LauncherDelegate;
import cn.fxn.svm.ec.main.EcBottomDelegate;
import cn.fxn.svm.ec.sign.ISignListener;
import cn.fxn.svm.ec.sign.SignInDelegate;
import cn.jpush.android.api.JPushInterface;

/**
 * @author:Matthew
 * @date:2018/10/15
 * @email:guocheng0816@163.com
 * @func:
 */
public class ExampleActivity extends ProxyActivity implements ISignListener, ILauncherListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        EC.getConfigurator().withActivity(this);
    }

    @Override
    public EcDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                Toast.makeText(this, "用户登录了", Toast.LENGTH_SHORT).show();
                getSupportDelegate().startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this, "用户没登录", Toast.LENGTH_SHORT).show();
                getSupportDelegate().startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
}
