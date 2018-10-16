package cn.fxn.svm.fxn_core.net;

import android.content.Context;

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

    private String mUrl;
    private static final WeakHashMap<String, Object> PARAMS=RestCreator.getParams();
    private IRequest mIRequest;
    private ISuccess mISuccess;
    private IFailure mIFailure;
    private IError mIError;
    private RequestBody mBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle;

    RestClientBuilder(){

    }

    public final RestClientBuilder url(String url){
        mUrl=url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String,Object> params){
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key,Object value){
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder raws(String raws){
        mBody=RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raws);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest){
        mIRequest=iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess){
        mISuccess=iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure){
        mIFailure=iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError){
        mIError=iError;
        return this;
    }

    public final RestClientBuilder loader(Context context,LoaderStyle loaderStyle){
        mContext=context;
        mLoaderStyle=loaderStyle;
        return this;
    }

    public final RestClientBuilder loader(Context context){
        mContext=context;
        mLoaderStyle=LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClient build(){
        return new RestClient(mUrl, PARAMS, mIRequest,
                mISuccess, mIFailure, mIError, mBody, mLoaderStyle, mContext);
    }
}
