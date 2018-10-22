package cn.fxn.svm.ec.generator;

import cn.fxn.svm.annotations.PayEntryGenerator;
import cn.fxn.svm.fxn_core.wechat.template.WXPayEntryTemplate;

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
