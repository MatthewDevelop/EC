package cn.fxn.svm.fxn_core.delegates.web.client;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.fxn.svm.fxn_core.app.EC;
import cn.fxn.svm.fxn_core.delegates.web.IPageLoadListener;
import cn.fxn.svm.fxn_core.delegates.web.WebDelegate;
import cn.fxn.svm.fxn_core.delegates.web.route.Router;
import cn.fxn.svm.fxn_core.ui.loader.EcLoader;
import cn.fxn.svm.fxn_core.util.log.EcLogger;

/**
 * @author:Matthew
 * @date:2018/10/26
 * @email:guocheng0816@163.com
 * @func:
 */
public class WebViewClientImpl extends WebViewClient {
    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;

    public void setPageLoadListener(IPageLoadListener listener){
        this.mIPageLoadListener=listener;
    }

    public WebViewClientImpl(WebDelegate delegate) {
        DELEGATE = delegate;
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
        if(mIPageLoadListener!=null){
            mIPageLoadListener.onLoadStart();
        }
        EcLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if(mIPageLoadListener!=null){
            mIPageLoadListener.onLoadFinish();
        }
        EC.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EcLoader.stopLoading();
            }
        }, 1000);
    }
}
