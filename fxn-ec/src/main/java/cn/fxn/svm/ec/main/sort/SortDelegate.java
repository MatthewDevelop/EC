package cn.fxn.svm.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import cn.fxn.svm.core.delegates.bottom.BottomItemDelegate;
import cn.fxn.svm.ec.R;
import cn.fxn.svm.ec.main.sort.content.ContentDelegate;
import cn.fxn.svm.ec.main.sort.list.VerticalListDelegate;
import qiu.niorgai.StatusBarCompat;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class SortDelegate extends BottomItemDelegate {
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListDelegate verticalListDelegate = new VerticalListDelegate();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container, verticalListDelegate);
        //设置右侧第一个分类显示，默认显示分类1
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1), false, false);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.setStatusBarColor(getProxyActivity()
                , ContextCompat.getColor(getContext(), R.color.app_main));
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
