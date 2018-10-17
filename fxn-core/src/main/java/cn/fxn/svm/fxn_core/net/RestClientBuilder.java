package cn.fxn.svm.fxn_core.net;

import android.content.Context;

import java.io.File;
import java.util.WeakHashMap;

import cn.fxn.svm.fxn_core.net.callback.IError;
import cn.fxn.svm.fxn_core.net.callback.IFailure;
import cn.fxn.svm.fxn_core.net.callback.IRequest;
import cn.fxn.svm.fxn_core.net.callback.ISuccess;
import cn.fxn.svm.fxn_core.ui.LoaderStyle;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author:Matthew
 * @date:2018/10/16
 * @email:guocheng0816@163.com
 * @func:
 */
public class RestClientBuilder {

    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private String mUrl = null;
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;
    private String mExtension = null;
    private String mDownloadDir = null;
    private String mName = null;

    RestClientBuilder() {

    }

    public final RestClientBuilder url(String url) {
        mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file) {
        mFile = file;
        return this;
    }

    public final RestClientBuilder file(String filePath) {
        mFile = new File(filePath);
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        mExtension = extension;
        return this;
    }

    public final RestClientBuilder name(String name) {
        mName=name;
        return this;
    }


    public final RestClientBuilder raws(String raws) {
        mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raws);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        mIError = iError;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
        mContext = context;
        mLoaderStyle = loaderStyle;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        mContext = context;
        mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mDownloadDir, mExtension, mName, mIRequest,
                mISuccess, mIFailure, mIError, mBody, mLoaderStyle, mFile, mContext);
    }
}
