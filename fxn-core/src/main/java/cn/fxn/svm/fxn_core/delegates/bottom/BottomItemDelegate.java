package cn.fxn.svm.fxn_core.delegates.bottom;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import cn.fxn.svm.fxn_core.delegates.EcDelegate;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public abstract class BottomItemDelegate extends EcDelegate {

    private long TOUCH_TIME=0;
    public static final int WAIT_TIME=2000;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "再次点击退出应用", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
