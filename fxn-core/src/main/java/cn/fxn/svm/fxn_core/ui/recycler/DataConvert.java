package cn.fxn.svm.fxn_core.ui.recycler;

import java.util.ArrayList;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public abstract class DataConvert {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();

    private String mJsonData = null;

    public String getJsonData() {
        if (mJsonData==null||mJsonData.isEmpty()){
            throw new NullPointerException("data is null !!");
        }
        return mJsonData;
    }

    public DataConvert setJsonData(String jsonData) {
        mJsonData = jsonData;
        return this;
    }

    /**
     * 将json数据转换成实体
     * @return
     */
    public abstract ArrayList<MultipleItemEntity> convert();
}
