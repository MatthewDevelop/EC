package cn.fxn.svm.fxn_ec.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import cn.fxn.svm.fxn_ui.ui.recycler.DataConvert;
import cn.fxn.svm.fxn_ui.ui.recycler.ItemType;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleEntityBuilder;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleFields;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleItemEntity;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class IndexDataConvert extends DataConvert {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> entities=new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);

            final String imageUrl = data.getString("imageUrl");
            final String text = data.getString("text");
            final int spanSize = data.getInteger("spanSize");
            final int id = data.getInteger("goodsId");
            final JSONArray banners = data.getJSONArray("banners");

            final ArrayList<String> bannerImages = new ArrayList<>();
            int type = 0;

            if (imageUrl == null && text != null) {
                type = ItemType.TEXT;
            } else if (imageUrl != null && text == null) {
                type = ItemType.IMAGE;
            } else if (imageUrl != null) {
                type = ItemType.TEXT_IMAGE;
            }else if(banners!=null){
                type=ItemType.BANNER;
                final int bannerSize=banners.size();
                for (int j = 0; j < bannerSize; j++) {
                    final String url=banners.getString(j);
                    bannerImages.add(url);
                }
            }
            final MultipleItemEntity entity=MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.SPAN_SIZE, spanSize)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, text)
                    .setField(MultipleFields.IMAGE_URL, imageUrl)
                    .setField(MultipleFields.BANNERS, bannerImages)
                    .build();

            entities.add(entity);

        }
        return entities;
    }

}
