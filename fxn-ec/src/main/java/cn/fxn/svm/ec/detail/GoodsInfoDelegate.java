package cn.fxn.svm.ec.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import butterknife.BindView;
import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.ec.R;
import cn.fxn.svm.ec.R2;

/**
 * @author:Matthew
 * @date:2018/11/8
 * @email:guocheng0816@163.com
 * @func:
 */
public class GoodsInfoDelegate extends EcDelegate {


    private static final String ARG_GOODS_INFO = "ARG_GOODS_INFO";
    @BindView(R2.id.tv_goods_info_title)
    AppCompatTextView mGoodsInfoTitle = null;
    @BindView(R2.id.tv_goods_info_desc)
    AppCompatTextView mGoodsInfoDesc = null;
    @BindView(R2.id.tv_goods_info_price)
    AppCompatTextView mGoodsInfoPrice = null;

    private JSONObject mData = null;

    public static GoodsInfoDelegate create(@NonNull String goodsInfo) {
        final Bundle args = new Bundle();
        args.putString(ARG_GOODS_INFO, goodsInfo);
        final GoodsInfoDelegate goodsInfoDelegate = new GoodsInfoDelegate();
        goodsInfoDelegate.setArguments(args);
        return goodsInfoDelegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_info;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final String name = mData.getString("name");
        final String desc = mData.getString("description");
        final double price = mData.getDouble("price");
        mGoodsInfoTitle.setText(name);
        mGoodsInfoDesc.setText(desc);
        mGoodsInfoPrice.setText(String.valueOf(price));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle bundle = getArguments();
        final String goodsInfo = bundle.getString(ARG_GOODS_INFO);
        mData = JSON.parseObject(goodsInfo);
    }
}
