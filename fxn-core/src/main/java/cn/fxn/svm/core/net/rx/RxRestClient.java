package cn.fxn.svm.core.net.rx;

import android.content.Context;

import java.io.File;
import java.util.WeakHashMap;

import cn.fxn.svm.core.net.HttpMethod;
import cn.fxn.svm.core.net.RestCreator;
import cn.fxn.svm.core.ui.loader.EcLoader;
import cn.fxn.svm.core.ui.loader.LoaderStyle;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author:Matthew
 * @date:2018/10/15
 * @email:guocheng0816@163.com
 * @func:
 */
public class RxRestClient {
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final String URL;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    RxRestClient(String url, WeakHashMap<String, Object> params,
                 RequestBody body, LoaderStyle loaderStyle, File file, Context context) {
        this.URL = url;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
        this.CONTEXT = context;
        PARAMS.putAll(params);
        this.BODY = body;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestService rxRestService = RestCreator.getRxRestService();
        Observable<String> observable = null;

        if (LOADER_STYLE != null) {
            EcLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case GET:
                observable = rxRestService.get(URL, PARAMS);
                break;
            case POST:
                observable = rxRestService.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = rxRestService.postRaw(URL, BODY);
                break;
            case PUT:
                observable = rxRestService.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = rxRestService.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = rxRestService.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = rxRestService.upload(URL, body);
                break;
            default:
                break;
        }
        return observable;
    }


    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("PARAMS must be null!");
            }
            return request(HttpMethod.POST_RAW);
        }
    }


    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("PARAMS must be null!");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download() {
        return RestCreator.getRxRestService().download(URL, PARAMS);
    }
}
