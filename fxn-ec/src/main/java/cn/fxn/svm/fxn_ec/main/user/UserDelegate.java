package cn.fxn.svm.fxn_ec.main.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.fxn.svm.fxn_core.delegates.bottom.BottomItemDelegate;
import cn.fxn.svm.fxn_ec.R;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class UserDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_user;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}