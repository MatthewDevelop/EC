package cn.fxn.svm.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;


/**
 * @author:Matthew
 * @date:2018/10/24
 * @email:guocheng0816@163.com
 * @func:
 */
public class MultipleRecyclerAdapter
        extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>{
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {

    }
}
