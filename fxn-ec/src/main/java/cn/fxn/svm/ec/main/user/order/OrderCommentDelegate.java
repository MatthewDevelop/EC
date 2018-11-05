package cn.fxn.svm.ec.main.user.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.ec.R;
import cn.fxn.svm.ec.R2;
import cn.fxn.svm.ui.wdget.StartLayout;

/**
 * @author:Matthew
 * @date:2018/11/5
 * @email:guocheng0816@163.com
 * @func:
 */
public class OrderCommentDelegate extends EcDelegate {

    @BindView(R2.id.custom_comment_star)
    StartLayout mStartLayout = null;

    @OnClick(R2.id.top_tv_comment_commit)
    void onSubmit() {
        Toast.makeText(_mActivity, mStartLayout.getStartCount()+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
