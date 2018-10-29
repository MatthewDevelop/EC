package cn.fxn.svm.fxn_ec.main.cart;

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
 * @date:2018/10/29
 * @email:guocheng0816@163.com
 * @func:
 */
public class ShopCartDataConverter extends DataConvert {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String desc = data.getString("desc");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            final double price = data.getDouble("price");
            final int count = data.getInteger("count");
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ShopCartItemType.SHOP_CART_ITEM_TYPE)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    .setField(ShopCartFields.DESC, desc)
                    .setField(ShopCartFields.TITLE, title)
                    .setField(ShopCartFields.PRICE, price)
                    .setField(ShopCartFields.COUNT, count)
                    .build();
            dataList.add(entity);
        }
        return dataList;
    }

}
