package cn.fxn.svm.ec;

import cn.fxn.svm.fxn_core.activities.ProxyActivity;
import cn.fxn.svm.fxn_core.delegates.EcDelegate;

/**
 * @author:Matthew
 * @date:2018/10/15
 * @email:guocheng0816@163.com
 * @func:
 */
public class ExampleActivity extends ProxyActivity {
    @Override
    public EcDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
