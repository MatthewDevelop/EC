package cn.fxn.svm.fxn_core.delegates.web;


import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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

    @JavascriptInterface
    public String event(String params) {
        final String action = JSON.parseObject(params).getString("action");
        return null;
    }
}
