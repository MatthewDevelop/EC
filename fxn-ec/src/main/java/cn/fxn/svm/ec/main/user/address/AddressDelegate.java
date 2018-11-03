package cn.fxn.svm.ec.main.user.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.core.net.RestClient;
import cn.fxn.svm.core.net.callback.ISuccess;
import cn.fxn.svm.ec.R;
import cn.fxn.svm.ec.R2;

/**
 * @author:Matthew
 * @date:2018/11/3
 * @email:guocheng0816@163.com
 * @func:
 */
public class AddressDelegate extends EcDelegate implements ISuccess {

    @BindView(R2.id.rv_address)
    RecyclerView mRecyclerView=null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        RestClient.builder()
                .url("address.json")
                .loader(_mActivity)
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final LinearLayoutManager manager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final AddressAdapter adapter=new AddressAdapter(new AddressDataConverter().setJsonData(response).convert());
        mRecyclerView.setAdapter(adapter);
    }
}
