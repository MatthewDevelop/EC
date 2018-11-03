package cn.fxn.svm.core.app;

import cn.fxn.svm.core.util.storage.EcPreference;

/**
 * @author:Matthew
 * @date:2018/10/22
 * @email:guocheng0816@163.com
 * @func:
 */
public class AccountManager {

    /**
     * 保存用户登录状态
     *
     * @param signState
     */
    public static void setSignState(boolean signState) {
        EcPreference.setAppFlag(SignTag.SIGN_TAG.name(), signState);
    }

    /**
     * 检查登录状态
     *
     * @param userChecker
     */
    public static void checkAccount(IUserChecker userChecker) {
        if (isSignIn()) {
            userChecker.onSignIn();
        } else {
            userChecker.onNotSignIn();
        }
    }

    /**
     * 获取登录状态
     *
     * @return
     */
    private static boolean isSignIn() {
        return EcPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    private enum SignTag {
        SIGN_TAG
    }
}
