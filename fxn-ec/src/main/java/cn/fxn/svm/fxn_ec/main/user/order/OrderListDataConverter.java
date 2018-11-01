package cn.fxn.svm.fxn_ec.main.user.order;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import cn.fxn.svm.fxn_ui.ui.recycler.DataConvert;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleFields;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleItemEntity;

/**
 * @author:Matthew
 * @date:2018/11/1
 * @email:guocheng0816@163.com
 * @func:
 */
public class OrderListDataConverter extends DataConvert {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> data = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject object = dataArray.getJSONObject(i);
            final String thumb = object.getString("thumb");
            final int id = object.getInteger("id");
            final String title = object.getString("title");
            final double price = object.getDouble("price");
            final String time = object.getString("time");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(OrderListItemType.ITEM_ORDER_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TITLE, title)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    .setField(OrderItemFields.PRICE, price)
                    .setField(OrderItemFields.TIME, time)
                    .build();
            data.add(entity);
        }
        return data;
    }
}
