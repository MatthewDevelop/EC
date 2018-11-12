package cn.fxn.svm.ec.main.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import cn.fxn.svm.core.delegates.bottom.BottomItemDelegate;
import cn.fxn.svm.core.delegates.web.WebDelegateImpl;
import cn.fxn.svm.ec.R;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import qiu.niorgai.StatusBarCompat;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class DiscoverDelegate extends BottomItemDelegate {
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebDelegateImpl delegate = WebDelegateImpl.create("index.html");
        delegate.setTopDelegate(this.getParentDelegate());
        getSupportDelegate().loadRootFragment(R.id.web_container, delegate);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.setStatusBarColor(getProxyActivity()
                , ContextCompat.getColor(getContext(), R.color.app_main));
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
