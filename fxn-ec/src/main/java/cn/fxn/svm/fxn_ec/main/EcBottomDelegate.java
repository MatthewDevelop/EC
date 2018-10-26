package cn.fxn.svm.fxn_ec.main;

import android.graphics.Color;

import java.util.LinkedHashMap;

import cn.fxn.svm.fxn_core.delegates.bottom.BaseBottomDelegate;
import cn.fxn.svm.fxn_core.delegates.bottom.BottomItemDelegate;
import cn.fxn.svm.fxn_core.delegates.bottom.BottomTabBean;
import cn.fxn.svm.fxn_core.delegates.bottom.ItemBuilder;
import cn.fxn.svm.fxn_ec.main.cart.CartDelegate;
import cn.fxn.svm.fxn_ec.main.discover.DiscoverDelegate;
import cn.fxn.svm.fxn_ec.main.index.IndexDelegate;
import cn.fxn.svm.fxn_ec.main.sort.SortDelegate;
import cn.fxn.svm.fxn_ec.main.user.UserDelegate;

/**
 * @author:Matthew
 * @date:2018/10/23
 * @email:guocheng0816@163.com
 * @func:
 */
public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public int getIndexDelegate() {
        return 0;
    }

    @Override
    public int getClickedColor() {
        return Color.parseColor("#ffff8800");
    }

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> getItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items=new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}", "发现"), new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new CartDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new UserDelegate());
        return builder.addItems(items).build();
    }
}
