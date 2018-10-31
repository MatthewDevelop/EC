package cn.fxn.svm.fxn_ui.ui.recycler;

import com.google.auto.value.AutoValue;

/**
 * @author:Matthew
 * @date:2018/10/24
 * @email:guocheng0816@163.com
 */
@AutoValue
public abstract class RgbValue {

    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red, int green, int blue) {
        return new AutoValue_RgbValue(red, green, blue);
    }
}
