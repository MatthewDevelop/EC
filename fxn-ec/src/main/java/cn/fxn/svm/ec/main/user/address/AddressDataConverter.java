package cn.fxn.svm.ec.main.user.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import cn.fxn.svm.ui.recycler.DataConverter;
import cn.fxn.svm.ui.recycler.MultipleFields;
import cn.fxn.svm.ui.recycler.MultipleItemEntity;

/**
 * @author:Matthew
 * @date:2018/11/3
 * @email:guocheng0816@163.com
 * @func:
 */
public class AddressDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> data = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject object = dataArray.getJSONObject(i);
            final int id = object.getInteger("id");
            final String name = object.getString("name");
            final String phone = object.getString("phone");
            final String address = object.getString("address");
            final boolean isDefault = object.getBoolean("default");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(AddressItemType.ITEM_ADDRESS)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.NAME, name)
                    .setField(MultipleFields.TAG, isDefault)
                    .setField(AddressItemFields.ADDRESS, address)
                    .setField(AddressItemFields.PHONE, phone)
                    .build();
            data.add(entity);
        }
        return data;
    }
}
