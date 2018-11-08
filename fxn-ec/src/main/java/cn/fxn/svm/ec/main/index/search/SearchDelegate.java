package cn.fxn.svm.ec.main.index.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.core.net.RestClient;
import cn.fxn.svm.core.net.callback.ISuccess;
import cn.fxn.svm.core.util.storage.EcPreference;
import cn.fxn.svm.ec.R;
import cn.fxn.svm.ec.R2;
import cn.fxn.svm.ui.recycler.MultipleItemEntity;

/**
 * @author:Matthew
 * @date:2018/11/8
 * @email:guocheng0816@163.com
 * @func:
 */
public class SearchDelegate extends EcDelegate {

    @BindView(R2.id.rv_search)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mEtSearch = null;

    @OnClick(R2.id.tv_top_search)
    void onClickSearch() {
        final String searchItemText = mEtSearch.getText().toString();
        saveItem(searchItemText);
//        if (!StringUtils.isEmpty(searchItemText)) {
//            RestClient.builder()
//                    .url("search.php?key=" + searchItemText)
//                    .success(new ISuccess() {
//                        @Override
//                        public void onSuccess(String response) {
//                            saveItem(searchItemText);
//                            mEtSearch.setText("");
//                            //处理搜索结果
//                        }
//                    })
//                    .build()
//                    .get();
    }

}

    @SuppressWarnings("unchecked")
    private void saveItem(String item) {
        if (!StringUtils.isEmpty(item) && !StringUtils.isSpace(item)) {
            List<String> history;
            final String historyStr = EcPreference.getCustomAppProfile(SearchDatConverter.TAG_SEARCH_HISTORY);
            if (StringUtils.isEmpty(historyStr)) {
                history = new ArrayList<>();
            } else {
                history = JSON.parseObject(historyStr, ArrayList.class);
            }
            history.add(item);
            final String json = JSON.toJSONString(history);
            EcPreference.addCustomAppProfile(SearchDatConverter.TAG_SEARCH_HISTORY, json);
        }
    }

    @OnClick(R2.id.icon_top_search_back)
    void onClickBack() {
        getSupportDelegate().pop();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

        final List<MultipleItemEntity> data = new SearchDatConverter().convert();
        final SearchAdapter adapter = new SearchAdapter(data);
        mRecyclerView.setAdapter(adapter);
        //分割线
        final DividerItemDecoration itemDecoration = new DividerItemDecoration();
        itemDecoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
            @Override
            public Divider getVerticalDivider(int position) {
                return null;
            }

            @Override
            public Divider getHorizontalDivider(int position) {
                return new Divider.Builder()
                        .size(2)
                        .color(Color.GRAY)
                        .margin(20, 20)
                        .build();
            }
        });
        mRecyclerView.addItemDecoration(itemDecoration);
    }
}
