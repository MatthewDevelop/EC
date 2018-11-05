package cn.fxn.svm.ui.wdget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import cn.fxn.svm.ui.R;

/**
 * @author:Matthew
 * @date:2018/11/5
 * @email:guocheng0816@163.com
 * @func:
 */
public class StartLayout extends LinearLayoutCompat implements View.OnClickListener {

    private static final CharSequence ICON_UN_SELECTED = "{fa-star-o}";
    private static final CharSequence ICON_SELECTED = "{fa-star}";
    private static final int START_TOTAL_COUNT = 5;
    private static final ArrayList<IconTextView> STARTS = new ArrayList<>();

    public StartLayout(Context context) {
        this(context, null);
    }

    public StartLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StartLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStarIcon();
    }

    private void initStarIcon() {
        for (int i = 0; i < START_TOTAL_COUNT; i++) {
            final IconTextView star = new IconTextView(getContext());
            star.setGravity(Gravity.CENTER);
            final LinearLayoutCompat.LayoutParams layoutParams =
                    new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            star.setLayoutParams(layoutParams);
            star.setText(ICON_UN_SELECTED);
            star.setTag(R.id.star_count, i);
            star.setTag(R.id.star_is_select, false);
            star.setOnClickListener(this);
            STARTS.add(star);
            this.addView(star);
        }
    }

    public int getStartCount(){
        int count=0;
        for (int i = 0; i < START_TOTAL_COUNT; i++) {
            final IconTextView star = STARTS.get(i);
            final boolean isSelect = (boolean) star.getTag(R.id.star_is_select);
            if (isSelect){
                count++;
            }
        }
        return count;
    }

    @Override
    public void onClick(View v) {
        final IconTextView star = (IconTextView) v;
        //获取是第几颗星星
        final int count = (int) star.getTag(R.id.star_count);
        //获取是第几颗星星
        final boolean isSelect = (boolean) star.getTag(R.id.star_is_select);

        if (!isSelect) {
            selectStar(count);
        } else {
            unSelectStar(count);
        }
    }

    private void selectStar(int count) {
        for (int i = 0; i <= count; i++) {
            final IconTextView star = STARTS.get(i);
            star.setText(ICON_SELECTED);
            star.setTextColor(Color.RED);
            star.setTag(R.id.star_is_select, true);
        }
    }

    private void unSelectStar(int count) {
        for (int i = 0; i < START_TOTAL_COUNT; i++) {
            if (i >= count) {
                final IconTextView star = STARTS.get(i);
                star.setText(ICON_UN_SELECTED);
                star.setTextColor(Color.GRAY);
                star.setTag(R.id.star_is_select, false);
            }
        }
    }
}
