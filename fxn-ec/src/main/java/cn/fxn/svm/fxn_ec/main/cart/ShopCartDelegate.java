package cn.fxn.svm.fxn_ec.main.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import cn.fxn.svm.fxn_core.delegates.bottom.BottomItemDelegate;
import cn.fxn.svm.fxn_core.net.RestClient;
import cn.fxn.svm.fxn_core.net.callback.ISuccess;
import cn.fxn.svm.fxn_core.ui.loader.EcLoader;
import cn.fxn.svm.fxn_core.ui.recycler.MultipleItemEntity;
import cn.fxn.svm.fxn_core.util.log.EcLogger;
import cn.fxn.svm.fxn_ec.R;
import cn.fxn.svm.fxn_ec.R2;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class ShopCartDelegate extends BottomItemDelegate implements ISuccess {

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    private ShopCartAdapter mAdapter = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shopping_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder().url("shop_cart_data.json")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        EcLogger.d(response);
        final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter().setJsonData(response).convert();
        mAdapter=new ShopCartAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
