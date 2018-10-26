package cn.fxn.svm.fxn_core.delegates.web.event;

import cn.fxn.svm.fxn_core.util.log.EcLogger;

/**
 * @author:Matthew
 * @date:2018/10/26
 * @email:guocheng0816@163.com
 * @func:
 */
public class UndefinedEvent extends Event{


    @Override
    public String execute(String params) {
        EcLogger.e("UndefinedEvent", params);
        return null;
    }
}
