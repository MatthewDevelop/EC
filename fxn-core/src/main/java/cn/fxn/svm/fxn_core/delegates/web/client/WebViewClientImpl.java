package cn.fxn.svm.fxn_core.delegates.web.client;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.fxn.svm.fxn_core.delegates.web.WebDelegate;
import cn.fxn.svm.fxn_core.delegates.web.route.Router;
import cn.fxn.svm.fxn_core.util.log.EcLogger;
import retrofit2.http.Url;

/**
 * @author:Matthew
 * @date:2018/10/26
 * @email:guocheng0816@163.com
 * @func:
 */
public class WebViewClientImpl extends WebViewClient {
    private final WebDelegate DELEGATE;

    public WebViewClientImpl(WebDelegate delegate) {
        DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        EcLogger.d("shouldOverrideUrlLoading", url);
        //所有链接的跳转，重定向全部由原生接管
        return Router.getInstance().handleUrl(DELEGATE, url);
    }
}
