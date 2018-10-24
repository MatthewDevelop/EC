package cn.fxn.svm.fxn_core.ui.recycler;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/**
 * @author:Matthew
 * @date:2018/10/24
 * @email:guocheng0816@163.com
 * @func:
 */
public class DividerLookupImpl implements DividerItemDecoration.DividerLookup {

    private final int color;
    private final int size;

    public DividerLookupImpl(int color, int size) {
        this.color = color;
        this.size = size;
    }

    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder()
                .color(color)
                .size(size)
                .build();
    }

    @Override
    public Divider getHorizontalDivider(int position) {
        return new Divider.Builder()
                .color(color)
                .size(size)
                .build();
    }
}
