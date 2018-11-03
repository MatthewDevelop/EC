package cn.fxn.svm.fxn_core.delegates;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import cn.fxn.svm.fxn_core.ui.camera.CameraImageBean;
import cn.fxn.svm.fxn_core.ui.camera.EcCamera;
import cn.fxn.svm.fxn_core.ui.camera.RequestCode;
import cn.fxn.svm.fxn_core.util.callback.CallbackManager;
import cn.fxn.svm.fxn_core.util.callback.CallbackType;
import cn.fxn.svm.fxn_core.util.callback.IGlobalCallback;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author:Matthew
 * @date:2018/10/15
 * @email:guocheng0816@163.com
 * @func:
 */
@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {
    //不是直接调用方法，生成代码需要
    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void startCamera() {
        EcCamera.start(this);
    }

    //真正需要调用的方法
    public void startCaremaWithCheck() {
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck(this);
    }

    @OnPermissionDenied({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onCameraDenied() {
        Toast.makeText(_mActivity, "拒绝使用相机", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onCameraNever() {
        Toast.makeText(_mActivity, "权限被永久拒绝", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }

    private void showRationaleDialog(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("权限管理")
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCode.TAKE_PHOTO:
                    final Uri resultUri=CameraImageBean.getInstance().getPath();
                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400, 400)
                            .start(getContext(),this);
                    break;
                case RequestCode.PICK_PHOTO:
                    if (data!=null){
                        final Uri pickPath=data.getData();
                        //从相册选择照片后需要有个路径存放剪裁过的照片
                        final String pickCropPath=EcCamera.createCropFile().getPath();
                        UCrop.of(pickPath, Uri.parse(pickCropPath))
                                .withMaxResultSize(400, 400)
                                .start(getContext(),this);
                    }
                    break;
                case RequestCode.CROP_PHOTO:
                    final Uri cropUri=UCrop.getOutput(data);
                    //拿到剪裁后的图片进行处理
                    final IGlobalCallback<Uri> callback=CallbackManager
                            .getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if (callback!=null){
                        callback.excuteCallback(cropUri);
                    }
                    break;
                case RequestCode.CROP_ERROR:
                    Toast.makeText(_mActivity, "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                case RequestCode.SCAN:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerDelegatePermissionsDispatcher
                .onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
