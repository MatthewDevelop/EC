package cn.fxn.svm.fxn_ec.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;
import cn.fxn.svm.fxn_core.delegates.EcDelegate;
import cn.fxn.svm.fxn_core.util.storage.EcPreference;
import cn.fxn.svm.fxn_core.util.timer.BaseTimerTask;
import cn.fxn.svm.fxn_core.util.timer.ITimerListener;
import cn.fxn.svm.fxn_ec.R;
import cn.fxn.svm.fxn_ec.R2;

/**
 * @author:Matthew
 * @date:2018/10/18
 * @email:guocheng0816@163.com
 * @func:
 */
public class LauncherDelegate extends EcDelegate implements ITimerListener {

    @BindView(R2.id.tv_timer)
    AppCompatTextView mTvTimer;

    private Timer mTimer;
    private int count=5;

    @OnClick(R2.id.tv_timer)
    void onClickTvTimer() {
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
            checkIsShowScrollLauncher();
        }
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 500,1000);
    }

    private void checkIsShowScrollLauncher(){
        if(!EcPreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCH_APP.name())){
            start(new LauncherScrollDelegate(), SINGLETASK);
        }else {
            //检查用户登陆信息
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mTvTimer!=null){
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", count));
                    count--;
                    if(count<0){
                        if(mTimer!=null){
                            mTimer.cancel();
                            mTimer=null;
                            checkIsShowScrollLauncher();
                        }
                    }
                }
            }
        });
    }
}
