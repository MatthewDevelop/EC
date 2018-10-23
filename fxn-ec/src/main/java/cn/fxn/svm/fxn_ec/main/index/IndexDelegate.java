package cn.fxn.svm.fxn_ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import cn.fxn.svm.fxn_core.delegates.bottom.BottomItemDelegate;
import cn.fxn.svm.fxn_core.ui.refresh.RefreshHandler;
import cn.fxn.svm.fxn_ec.R;
import cn.fxn.svm.fxn_ec.R2;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class IndexDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mSwipeRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.ic_index_scan)
    IconTextView mIcScan = null;
    @BindView(R2.id.ic_index_message)
    IconTextView mIcMessage = null;

    private RefreshHandler mRefreshHandler=null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler=new RefreshHandler(mSwipeRefreshLayout);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setProgressViewOffset(true, 120, 300);
    }
}
