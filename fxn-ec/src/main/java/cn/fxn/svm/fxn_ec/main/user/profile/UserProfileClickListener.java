package cn.fxn.svm.fxn_ec.main.user.profile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import cn.fxn.svm.fxn_core.delegates.EcDelegate;
import cn.fxn.svm.fxn_ec.R;
import cn.fxn.svm.fxn_ec.main.user.list.ListBean;
import cn.fxn.svm.fxn_ec.main.user.settings.NameDelegate;
import cn.fxn.svm.fxn_ui.ui.date.DateDialogUtil;

/**
 * @author:Matthew
 * @date:2018/11/2
 * @email:guocheng0816@163.com
 * @func:
 */
public class UserProfileClickListener extends SimpleClickListener {

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
