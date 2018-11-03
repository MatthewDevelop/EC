package cn.fxn.svm.core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import cn.fxn.svm.core.app.EC;

/**
 * @author:Matthew
 * @date:2018/10/16
 * @email:guocheng0816@163.com
 * @func:
 */
public class DimenUtil {

    public static int getScreenWidth(){
        final Resources resources=EC.getApplication().getResources();
        final DisplayMetrics dm=resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources=EC.getApplication().getResources();
        final DisplayMetrics dm=resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
