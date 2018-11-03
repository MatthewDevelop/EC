package cn.fxn.svm.core.delegates.web.client;

import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.fxn.svm.core.app.ConfigKeys;
import cn.fxn.svm.core.app.EC;
import cn.fxn.svm.core.delegates.web.IPageLoadListener;
import cn.fxn.svm.core.delegates.web.WebDelegate;
import cn.fxn.svm.core.delegates.web.route.Router;
import cn.fxn.svm.core.ui.loader.EcLoader;
import cn.fxn.svm.core.util.log.EcLogger;
import cn.fxn.svm.core.util.storage.EcPreference;

/**
 * @author:Matthew
 * @date:2018/10/26
 * @email:guocheng0816@163.com
 * @func:
 */
public class WebViewClientImpl extends WebViewClient {
    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;

    public WebViewClientImpl(WebDelegate delegate) {
        DELEGATE = delegate;
    }

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        EcLogger.d("shouldOverrideUrlLoading", url);
        //所有链接的跳转，重定向全部由原生接管
        return Router.getInstance().handleUrl(DELEGATE, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        EcLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        asyncCookie();
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadFinish();
        }
        EC.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EcLoader.stopLoading();
            }
        }, 1000);
    }

    //获取浏览器cookie
    private void asyncCookie() {
        final CookieManager cookieManager = CookieManager.getInstance();
        /**
         * 此cookie和api请求的cookie不同，在网页中不可见
         */
        final String webHost = EC.getConfiguration(ConfigKeys.WEB_HOST);
        if (webHost != null) {
            if (cookieManager.hasCookies()) {
                final String cookieStr = cookieManager.getCookie(webHost);
                if (cookieStr != null || !cookieStr.equals("")) {
                    EcPreference.addCustomAppProfile("cookie", cookieStr);
                }
            }
        }

    }
}
