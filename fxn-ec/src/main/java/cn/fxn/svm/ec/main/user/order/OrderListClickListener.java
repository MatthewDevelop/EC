package cn.fxn.svm.ec.main.user.order;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.ui.recycler.MultipleFields;
import cn.fxn.svm.ui.recycler.MultipleItemEntity;

/**
 * @author:Matthew
 * @date:2018/11/5
 * @email:guocheng0816@163.com
 * @func:
 */
public class OrderListClickListener extends SimpleClickListener {

    private final EcDelegate DELEGATE;

    public OrderListClickListener(EcDelegate delegate) {
        DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity= (MultipleItemEntity) adapter.getData().get(position);
        final OrderCommentDelegate orderCommentDelegate=new OrderCommentDelegate();
        Bundle bundle=new Bundle();
        bundle.putString("url", (String) entity.getField(MultipleFields.IMAGE_URL));
        orderCommentDelegate.setArguments(bundle);
        DELEGATE.getSupportDelegate().start(orderCommentDelegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
