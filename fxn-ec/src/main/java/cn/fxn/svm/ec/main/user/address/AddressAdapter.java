package cn.fxn.svm.ec.main.user.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.util.List;

import cn.fxn.svm.core.net.RestClient;
import cn.fxn.svm.core.net.callback.ISuccess;
import cn.fxn.svm.ec.R;
import cn.fxn.svm.ui.recycler.MultipleFields;
import cn.fxn.svm.ui.recycler.MultipleItemEntity;
import cn.fxn.svm.ui.recycler.MultipleRecyclerAdapter;
import cn.fxn.svm.ui.recycler.MultipleViewHolder;

/**
 * @author:Matthew
 * @date:2018/11/3
 * @email:guocheng0816@163.com
 * @func:
 */
public class AddressAdapter extends MultipleRecyclerAdapter {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    protected AddressAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(AddressItemType.ITEM_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (entity.getItemType()) {
            case AddressItemType.ITEM_ADDRESS:
                final String name=entity.getField(MultipleFields.NAME);
                final String phone=entity.getField(AddressItemFields.PHONE);
                final String address=entity.getField(AddressItemFields.ADDRESS);
                final boolean isDefault=entity.getField(MultipleFields.TAG);
                final int id=entity.getField(MultipleFields.ID);

                final AppCompatTextView nameText = holder.getView(R.id.tv_address_name);
                final AppCompatTextView phoneText = holder.getView(R.id.tv_address_phone);
                final AppCompatTextView addressText = holder.getView(R.id.tv_address_address);
                final AppCompatTextView deleteTextView = holder.getView(R.id.tv_address_delete);

                nameText.setText(name);
                phoneText.setText(phone);
                addressText.setText(address);

                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RestClient.builder()
                                //删除地址的接口
                                .url("address.json")
                                .params("id", entity.getField(MultipleFields.ID))
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        remove(holder.getAdapterPosition());
                                    }
                                })
                                .build()
                                .get();
                    }
                });

                break;
            default:
                break;
        }
    }
}
