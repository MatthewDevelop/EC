package cn.fxn.svm.ec.main.user.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;

import butterknife.BindView;
import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.core.net.RestClient;
import cn.fxn.svm.core.net.callback.ISuccess;
import cn.fxn.svm.ec.R;
import cn.fxn.svm.ec.R2;

/**
 * @author:Matthew
 * @date:2018/11/5
 * @email:guocheng0816@163.com
 * @func:
 */
public class AboutDelegate extends EcDelegate {

    @BindView(R2.id.tv_info)
    AppCompatTextView mTextView=null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        RestClient.builder()
                .url("about.json")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        String content=JSON.parseObject(response)
                                .getString("data");
                        mTextView.setText(content);
                    }
                })
                .build()
                .get();
    }
}
