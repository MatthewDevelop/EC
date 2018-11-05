package cn.fxn.svm.ec.main.user.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.core.util.callback.CallbackManager;
import cn.fxn.svm.core.util.callback.CallbackType;
import cn.fxn.svm.ec.R;
import cn.fxn.svm.ec.R2;
import cn.fxn.svm.ec.main.user.UserOnClickListener;
import cn.fxn.svm.ec.main.user.list.ListAdapter;
import cn.fxn.svm.ec.main.user.list.ListBean;
import cn.fxn.svm.ec.main.user.list.ListItemType;

/**
 * @author:Matthew
 * @date:2018/11/5
 * @email:guocheng0816@163.com
 * @func:
 */
public class SettingsDelegate extends EcDelegate {

    @BindView(R2.id.rv_settings)
    RecyclerView mRecyclerView=null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final ListBean push = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_SWITCH)
                .setId(1)
                .setText("消息推送")
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            CallbackManager.getInstance().getCallback(CallbackType.TAG_OPEN_PUSH).executeCallback(null);
                            Toast.makeText(_mActivity, "开启推送", Toast.LENGTH_SHORT).show();
                        }else {
                            CallbackManager.getInstance().getCallback(CallbackType.TAG_STOP_PUSH).executeCallback(null);
                            Toast.makeText(_mActivity, "关闭推送", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .build();
        final ListBean about = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setDelegate(new AboutDelegate())
                .setText("关于")
                .build();
        final List<ListBean> data = new ArrayList<>();
        data.add(push);
        data.add(about);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new SettingsOnClickListener(this));
    }
}
