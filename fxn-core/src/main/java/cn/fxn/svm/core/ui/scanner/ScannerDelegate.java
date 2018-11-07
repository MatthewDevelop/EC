package cn.fxn.svm.core.ui.scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.core.ui.camera.RequestCode;
import cn.fxn.svm.core.util.callback.CallbackManager;
import cn.fxn.svm.core.util.callback.CallbackType;
import cn.fxn.svm.core.util.callback.IGlobalCallback;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * @author:Matthew
 * @date:2018/11/7
 * @email:guocheng0816@163.com
 * @func:
 */
public class ScannerDelegate extends EcDelegate implements ZBarScannerView.ResultHandler {

    private ScannerView mScannerView = null;

    @Override
    public Object setLayout() {
        return mScannerView;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScannerView == null) {
            mScannerView = new ScannerView(getContext());
        }
        mScannerView.setAutoFocus(true);
        mScannerView.setResultHandler(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScannerView != null) {
            mScannerView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView != null) {
            mScannerView.stopCameraPreview();
            mScannerView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        @SuppressWarnings("unchecked") final IGlobalCallback<String> callback = CallbackManager.getInstance()
                .getCallback(CallbackType.ON_SCAN);
        if (callback != null) {
            callback.executeCallback(result.getContents());
        }
        getSupportDelegate().pop();
    }
}
