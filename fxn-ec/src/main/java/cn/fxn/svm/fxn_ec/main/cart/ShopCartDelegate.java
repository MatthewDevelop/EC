package cn.fxn.svm.fxn_ec.main.cart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.fxn.svm.fxn_core.app.EC;
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
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubCompat = null;
    private ShopCartAdapter mAdapter = null;

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll() {
        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0) {
            mIconSelectAll.setTextColor(ContextCompat.getColor(EC.getApplication(), R.color.app_main));
            mIconSelectAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            //更新list显示状态
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }
    }

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelected() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartFields.IS_SELECTED);
            if (isSelected) {
                deleteEntities.add(entity);
            }
        }

        for (MultipleItemEntity entity : deleteEntities) {
            data.remove(entity);
        }
        mAdapter.notifyDataSetChanged();
        checkoutItemCount();
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear() {
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        checkoutItemCount();
    }

    private void checkoutItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            @SuppressLint("RestrictedApi") final View stubView = mStubCompat.inflate();
            final AppCompatTextView stubTextView=stubView.findViewById(R.id.tv_stub_to_buy);
            stubTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DO NOTHING
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        }else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shopping_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mIconSelectAll.setTag(0);
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
        mAdapter = new ShopCartAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        checkoutItemCount();
    }
}
