package cn.fxn.svm.ec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.fxn.svm.fxn_core.delegates.EcDelegate;

/**
 * @author:Matthew
 * @date:2018/10/15
 * @email:guocheng0816@163.com
 * @func:
 */
public class ExampleDelegate extends EcDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
