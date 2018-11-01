package cn.fxn.svm.fxn_ec.main.user.order;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import cn.fxn.svm.fxn_ec.R;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleFields;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleItemEntity;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleRecyclerAdapter;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleViewHolder;

/**
 * @author:Matthew
 * @date:2018/11/1
 * @email:guocheng0816@163.com
 * @func:
 */
public class OrderListAdapter extends MultipleRecyclerAdapter {

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
    protected OrderListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_ORDER_LIST, R.layout.item_order_list);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case OrderListItemType.ITEM_ORDER_LIST:
                final AppCompatImageView imageView = holder.getView(R.id.image_order_list);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_order_list_title);
                final AppCompatTextView tvTime = holder.getView(R.id.tv_order_list_time);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_order_list_price);

                final String imgUrl = entity.getField(MultipleFields.IMAGE_URL);
                final String title = entity.getField(MultipleFields.TITLE);
                final String time = entity.getField(OrderItemFields.TIME);
                final double price = entity.getField(OrderItemFields.PRICE);

                tvTitle.setText(title);
                tvTime.setText(time);
                tvPrice.setText("￥"+String.valueOf(price));

                Glide.with(mContext)
                        .load(imgUrl)
                        .apply(OPTIONS)
                        .into(imageView);

                break;
            default:
                break;
        }
    }
}
