package cn.fxn.svm.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.ec.detail.GoodsDetailDelegate;
import cn.fxn.svm.ui.recycler.MultipleFields;
import cn.fxn.svm.ui.recycler.MultipleItemEntity;

/**
 * @author:Matthew
 * @date:2018/10/25
 * @email:guocheng0816@163.com
 * @func:
 */
public class IndexItemClickListener extends SimpleClickListener {

    private final EcDelegate DELEGATE;

    private IndexItemClickListener(EcDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static IndexItemClickListener create(EcDelegate delegate) {
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) adapter.getData().get(position);
        final GoodsDetailDelegate goodsDetailDelegate =
                GoodsDetailDelegate.create((Integer) entity.getField(MultipleFields.ID));
        DELEGATE.getSupportDelegate().start(goodsDetailDelegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        //do nothing
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        //do nothing
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        //do nothing
    }

}
