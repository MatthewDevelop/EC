package cn.fxn.svm.fxn_core.delegates.web;


import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.fxn.svm.fxn_core.delegates.web.event.Event;
import cn.fxn.svm.fxn_core.delegates.web.event.EventManager;
import retrofit2.http.DELETE;

/**
 * @author:Matthew
 * @date:2018/10/26
 * @email:guocheng0816@163.com
 * @func:
 */
public class EcWebInterface {
    private final WebDelegate DELEGATE;

    private EcWebInterface(WebDelegate delegate) {
        DELEGATE = delegate;
    }

    public static EcWebInterface create(WebDelegate delegate) {
        return new EcWebInterface(delegate);
    }

    @SuppressWarnings("unused")
    @JavascriptInterface
    public String event(String params) {
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance().createEvent(action);
        if (event != null) {
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
