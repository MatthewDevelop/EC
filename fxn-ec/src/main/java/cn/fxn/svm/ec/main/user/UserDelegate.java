package cn.fxn.svm.ec.main.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.fxn.svm.core.delegates.bottom.BottomItemDelegate;
import cn.fxn.svm.ec.R;
import cn.fxn.svm.ec.R2;
import cn.fxn.svm.ec.main.user.address.AddressDelegate;
import cn.fxn.svm.ec.main.user.list.ListAdapter;
import cn.fxn.svm.ec.main.user.list.ListBean;
import cn.fxn.svm.ec.main.user.list.ListItemType;
import cn.fxn.svm.ec.main.user.order.OrderListDelegate;
import cn.fxn.svm.ec.main.user.profile.UserProfileClickListener;
import cn.fxn.svm.ec.main.user.profile.UserProfileDelegate;
import cn.fxn.svm.ec.main.user.settings.SettingsDelegate;
import qiu.niorgai.StatusBarCompat;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class UserDelegate extends BottomItemDelegate {

    public static final String ORDER_TYPE = "ORDER_TYPE";
    public static final String ALL = "ALL";
    public static final String PAY = "PAY";
    public static final String RECEIVE = "RECEIVE";
    public static final String EVALUATE = "EVALUATE";
    public static final String AFTER_SALE = "AFTER_SALE";
    @BindView(R2.id.rv_user_setting)
    RecyclerView mRecyclerView = null;
    private Bundle args = null;

    @OnClick(R2.id.tv_all_order)
    void onClickAllOrder() {
        args.putString(ORDER_TYPE, ALL);
        startOrderListByType();
    }

    private void startOrderListByType() {
        final OrderListDelegate orderListDelegate = new OrderListDelegate();
        orderListDelegate.setArguments(args);
        getParentDelegate().getSupportDelegate().start(orderListDelegate);
    }

    @OnClick(R2.id.img_user_avatar)
    void onClickAvatar() {
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }

    @OnClick(R2.id.ll_pay)
    void onClickLLPay() {
        args.putString(ORDER_TYPE, PAY);
        startOrderListByType();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.setStatusBarColor(getProxyActivity()
                , ContextCompat.getColor(getContext(), R.color.app_main));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = new Bundle();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_user;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final ListBean push = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setText("收货地址")
                .setDelegate(new AddressDelegate())
                .build();
        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("系统设置")
                .setDelegate(new SettingsDelegate())
                .build();
        final List<ListBean> data = new ArrayList<>();
        data.add(push);
        data.add(system);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new UserOnClickListener(this));
    }

    @OnClick(R2.id.ll_receive)
    void onClickLLReceive() {
        args.putString(ORDER_TYPE, RECEIVE);
        startOrderListByType();
    }

    @OnClick(R2.id.ll_evaluate)
    void onClickLLEvaluate() {
        args.putString(ORDER_TYPE, EVALUATE);
        startOrderListByType();
    }

    @OnClick(R2.id.ll_after_sale)
    void onClickLLAfterSale() {
        args.putString(ORDER_TYPE, AFTER_SALE);
        startOrderListByType();
    }
}
