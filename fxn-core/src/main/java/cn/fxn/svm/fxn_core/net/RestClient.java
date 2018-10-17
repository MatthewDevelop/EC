package cn.fxn.svm.fxn_core.net;

import android.content.Context;

import java.io.File;
import java.util.WeakHashMap;

import cn.fxn.svm.fxn_core.net.callback.IError;
import cn.fxn.svm.fxn_core.net.callback.IFailure;
import cn.fxn.svm.fxn_core.net.callback.IRequest;
import cn.fxn.svm.fxn_core.net.callback.ISuccess;
import cn.fxn.svm.fxn_core.net.callback.RequestCallbacks;
import cn.fxn.svm.fxn_core.ui.EcLoader;
import cn.fxn.svm.fxn_core.ui.LoaderStyle;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author:Matthew
 * @date:2018/10/15
 * @email:guocheng0816@163.com
 * @func:
 */
public class RestClient {
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final String URL;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    public RestClient(String url, WeakHashMap<String, Object> params,
                      IRequest request, ISuccess success,
                      IFailure failure, IError error,
                      RequestBody body, LoaderStyle loaderStyle, File file, Context context) {
        this.URL = url;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
        this.CONTEXT = context;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    private void request(HttpMethod method) {
        final RestService restService = RestCreator.getRestService();
        Call<String> call = null;
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {
            EcLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case GET:
                call = restService.get(URL, PARAMS);
                break;
            case POST:
                call = restService.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = restService.postRaw(URL, BODY);
                break;
            case PUT:
                call = restService.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = restService.putRaw(URL, BODY);
                break;
            case DELETE:
                call = restService.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody=RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body=MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call=restService.upload(URL, body);
                break;
            default:
                break;
        }
        if (call != null) {
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("PARAMS must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }


    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("PARAMS must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }


}
