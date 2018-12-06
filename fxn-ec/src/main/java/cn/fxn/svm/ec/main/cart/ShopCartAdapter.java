package cn.fxn.svm.ec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

import cn.fxn.svm.core.app.EC;
import cn.fxn.svm.core.net.RestClient;
import cn.fxn.svm.core.net.callback.ISuccess;
import cn.fxn.svm.ui.recycler.MultipleFields;
import cn.fxn.svm.ui.recycler.MultipleItemEntity;
import cn.fxn.svm.ui.recycler.MultipleRecyclerAdapter;
import cn.fxn.svm.ui.recycler.MultipleViewHolder;
import cn.fxn.svm.ec.R;

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
    private ICartSelectedTotalPriceListener mCartItemListener = null;
    private List<MultipleItemEntity> mData;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    protected ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        mData = data;
        addItemType(ShopCartItemType.SHOP_CART_ITEM_TYPE, R.layout.item_shop_cart);
    }

    public void setCartItemListener(ICartSelectedTotalPriceListener cartItemListener) {
        mCartItemListener = cartItemListener;
    }

    public void setIsSelectedAll(boolean isSelectedAll) {
        for (MultipleItemEntity entity : mData) {
            entity.setField(ShopCartFields.IS_SELECTED, isSelectedAll);
        }
        checkSelectedItemTotalPrice();
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM_TYPE:
                //取出所有的值
                final int id = entity.getField(MultipleFields.ID);
                final int count = entity.getField(ShopCartFields.COUNT);
                final double price = entity.getField(ShopCartFields.PRICE);
                final String title = entity.getField(ShopCartFields.TITLE);
                final String desc = entity.getField(ShopCartFields.DESC);
                final String imgUrl = entity.getField(MultipleFields.IMAGE_URL);
                //取出所有控件
                final AppCompatImageView thumbImg = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
                final AppCompatTextView tvDes = holder.getView(R.id.tv_item_shop_cart_desc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
                final IconTextView iconSelected = holder.getView(R.id.icon_item_shop_cart_select);
                //绑定数据
                tvTitle.setText(title);
                tvDes.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(imgUrl)
                        .apply(OPTIONS)
                        .into(thumbImg);

                //选中状态按钮渲染之前改变全选或全否的状态
                final boolean isSelected = entity.getField(ShopCartFields.IS_SELECTED);

                //根据数据选中状态显示显示UI
                if (isSelected) {
                    iconSelected.setTextColor(ContextCompat.getColor(EC.getApplication(), R.color.app_main));
                } else {
                    iconSelected.setTextColor(Color.GRAY);
                }
                //设置选中按钮点击事件
                iconSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected = entity.getField(ShopCartFields.IS_SELECTED);
                        if (currentSelected) {
                            iconSelected.setTextColor(Color.GRAY);
                            entity.setField(ShopCartFields.IS_SELECTED, false);
                        } else {
                            iconSelected.setTextColor(ContextCompat.getColor(EC.getApplication(), R.color.app_main));
                            entity.setField(ShopCartFields.IS_SELECTED, true);
                        }
                        checkSelectedItemTotalPrice();
                    }
                });

                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentCount = entity.getField(ShopCartFields.COUNT);
                        if (Integer.parseInt(tvCount.getText().toString()) > 1) {
                            RestClient.builder()
                                    .url("user_profile.json")
                                    .loader(mContext)
                                    .params("count", currentCount)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int countNum = Integer.parseInt(tvCount.getText().toString());
                                            countNum--;
                                            tvCount.setText(String.valueOf(countNum));
                                            entity.setField(ShopCartFields.COUNT, countNum);
                                            checkSelectedItemTotalPrice();
                                        }
                                    })
                                    .build()
                                    .get();
//                                    .post();
                        }
                    }
                });

                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentCount = entity.getField(ShopCartFields.COUNT);
                        RestClient.builder()
                                .url("user_profile.json")
                                .loader(mContext)
                                .params("count", currentCount)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        int countNum = Integer.parseInt(tvCount.getText().toString());
                                        countNum++;
                                        tvCount.setText(String.valueOf(countNum));
                                        entity.setField(ShopCartFields.COUNT, countNum);
                                        checkSelectedItemTotalPrice();
                                    }
                                })
                                .build()
                                .get();
//                                .post();
                    }
                });

                break;
            default:
                break;
        }
    }



    public void checkSelectedItemTotalPrice() {
        double selectedTotalPrice = 0;
        for (MultipleItemEntity entity : mData) {
            final boolean isSelected = entity.getField(ShopCartFields.IS_SELECTED);
            if (isSelected) {
                final double price = entity.getField(ShopCartFields.PRICE);
                final int count = entity.getField(ShopCartFields.COUNT);
                final double totalPrice = price * count;
                selectedTotalPrice +=totalPrice;
            }
        }
        if(mCartItemListener!=null){
            mCartItemListener.updatePrice(selectedTotalPrice);
        }
    }
}
