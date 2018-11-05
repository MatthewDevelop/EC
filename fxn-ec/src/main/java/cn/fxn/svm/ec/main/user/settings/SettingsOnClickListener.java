package cn.fxn.svm.ec.main.user.settings;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.ec.main.user.list.ListBean;

/**
 * @author:Matthew
 * @date:2018/11/5
 * @email:guocheng0816@163.com
 * @func:
 */
public class SettingsOnClickListener extends SimpleClickListener {

    private final EcDelegate DELEGATE;

    public SettingsOnClickListener(EcDelegate delegate) {
        DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                //do nothing
                break;
            case 2:
                DELEGATE.getSupportDelegate().start(bean.getDelegate());
                break;
            default:
                break;
        }
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
