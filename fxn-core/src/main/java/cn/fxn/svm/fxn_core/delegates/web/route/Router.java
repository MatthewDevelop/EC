package cn.fxn.svm.fxn_core.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import cn.fxn.svm.fxn_core.delegates.EcDelegate;
import cn.fxn.svm.fxn_core.delegates.PermissionCheckerDelegate;
import cn.fxn.svm.fxn_core.delegates.web.WebDelegate;
import cn.fxn.svm.fxn_core.delegates.web.WebDelegateImpl;
import cn.fxn.svm.fxn_core.util.log.EcLogger;

/**
 * @author:Matthew
 * @date:2018/10/26
 * @email:guocheng0816@163.com
 * @func:
 */
public class Router {

    private Router() {

    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    public final boolean handleUrl(WebDelegate delegate, String url) {
        //如果时电话协议
        if (url.contains("tel:")) {
            callPhone(delegate.getContext(), url);
        }
        final EcDelegate topDelegate = delegate.getTopDelegate();
        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        topDelegate.start(webDelegate);
        return true;
    }

    private void callPhone(Context context, String uri) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(uri);
        intent.setData(data);
        ContextCompat.startActivity(context, intent, null);
    }

    public void loadPage(WebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), url);
    }

    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    private void loadWebPage(WebView webView, String url) {
        EcLogger.d(url);
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("WebView is null");
        }
    }

    /**
     * 加载本地页面
     *
     * @param webView
     * @param url
     */
    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    /**
     * 线程安全的惰性单例
     */
    private static class Holder {
        private static final Router INSTANCE = new Router();
    }
}
