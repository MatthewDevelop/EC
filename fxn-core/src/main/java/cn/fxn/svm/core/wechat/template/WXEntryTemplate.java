package cn.fxn.svm.core.wechat.template;

import cn.fxn.svm.core.wechat.BaseWXEntryActivity;
import cn.fxn.svm.core.wechat.EcWeChat;

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
