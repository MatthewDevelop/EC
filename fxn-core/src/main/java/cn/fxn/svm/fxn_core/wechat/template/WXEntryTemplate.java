package cn.fxn.svm.fxn_core.wechat.template;

import cn.fxn.svm.fxn_core.activities.ProxyActivity;
import cn.fxn.svm.fxn_core.delegates.EcDelegate;
import cn.fxn.svm.fxn_core.wechat.BaseWXEntryActivity;
import cn.fxn.svm.fxn_core.wechat.EcWeChat;

/**
 * @author:Matthew
 * @date:2018/10/22
 * @email:guocheng0816@163.com
 * @func:
 */
public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onSignSuccess(String userInfo) {
        EcWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
