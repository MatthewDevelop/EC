package cn.fxn.svm.fxn_core.ui.camera;

import android.net.Uri;

import cn.fxn.svm.fxn_core.delegates.PermissionCheckerDelegate;
import cn.fxn.svm.fxn_core.util.file.FileUtil;

/**
 * @author:Matthew
 * @date:2018/11/2
 * @email:guocheng0816@163.com
 * @func:照相机调用
 */
public class EcCamera {

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }

    public static Uri createCropFile() {
        return Uri.parse(FileUtil.createFile("crop_image",
                FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

}
