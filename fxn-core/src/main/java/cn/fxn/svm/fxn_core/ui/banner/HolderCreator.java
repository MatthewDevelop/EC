package cn.fxn.svm.fxn_core.ui.banner;

import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

import cn.fxn.svm.fxn_core.R;

/**
 * @author:Matthew
 * @date:2018/10/24
 * @email:guocheng0816@163.com
 * @func:
 */
public class HolderCreator implements CBViewHolderCreator {

    @Override
    public Holder createHolder(View itemView) {
        return new ImageHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_banner_image;
    }
}
