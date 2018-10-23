package cn.fxn.svm.fxn_core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import cn.fxn.svm.fxn_core.app.EC;
import cn.fxn.svm.fxn_core.net.RestClient;
import cn.fxn.svm.fxn_core.net.callback.ISuccess;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {

    private final SwipeRefreshLayout SWIPE_REFRESH_LAYOUT;

    public RefreshHandler(SwipeRefreshLayout swipeRefreshLayout) {
        SWIPE_REFRESH_LAYOUT = swipeRefreshLayout;
        SWIPE_REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public void firstPage(String url) {
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(EC.getApplication(), response, Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh() {
        SWIPE_REFRESH_LAYOUT.setRefreshing(true);
        EC.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SWIPE_REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }
}
