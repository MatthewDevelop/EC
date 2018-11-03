package cn.fxn.svm.core.ui.camera;

import android.net.Uri;

/**
 * @author:Matthew
 * @date:2018/11/2
 * @email:guocheng0816@163.com
 * @func:存储中间值
 */
public final class CameraImageBean {

    private static final CameraImageBean INSTANCE = new CameraImageBean();
    private Uri mPath = null;

    public static CameraImageBean getInstance() {
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri path) {
        mPath = path;
    }
}
