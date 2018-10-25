package cn.fxn.svm.fxn_core.delegates;

/**
 * @author:Matthew
 * @date:2018/10/15
 * @email:guocheng0816@163.com
 * @func:
 */
public abstract class EcDelegate extends PermissionCheckerDelegate {

    @SuppressWarnings("unchecked")
    public <T extends EcDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }

}
