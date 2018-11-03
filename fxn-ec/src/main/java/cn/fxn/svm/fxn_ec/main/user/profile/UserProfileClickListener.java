package cn.fxn.svm.fxn_ec.main.user.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import cn.fxn.svm.fxn_core.delegates.EcDelegate;
import cn.fxn.svm.fxn_core.util.callback.CallbackManager;
import cn.fxn.svm.fxn_core.util.callback.CallbackType;
import cn.fxn.svm.fxn_core.util.callback.IGlobalCallback;
import cn.fxn.svm.fxn_core.util.log.EcLogger;
import cn.fxn.svm.fxn_ec.R;
import cn.fxn.svm.fxn_ec.main.user.list.ListBean;
import cn.fxn.svm.fxn_ec.main.user.settings.NameDelegate;
import cn.fxn.svm.fxn_ui.ui.date.DateDialogUtil;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author:Matthew
 * @date:2018/11/2
 * @email:guocheng0816@163.com
 * @func:
 */
public class UserProfileClickListener extends SimpleClickListener {
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    final String[] GENDERS = new String[]{"男", "女", "保密"};
    private final EcDelegate DELEGATE;

    public UserProfileClickListener(EcDelegate delegate) {
        DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id) {
            case 1:
                //拍照或选择图片
                CallbackManager.getInstance().addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void excuteCallback(Uri args) {
                        EcLogger.d(args);
                        final CircleImageView imageView=view.findViewById(R.id.img_arrow_avatar);
                        //拿到文件后处理,上传，展示等操作
                        Glide.with(view).load(args).apply(OPTIONS).into(imageView);
                    }
                });
                DELEGATE.startCaremaWithCheck();
                break;
            case 2:
                DELEGATE.getSupportDelegate().start(bean.getDelegate());
                break;
            case 3:
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AppCompatTextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(GENDERS[which]);
                        dialog.cancel();
                    }
                });
                break;
            case 4:
                new DateDialogUtil()
                        .setIDateListener(new DateDialogUtil.IDateListener() {
                            @Override
                            public void onDateChange(String date) {
                                final AppCompatTextView textView = view.findViewById(R.id.tv_arrow_value);
                                textView.setText(date);
                            }
                        })
                        .showDialog(DELEGATE.getContext());
                break;
            default:
                break;
        }
    }

    private void getGenderDialog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(GENDERS, 0, listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
