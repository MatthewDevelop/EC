package cn.fxn.svm.ui.date;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author:Matthew
 * @date:2018/11/2
 * @email:guocheng0816@163.com
 * @func:
 */
public class DateDialogUtil {

    private IDateListener mIDateListener = null;

    public DateDialogUtil setIDateListener(IDateListener datelistener) {
        mIDateListener = datelistener;
        return this;
    }

    public void showDialog(Context context) {
        final LinearLayoutCompat ll = new LinearLayoutCompat(context);
        final DatePicker datePicker = new DatePicker(context);
        final LinearLayoutCompat.LayoutParams params =
                new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.MATCH_PARENT);
        datePicker.setLayoutParams(params);
        datePicker.init(1991, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
                final String date=format.format(calendar.getTime());
                if(mIDateListener!=null){
                    mIDateListener.onDateChange(date);
                }
            }
        });

        ll.addView(datePicker);

        new AlertDialog.Builder(context)
                .setTitle("选择日期")
                .setView(ll)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public interface IDateListener {
        void onDateChange(String date);
    }
}
