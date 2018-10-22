package cn.fxn.svm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author:Matthew
 * @date:2018/10/22
 * @email:guocheng0816@163.com
 * @func:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EntryGenerator {
    String packageName();

    Class<?> entryTemplate();
}
