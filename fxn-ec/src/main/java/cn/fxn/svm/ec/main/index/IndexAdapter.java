package cn.fxn.svm.ec.main.index;

import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.fxn.svm.ui.banner.BannerCreator;
import cn.fxn.svm.ui.recycler.ItemType;
import cn.fxn.svm.ui.recycler.MultipleFields;
import cn.fxn.svm.ui.recycler.MultipleItemEntity;
import cn.fxn.svm.ui.recycler.MultipleRecyclerAdapter;
import cn.fxn.svm.ui.recycler.MultipleViewHolder;

/**
 * Author:Matthew
 * <p>
 * Date:2018/11/15
 * <p>
 * Email:guocheng0816@163.com
 * <p>
 * Func:主页内容的适配器
 */
public class IndexAdapter extends MultipleRecyclerAdapter
        implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {

    /**
     * 初始化一次banner
     */
    private boolean isInitBanner = false;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    protected IndexAdapter(List<MultipleItemEntity> data) {
        super(data);
        //设置不同item的布局
        addItemType(ItemType.TEXT, cn.fxn.svm.ui.R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, cn.fxn.svm.ui.R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE, cn.fxn.svm.ui.R.layout.item_multiple_image_text);
        addItemType(ItemType.BANNER, cn.fxn.svm.ui.R.layout.item_multiple_banner);
        //设置宽度监听
        setSpanSizeLookup(this);
    }


    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {
    }


    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;
        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(cn.fxn.svm.ui.R.id.text_single, text);
                break;
            case ItemType.IMAGE:
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .dontAnimate()
                                .centerCrop())
                        .into((ImageView) holder.getView(cn.fxn.svm.ui.R.id.img_single));
                break;
            case ItemType.TEXT_IMAGE:
                text = entity.getField(MultipleFields.TEXT);
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .dontAnimate()
                                .centerCrop())
                        .into((ImageView) holder.getView(cn.fxn.svm.ui.R.id.img_multiple));
                holder.setText(cn.fxn.svm.ui.R.id.tv_multiple, text);
                break;
            case ItemType.BANNER:
                if (!isInitBanner) {
                    bannerImages = entity.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(cn.fxn.svm.ui.R.id.banner_recycler);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    isInitBanner = true;
                }
                break;
            default:
                break;
        }
    }
}
