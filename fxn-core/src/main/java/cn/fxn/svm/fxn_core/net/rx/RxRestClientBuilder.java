package cn.fxn.svm.fxn_core.net.rx;

import android.content.Context;

import java.io.File;
import java.util.WeakHashMap;

import cn.fxn.svm.fxn_core.net.RestCreator;
import cn.fxn.svm.fxn_core.ui.loader.LoaderStyle;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author:Matthew
 * @date:2018/10/16
 * @email:guocheng0816@163.com
 * @func:
 */
public class RxRestClientBuilder {

    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private String mUrl = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;

    RxRestClientBuilder() {

    }

    public final RxRestClientBuilder url(String url) {
        mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String filePath) {
        mFile = new File(filePath);
        return this;
    }


    public final RxRestClientBuilder raws(String raws) {
        mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raws);
        return this;
    }


    public final RxRestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
        mContext = context;
        mLoaderStyle = loaderStyle;
        return this;
    }

    public final RxRestClientBuilder loader(Context context) {
        mContext = context;
        mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS, mBody, mLoaderStyle, mFile, mContext);
    }
}
