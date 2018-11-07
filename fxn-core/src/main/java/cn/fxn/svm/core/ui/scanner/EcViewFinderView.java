package cn.fxn.svm.core.ui.scanner;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * @author:Matthew
 * @date:2018/11/7
 * @email:guocheng0816@163.com
 * @func:
 */
public class EcViewFinderView extends ViewFinderView {
    public EcViewFinderView(Context context) {
        this(context, null);
    }

    public EcViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mSquareViewFinder = true;
        mBorderPaint.setColor(Color.YELLOW);
        mLaserPaint.setColor(Color.YELLOW);
    }
}
