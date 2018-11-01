package cn.fxn.svm.fxn_ec.main.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.fxn.svm.fxn_core.delegates.bottom.BottomItemDelegate;
import cn.fxn.svm.fxn_ec.R;
import cn.fxn.svm.fxn_ec.R2;
import cn.fxn.svm.fxn_ec.main.user.list.ListAdapter;
import cn.fxn.svm.fxn_ec.main.user.list.ListBean;
import cn.fxn.svm.fxn_ec.main.user.list.ListItemType;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class UserDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_user_setting)
    RecyclerView mRecyclerView=null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_user;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final ListBean push=new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setText("收货地址")
                .build();
        final ListBean system=new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("系统设置")
                .build();
        final List<ListBean> data=new ArrayList<>();
        data.add(push);
        data.add(system);

        //设置RecyclerView
        final LinearLayoutManager manager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter=new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }
}
