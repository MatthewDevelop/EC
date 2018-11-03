package cn.fxn.svm.app.generator;

import cn.fxn.svm.annotations.AppRegisterGenerator;
import cn.fxn.svm.core.wechat.template.AppRegisterTemplate;

/**
 * @author:Matthew
 * @date:2018/10/22
 * @email:guocheng0816@163.com
 * @func:
 */
@AppRegisterGenerator(
        packageName = "cn.fxn.svm.ec",
        registerTemplate = AppRegisterTemplate.class)
public interface AppRegister {
}
