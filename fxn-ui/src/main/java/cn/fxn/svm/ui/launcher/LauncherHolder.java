package cn.fxn.svm.ui.launcher;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * @author:Matthew
 * @date:2018/10/18
 * @email:guocheng0816@163.com
 * @func:
 */
public class LauncherHolder extends Holder<Integer> {
//    private static final String TAG = "LauncherHolder";

    private AppCompatImageView mImageView;

    public LauncherHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
//        Log.e(TAG, "initView: "+(itemView==null) );
        mImageView = (AppCompatImageView) itemView;
//        Log.e(TAG, "initView: "+(mImageView==null) );
    }

    @Override
    public void updateUI(Integer data) {
        mImageView.setBackgroundResource(data);
    }
}
