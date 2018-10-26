package cn.fxn.svm.fxn_core.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.fxn.svm.fxn_core.delegates.web.chromeclient.WebChromeClientImpl;
import cn.fxn.svm.fxn_core.delegates.web.client.WebViewClientImpl;
import cn.fxn.svm.fxn_core.delegates.web.route.RouteKeys;
import cn.fxn.svm.fxn_core.delegates.web.route.Router;
import cn.fxn.svm.fxn_core.util.log.EcLogger;

/**
 * @author:Matthew
 * @date:2018/10/26
 * @email:guocheng0816@163.com
 * @func:
 */
public class WebDelegateImpl extends WebDelegate implements IWebViewInitializer {

    public static WebDelegateImpl create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }


    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }


    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        EcLogger.d(getUrl());
        if (getUrl() != null) {
            //用原生的方式模拟Web跳转
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }
}
