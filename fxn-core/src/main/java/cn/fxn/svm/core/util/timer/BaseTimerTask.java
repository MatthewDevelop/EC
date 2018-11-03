package cn.fxn.svm.core.util.timer;

import java.util.TimerTask;

/**
 * @author:Matthew
 * @date:2018/10/18
 * @email:guocheng0816@163.com
 * @func:
 */
public class BaseTimerTask extends TimerTask {

    public BaseTimerTask(ITimerListener timerListener) {
        mITimerListener = timerListener;
    }

    private ITimerListener mITimerListener=null;

    @Override
    public void run() {
        if (mITimerListener!=null){
            mITimerListener.onTimer();
        }
    }
}
