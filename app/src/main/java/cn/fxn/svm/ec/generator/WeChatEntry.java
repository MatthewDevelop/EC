package cn.fxn.svm.ec.generator;

import cn.fxn.svm.annotations.EntryGenerator;
import cn.fxn.svm.fxn_core.wechat.template.WXEntryTemplate;

/**
 * @author:Matthew
 * @date:2018/10/22
 * @email:guocheng0816@163.com
 * @func:
 */
@EntryGenerator(
        packageName = "cn.fxn.svm.ec",
        entryTemplate = WXEntryTemplate.class)
public interface WeChatEntry {
}
