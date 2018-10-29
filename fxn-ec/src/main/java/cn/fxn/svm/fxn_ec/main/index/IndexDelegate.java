package cn.fxn.svm.fxn_ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.WeakHashMap;

import butterknife.BindView;
import cn.fxn.svm.fxn_core.delegates.bottom.BottomItemDelegate;
import cn.fxn.svm.fxn_core.net.RestCreator;
import cn.fxn.svm.fxn_core.net.rx.RxRestClient;
import cn.fxn.svm.fxn_core.ui.recycler.BaseDecoration;
import cn.fxn.svm.fxn_core.ui.refresh.RefreshHandler;
import cn.fxn.svm.fxn_ec.R;
import cn.fxn.svm.fxn_ec.R2;
import cn.fxn.svm.fxn_ec.main.EcBottomDelegate;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = RefreshHandler.create(mSwipeRefreshLayout, mRecyclerView, new IndexDataConvert());
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("index.json");
//        onCallRxGet();
//        onCallRxRestClient();
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(
                BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background),
                        5));
        final EcBottomDelegate ecBottomDelegate = getParentDelegate();

        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }

    //TODO:测试方法
    void onCallRxRestClient() {
        final String url = "index.json";
        RxRestClient.builder()
                .url(url)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //TODO:测试方法
    void onCallRxGet() {
        final String url = "index.json";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();

        final Observable<String> observable = RestCreator.getRxRestService().get(url, params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
