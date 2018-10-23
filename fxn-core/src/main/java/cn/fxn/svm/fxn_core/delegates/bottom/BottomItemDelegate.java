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
public abstract class BottomItemDelegate extends EcDelegate implements View.OnKeyListener {

    private long mExitTime=0;
    public static final int EXIT_TIME=2000;

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis()-mExitTime>EXIT_TIME){
                Toast.makeText(getContext(), "再次点击退出应用", Toast.LENGTH_SHORT).show();
                mExitTime=System.currentTimeMillis();
            }else {
                _mActivity.finish();
                if(mExitTime!=0){
                    mExitTime=0;
                }
            }
            return true;
        }
        //事件已经被消化掉
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        //不进行处理点击事件不会生效
        final View rootView=getView();
        if(rootView!=null){
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }
}
