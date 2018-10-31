package cn.fxn.svm.fxn_ec.main.sort.list;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.util.List;

import cn.fxn.svm.fxn_core.delegates.EcDelegate;
import cn.fxn.svm.fxn_ui.ui.recycler.ItemType;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleFields;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleItemEntity;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleRecyclerAdapter;
import cn.fxn.svm.fxn_ui.ui.recycler.MultipleViewHolder;
import cn.fxn.svm.fxn_ec.R;
import cn.fxn.svm.fxn_ec.main.sort.SortDelegate;
import cn.fxn.svm.fxn_ec.main.sort.content.ContentDelegate;
import me.yokeyword.fragmentation.SupportHelper;

/**
 * @author:Matthew
 * @date:2018/10/25
 * @email:guocheng0816@163.com
 * @func:
 */
public class SortRecyclerAdapter extends MultipleRecyclerAdapter {

    private final SortDelegate DELEGATE;
    private int mPrePosition = 0;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    protected SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;
        //添加垂直菜单布局
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ItemType.VERTICAL_MENU_LIST:
                final String text = entity.getField(MultipleFields.TEXT);
                final boolean isClicked = entity.getField(MultipleFields.TAG);
                final AppCompatTextView name = holder.getView(R.id.tv_vertical_item_name);
                final View line = holder.getView(R.id.view_line);
                final View itemView = holder.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentPosition = holder.getAdapterPosition();
                        if (mPrePosition != currentPosition) {
                            //还原上一个
                            getData().get(mPrePosition).setField(MultipleFields.TAG, false);
                            notifyItemChanged(mPrePosition);

                            //更新选中的item
                            entity.setField(MultipleFields.TAG, true);
                            notifyItemChanged(currentPosition);
                            mPrePosition = currentPosition;

                            //获取内容id
                            final int contentId=getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });
                if (!isClicked) {
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                } else {
                    line.setVisibility(View.VISIBLE);
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                }
                holder.setText(R.id.tv_vertical_item_name, text);
                break;
            default:
                break;
        }
    }

    private void showContent(int contentId){
        final ContentDelegate delegate=ContentDelegate.newInstance(contentId);
        switchContent(delegate);
    }

    private void switchContent(ContentDelegate delegate){
        final EcDelegate contentDelegate=
                SupportHelper.findFragment(DELEGATE.getChildFragmentManager(), ContentDelegate.class);
        if(contentDelegate!=null){
            contentDelegate.getSupportDelegate().replaceFragment(delegate, false);
        }
    }
}