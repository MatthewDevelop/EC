package cn.fxn.svm.ec.main.user.list;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.fxn.svm.core.delegates.EcDelegate;

/**
 * @author:Matthew
 * @date:2018/11/1
 * @email:guocheng0816@163.com
 * @func:
 */
public class ListBean implements MultiItemEntity {

    private int mItemType=0;
    private String mImageUrl=null;
    private String mText=null;
    private String mValue=null;
    private int mId=0;
    private EcDelegate mDelegate=null;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener=null;

    private ListBean(int itemType, String imageUrl, String text, String value, int id, EcDelegate delegate, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        mItemType = itemType;
        mImageUrl = imageUrl;
        mText = text;
        mValue = value;
        mId = id;
        mDelegate = delegate;
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getText() {
        if (mText==null){
            return "";
        }
        return mText;
    }

    public String getValue() {
        if (mValue==null){
            return "";
        }
        return mValue;
    }

    public int getId() {
        return mId;
    }

    public EcDelegate getDelegate() {
        return mDelegate;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final class Builder{
        private int itemType=0;
        private String imageUrl=null;
        private String text=null;
        private String value=null;
        private int id=0;
        private EcDelegate delegate=null;
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener=null;

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setDelegate(EcDelegate delegate) {
            this.delegate = delegate;
            return this;
        }

        public Builder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            this.onCheckedChangeListener = onCheckedChangeListener;
            return this;
        }

        public ListBean build(){
            return new ListBean(itemType, imageUrl, text, value, id, delegate, onCheckedChangeListener);
        }
    }
}
