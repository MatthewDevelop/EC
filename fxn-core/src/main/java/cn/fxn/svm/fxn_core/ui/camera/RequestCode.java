package cn.fxn.svm.fxn_core.ui.camera;

import com.yalantis.ucrop.UCrop;

/**
 * @author:Matthew
 * @date:2018/11/2
 * @email:guocheng0816@163.com
 * @func:请求码
 */
public class RequestCode {
    /**
     * 拍照
     */
    public static final int TAKE_PHOTO=4;
    /**
     * 相册选择
     */
    public static final int PICK_PHOTO=5;
    /**
     * 裁剪
     */
    public static final int CROP_PHOTO=UCrop.REQUEST_CROP;
    /**
     * 裁剪错误
     */
    public static final int CROP_ERROR=UCrop.RESULT_ERROR;
    /**
     * 扫描二维码
     */
    public static final int SCAN=7;


}
