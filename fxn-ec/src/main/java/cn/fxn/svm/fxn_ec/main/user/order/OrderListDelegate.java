package cn.fxn.svm.fxn_ec.main.user.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.List;

import butterknife.BindView;
import cn.fxn.svm.fxn_core.delegates.EcDelegate;
import cn.fxn.svm.fxn_core.net.RestClient;
import cn.fxn.svm.fxn_core.net.callback.ISuccess;
import cn.fxn.svm.fxn_ec.R;
import cn.fxn.svm.fxn_ec.R2;
import cn.fxn.svm.fxn_ec.main.user.UserDelegate;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleItemEntity;

/**
 * @author:Matthew
 * @date:2018/11/1
 * @email:guocheng0816@163.com
 * @func:
 */
public class OrderListDelegate extends EcDelegate {

    @BindView(R2.id.rv_order_list)
    RecyclerView mRecyclerView = null;
    private String mType = null;
    private String mUrl = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mType = args.getString(UserDelegate.ORDER_TYPE);
            if (mType != null) {
                switch (mType) {
                    case UserDelegate.ALL:
                        mUrl = "order_list_all.json";
                        break;
                    case UserDelegate.PAY:
                        mUrl = "order_list_pay.json";
                        break;
                    case UserDelegate.RECEIVE:
                        mUrl = "order_list_receive.json";
                        break;
                    case UserDelegate.EVALUATE:
                        mUrl = "order_list_evaluate.json";
                        break;
                    case UserDelegate.AFTER_SALE:
                        mUrl = "order_list_after_sale.json";
                        break;
                    default:
                        break;
                }
            }
        }

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .loader(getContext())
                .url(mUrl)
//                .url("order_list.json")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        final List<MultipleItemEntity> data = new OrderListDataConverter().setJsonData(response).convert();
                        final OrderListAdapter adapter = new OrderListAdapter(data);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }
}
