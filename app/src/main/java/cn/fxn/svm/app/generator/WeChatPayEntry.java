package cn.fxn.svm.app.generator;

import cn.fxn.svm.annotations.PayEntryGenerator;
import cn.fxn.svm.core.wechat.template.WXPayEntryTemplate;

/**
 * @author:Matthew
 * @date:2018/10/22
 * @email:guocheng0816@163.com
 * @func:
 */
@PayEntryGenerator(
        packageName = "cn.fxn.svm.ec",
        payEntryTemplate = WXPayEntryTemplate.class)
public interface WeChatPayEntry {
}
