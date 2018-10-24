package cn.fxn.svm.fxn_core.ui.banner;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import cn.fxn.svm.fxn_core.R;
import cn.fxn.svm.fxn_core.app.EC;

/**
 * @author:Matthew
 * @date:2018/10/24
 * @email:guocheng0816@163.com
 * @func:
 */
public class ImageHolder extends Holder<String> {

    private AppCompatImageView mImageView;

    public ImageHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        mImageView = (AppCompatImageView) itemView;
    }

    @Override
    public void updateUI(String imageUrl) {
        Glide.with(EC.getApplication())
                .load(imageUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop())
                .into(mImageView);
    }
}
