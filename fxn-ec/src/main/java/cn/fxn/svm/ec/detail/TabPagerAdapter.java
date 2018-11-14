package cn.fxn.svm.ec.detail;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.util.ArrayList;

/**
 * @author:Matthew
 * @date:2018/11/9
 * @email:guocheng0816@163.com
 * @func:
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {
    //FragmentStatePagerAdapter不保存页面数据

    private final ArrayList<String> TAB_TITLES = new ArrayList<>();
    private final ArrayList<ArrayList<String>> PICTURES = new ArrayList<>();

    public TabPagerAdapter(FragmentManager fm, JSONObject data) {
        super(fm);
        final JSONArray tabs = data.getJSONArray("tabs");
        final int size = tabs.size();
        for (int i = 0; i < size; i++) {
            final JSONObject eachTab = tabs.getJSONObject(i);
            final String title = eachTab.getString("name");
            final JSONArray picUrls = eachTab.getJSONArray("pictures");
            final ArrayList<String> eachTabUrls = new ArrayList<>();
            final int urlSize = picUrls.size();
            for (int j = 0; j < urlSize; j++) {
                final String url = picUrls.getString(j);
                eachTabUrls.add(url);
            }
            TAB_TITLES.add(title);
            PICTURES.add(eachTabUrls);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return ImageDelegate.create(PICTURES.get(position));
    }

    @Override
    public int getCount() {
        return PICTURES.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES.get(position);
    }
}
