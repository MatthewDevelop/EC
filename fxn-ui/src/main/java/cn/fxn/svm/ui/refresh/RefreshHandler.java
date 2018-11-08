package cn.fxn.svm.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;

import cn.fxn.svm.core.app.EC;
import cn.fxn.svm.core.net.RestClient;
import cn.fxn.svm.core.net.callback.IError;
import cn.fxn.svm.core.net.callback.IFailure;
import cn.fxn.svm.core.net.callback.ISuccess;
import cn.fxn.svm.ui.recycler.DataConvert;
import cn.fxn.svm.ui.recycler.MultipleRecyclerAdapter;
import cn.fxn.svm.core.util.log.EcLogger;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {
    private static final String TAG = "RefreshHandler";
    private final SwipeRefreshLayout SWIPE_REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private final DataConvert CONVERT;
    private MultipleRecyclerAdapter mAdapter = null;


    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                           PagingBean bean,
                           RecyclerView recyclerview,
                           DataConvert convert) {
        SWIPE_REFRESH_LAYOUT = swipeRefreshLayout;
        BEAN = bean;
        RECYCLERVIEW = recyclerview;
        CONVERT = convert;
        SWIPE_REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerview,
                                        DataConvert convert) {
        return new RefreshHandler(swipeRefreshLayout, new PagingBean(), recyclerview, convert);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        SWIPE_REFRESH_LAYOUT.setRefreshing(true);
        EC.getHandler().post(new Runnable() {
            @Override
            public void run() {
                firstPage("index.json");
            }
        });
    }

    public void firstPage(String url) {
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
//                        EcLogger.e(TAG, response);
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        //设置Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERT.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.setPageIndex(1);
                        if (SWIPE_REFRESH_LAYOUT.isRefreshing()) {
                            SWIPE_REFRESH_LAYOUT.setRefreshing(false);
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        EcLogger.d("FAILED");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        EcLogger.d(code + " " + msg);
                    }
                })
                .build()
                .get();
    }

    //分页处理
    private void paging(final String url) {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();

        if (mAdapter.getData().size() < pageSize || currentCount >= total) {
            mAdapter.loadMoreEnd(true);
        } else {
            EC.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestClient.builder()
                            .url(url)
//                            .url(url + index)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    mAdapter.addData(CONVERT.setJsonData(response).convert());
                                    BEAN.setCurrentCount(mAdapter.getData().size());
                                    mAdapter.loadMoreComplete();
                                    BEAN.addIndex();
                                }
                            })
                            .build()
                            .get();
                }
            }, 1000);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        paging("index.json");
//        paging("index.json?index=");
    }
}
