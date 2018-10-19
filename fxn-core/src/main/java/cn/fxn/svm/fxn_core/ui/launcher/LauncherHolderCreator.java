package cn.fxn.svm.fxn_core.ui.launcher;

import android.util.Log;
import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

import cn.fxn.svm.fxn_core.R;

/**
 * @author:Matthew
 * @date:2018/10/18
 * @email:guocheng0816@163.com
 * @func:
 */
public class LauncherHolderCreator implements CBViewHolderCreator {
    private static final String TAG = "LauncherHolderCreator";

    @Override
    public Holder createHolder(View itemView) {
        return new LauncherHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_banner_image;
    }
}
