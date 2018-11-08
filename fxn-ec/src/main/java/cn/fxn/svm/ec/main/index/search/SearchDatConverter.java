package cn.fxn.svm.ec.main.index.search;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;

import cn.fxn.svm.core.util.storage.EcPreference;
import cn.fxn.svm.ui.recycler.DataConverter;
import cn.fxn.svm.ui.recycler.MultipleFields;
import cn.fxn.svm.ui.recycler.MultipleItemEntity;

/**
 * @author:Matthew
 * @date:2018/11/8
 * @email:guocheng0816@163.com
 * @func:
 */
public class SearchDatConverter extends DataConverter {

    public static final String TAG_SEARCH_HISTORY="search_history";

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> data=new ArrayList<>();
        final String jsonStr=EcPreference.getCustomAppProfile(TAG_SEARCH_HISTORY);
        if(!"".equals(jsonStr)){
            final JSONArray dataArray=JSON.parseArray(jsonStr);
            final int size=dataArray.size();
            for (int i = 0; i < size; i++) {
                final String historyItem= (String) dataArray.get(i);
                MultipleItemEntity entity=MultipleItemEntity.builder()
                        .setItemType(SearchItemType.ITEM_SEARCH)
                        .setField(MultipleFields.TEXT, historyItem)
                        .build();
                data.add(entity);
            }
        }
        return data;
    }
}
