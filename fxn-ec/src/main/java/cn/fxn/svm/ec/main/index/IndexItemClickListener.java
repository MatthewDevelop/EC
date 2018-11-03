package cn.fxn.svm.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.ec.detail.GoodsDetailDelegate;

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
        final GoodsDetailDelegate goodsDetailDelegate = GoodsDetailDelegate.create();
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
