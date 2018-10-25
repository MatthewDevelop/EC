package cn.fxn.svm.fxn_ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fxn.svm.fxn_core.util.log.EcLogger;

/**
 * @author:Matthew
 * @date:2018/10/25
 * @email:guocheng0816@163.com
 */
public class SectionDataConverter {

    final List<SectionBean> convert(String json) {
        final List<SectionBean> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String title = data.getString("section");
            //添加title
            final SectionBean sectionTitleBean = new SectionBean(true, title);
            sectionTitleBean.setmId(id);
            sectionTitleBean.setIsMore(true);
            dataList.add(sectionTitleBean);

            final JSONArray goods=data.getJSONArray("goods");
            //商品内容循环
            final int goodsSize=goods.size();
            for (int i1 = 0; i1 < goodsSize; i1++) {
                final JSONObject contentItem=goods.getJSONObject(i1);
                final int goodsId=contentItem.getInteger("goods_id");
                final String goodsName=contentItem.getString("goods_name");
                final String goodsThumb=contentItem.getString("goods_thumb");
                //获取内容
                final SectionContentItemEntity itemEntity=new SectionContentItemEntity();
                itemEntity.setGoodsId(goodsId);
                itemEntity.setGoodsName(goodsName);
                itemEntity.setGoodsThumb(goodsThumb);

                //添加内容
                dataList.add(new SectionBean(itemEntity));
            }
            //商品内容循环结束
        }
        //section内容循环结束
        EcLogger.d(dataList.toString());
        return dataList;
    }

}
