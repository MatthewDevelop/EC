package cn.fxn.svm.ec.main.index.search;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.util.List;

import cn.fxn.svm.ec.R;
import cn.fxn.svm.ui.recycler.MultipleFields;
import cn.fxn.svm.ui.recycler.MultipleItemEntity;
import cn.fxn.svm.ui.recycler.MultipleRecyclerAdapter;
import cn.fxn.svm.ui.recycler.MultipleViewHolder;

/**
 * @author:Matthew
 * @date:2018/11/8
 * @email:guocheng0816@163.com
 * @func:
 */
public class SearchAdapter extends MultipleRecyclerAdapter {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    protected SearchAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(SearchItemType.ITEM_SEARCH, R.layout.item_search);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (entity.getItemType()) {
            case SearchItemType.ITEM_SEARCH:
                final AppCompatTextView tvSearchItem = holder.getView(R.id.tv_search_item);
                tvSearchItem.setText((String) entity.getField(MultipleFields.TEXT));
                tvSearchItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            default:
                break;
        }
    }
}
