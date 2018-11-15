package cn.fxn.svm.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.fxn.svm.core.net.RestClient;
import cn.fxn.svm.core.net.callback.IError;
import cn.fxn.svm.core.net.callback.IFailure;
import cn.fxn.svm.core.net.callback.ISuccess;
import cn.fxn.svm.core.util.callback.CallbackManager;
import cn.fxn.svm.core.util.callback.CallbackType;
import cn.fxn.svm.core.util.callback.IGlobalCallback;
import cn.fxn.svm.ui.recycler.DataConverter;
import cn.fxn.svm.ui.recycler.MultipleRecyclerAdapter;
import cn.fxn.svm.core.util.log.EcLogger;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "RefreshHandler";
    private final SwipeRefreshLayout SWIPE_REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final DataConverter CONVERT;
    private final MultipleRecyclerAdapter ADAPTER;


    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                           PagingBean bean,
                           DataConverter convert, MultipleRecyclerAdapter adapter) {
        SWIPE_REFRESH_LAYOUT = swipeRefreshLayout;
        BEAN = bean;
        CONVERT = convert;
        ADAPTER = adapter;
        SWIPE_REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        DataConverter convert,
                                        MultipleRecyclerAdapter adapter) {
        return new RefreshHandler(swipeRefreshLayout, new PagingBean(), convert, adapter);
    }

    @Override
    public void onRefresh() {
        SWIPE_REFRESH_LAYOUT.setRefreshing(true);
        IGlobalCallback refreshCallback = CallbackManager.getInstance().getCallback(CallbackType.ON_REFRESH);
        if (refreshCallback != null) {
            //noinspection unchecked
            refreshCallback.executeCallback(null);
        }
    }

    /**
     * 获取首页
     * @param url
     */
    public void firstPage(String url) {
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        BEAN.setPageIndex(1);
                        ADAPTER.getData().clear();
                        ADAPTER.addData(CONVERT.setJsonData(response).convert());
                        ADAPTER.notifyDataSetChanged();
                        if (SWIPE_REFRESH_LAYOUT.isRefreshing()) {
                            SWIPE_REFRESH_LAYOUT.setRefreshing(false);
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        if (SWIPE_REFRESH_LAYOUT.isRefreshing()) {
                            SWIPE_REFRESH_LAYOUT.setRefreshing(false);
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        EcLogger.d(code + " " + msg);
                        if (SWIPE_REFRESH_LAYOUT.isRefreshing()) {
                            SWIPE_REFRESH_LAYOUT.setRefreshing(false);
                        }
                    }
                })
                .build()
                .get();
    }

    //分页处理
    public void paging(final String url) {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();

        if (ADAPTER.getData().size() < pageSize || currentCount >= total) {
            ADAPTER.loadMoreEnd(false);
        } else {
            RestClient.builder()
                    .url(url)
//                            .url(url + index)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            ADAPTER.addData(CONVERT.setJsonData(response).convert());
                            BEAN.setCurrentCount(ADAPTER.getData().size());
                            ADAPTER.loadMoreComplete();
                            BEAN.addIndex();
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            ADAPTER.loadMoreFail();
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            ADAPTER.loadMoreFail();
                        }
                    })
                    .build()
                    .get();
        }
    }
}

