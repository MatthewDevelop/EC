package cn.fxn.svm.ec.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.ec.R;
import cn.fxn.svm.ec.R2;
import cn.fxn.svm.ui.recycler.ItemType;
import cn.fxn.svm.ui.recycler.MultipleFields;
import cn.fxn.svm.ui.recycler.MultipleItemEntity;

/**
 * @author:Matthew
 * @date:2018/11/9
 * @email:guocheng0816@163.com
 * @func:
 */
public class ImageDelegate extends EcDelegate {


    private static final String ARG_PICTURES = "ARG_PICTURES";

    @BindView(R2.id.rv_image_container)
    RecyclerView mRecyclerView = null;


    public static ImageDelegate create(@NonNull ArrayList<String> imageUrls) {
        final Bundle args = new Bundle();
        args.putStringArrayList(ARG_PICTURES, imageUrls);
        final ImageDelegate imageDelegate = new ImageDelegate();
        imageDelegate.setArguments(args);
        return imageDelegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_image;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        initImages();
    }

    private void initImages() {
        final ArrayList<MultipleItemEntity> entities=new ArrayList<>();
        if (getArguments() != null) {
            final ArrayList<String> pictures=getArguments().getStringArrayList(ARG_PICTURES);
            if (pictures!=null){
                final int size=pictures.size();
                for (int i = 0; i < size; i++) {
                    final String imageUrl=pictures.get(i);
                    final MultipleItemEntity entity=MultipleItemEntity.builder()
                            .setItemType(ItemType.SINGLE_BIG_IMAGE)
                            .setField(MultipleFields.IMAGE_URL, imageUrl)
                            .build();
                    entities.add(entity);
                }
            }
        }
        final ImageRecyclerAdapter adapter=new ImageRecyclerAdapter(entities);
        mRecyclerView.setAdapter(adapter);
    }


}
