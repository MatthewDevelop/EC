package cn.fxn.svm.ui.recycler;

import java.util.ArrayList;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public abstract class DataConverter {

    private String mJsonData = null;

    public String getJsonData() {
        if (mJsonData==null||mJsonData.isEmpty()){
            throw new NullPointerException("data is null !!");
        }
        return mJsonData;
    }

    public DataConverter setJsonData(String jsonData) {
        mJsonData = jsonData;
        return this;
    }

    /**
     * 将json数据转换成实体
     * @return
     */
    public abstract ArrayList<MultipleItemEntity> convert();
}
