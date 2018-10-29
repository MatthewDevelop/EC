package cn.fxn.svm.fxn_ec.main.cart;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;
import java.util.Observable;

import cn.fxn.svm.fxn_core.ui.recycler.MultipleFields;
import cn.fxn.svm.fxn_core.ui.recycler.MultipleItemEntity;
import cn.fxn.svm.fxn_core.ui.recycler.MultipleRecyclerAdapter;
import cn.fxn.svm.fxn_core.ui.recycler.MultipleViewHolder;
import cn.fxn.svm.fxn_ec.R;

/**
 * @author:Matthew
 * @date:2018/10/29
 * @email:guocheng0816@163.com
 * @func:
 */
public class ShopCartAdapter extends MultipleRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    protected ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ShopCartItemType.SHOP_CART_ITEM_TYPE, R.layout.item_shop_cart);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM_TYPE:
                //取出所有的值
                final int id = entity.getField(MultipleFields.ID);
                final int count = entity.getField(ShopCartFields.COUNT);
                final double price = entity.getField(ShopCartFields.PRICE);
                final String title = entity.getField(ShopCartFields.TITLE);
                final String desc = entity.getField(ShopCartFields.DESC);
                final String imagUrl = entity.getField(MultipleFields.IMAGE_URL);
                //取出所有控件
                final AppCompatImageView thumbImg = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
                final AppCompatTextView tvDes = holder.getView(R.id.tv_item_shop_cart_desc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
                //绑定数据
                tvTitle.setText(title);
                tvDes.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(imagUrl)
                        .apply(OPTIONS)
                        .into(thumbImg);
                break;
            default:
                break;
        }
    }
}
