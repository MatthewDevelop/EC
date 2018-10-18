package cn.fxn.svm.fxn_ec.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;

import cn.fxn.svm.fxn_core.delegates.EcDelegate;
import cn.fxn.svm.fxn_core.ui.launcher.LauncherHolderCreator;
import cn.fxn.svm.fxn_core.util.storage.EcPreference;
import cn.fxn.svm.fxn_ec.R;

/**
 * @author:Matthew
 * @date:2018/10/18
 * @email:guocheng0816@163.com
 * @func:
 */
public class LauncherScrollDelegate extends EcDelegate implements OnItemClickListener {

    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ConvenientBanner<Integer> mConvenientBanner = null;

    private void initBanner() {
        INTEGERS.clear();
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner.setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        if(position==INTEGERS.size()-1){
            EcPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCH_APP.name(), true);
            //检查登陆状态
        }
    }
}
