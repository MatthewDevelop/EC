package cn.fxn.svm.fxn_core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;

import cn.fxn.svm.fxn_core.app.EC;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {

    private final SwipeRefreshLayout SWIPE_REFRESH_LAYOUT;

    public RefreshHandler(SwipeRefreshLayout swipeRefreshLayout){
        SWIPE_REFRESH_LAYOUT=swipeRefreshLayout;
        SWIPE_REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public void refresh(){
        SWIPE_REFRESH_LAYOUT.setRefreshing(true);
        EC.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SWIPE_REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}
