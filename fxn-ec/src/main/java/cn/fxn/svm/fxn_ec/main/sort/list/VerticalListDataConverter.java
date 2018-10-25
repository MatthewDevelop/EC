package cn.fxn.svm.fxn_ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import cn.fxn.svm.fxn_core.ui.recycler.DataConvert;
import cn.fxn.svm.fxn_core.ui.recycler.ItemType;
import cn.fxn.svm.fxn_core.ui.recycler.MultipleFields;
import cn.fxn.svm.fxn_core.ui.recycler.MultipleItemEntity;

/**
 * @author:Matthew
 * @date:2018/10/25
 * @email:guocheng0816@163.com
 * @func:分类列表数据转换
 */
public class VerticalListDataConverter extends DataConvert {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON
                .parseObject(getJsonData())
                .getJSONObject("data")
                .getJSONArray("list");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject object = dataArray.getJSONObject(i);
            final int id = object.getInteger("id");
            final String name = object.getString("name");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, name)
                    //标记item的点击状态
                    .setField(MultipleFields.TAG, false)
                    .build();
            dataList.add(entity);
            //设置第一个被选中
            dataList.get(0).setField(MultipleFields.TAG, true);
        }
        return dataList;
    }
}
