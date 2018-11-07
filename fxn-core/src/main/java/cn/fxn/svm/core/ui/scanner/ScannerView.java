package cn.fxn.svm.core.ui.scanner;

import android.content.Context;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * @author:Matthew
 * @date:2018/11/7
 * @email:guocheng0816@163.com
 * @func:
 */
public class ScannerView extends ZBarScannerView {

    public ScannerView(Context context) {
        this(context, null);
    }

    public ScannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }


    @Override
    protected IViewFinder createViewFinderView(Context context) {
        return new EcViewFinderView(context);
    }
}
