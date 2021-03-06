package cn.fxn.svm.core.util.callback;

import android.support.annotation.Nullable;

/**
 * @author:Matthew
 * @date:2018/11/3
 * @email:guocheng0816@163.com
 * @func:
 */
public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);

}
