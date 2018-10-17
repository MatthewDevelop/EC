package cn.fxn.svm.fxn_core.net;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import cn.fxn.svm.fxn_core.app.ConfigKeys;
import cn.fxn.svm.fxn_core.app.EC;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;

/**
 * @author:Matthew
 * @date:2018/10/15
 * @email:guocheng0816@163.com
 * @func:
 */
public class RestCreator {

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    private static final class RetrofitHolder {
        public static final String BASE_URL = EC.getConfiguration(ConfigKeys.API_HOST.name());
        public static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class OKHttpHolder {
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final int TIME_OUT = 60;
        private static final ArrayList<Interceptor> INTERCEPTORS = EC.getConfiguration(ConfigKeys.INTERCEPTOR);
        public static final OkHttpClient OK_HTTP_CLIENT = addInterceptors()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        private static final OkHttpClient.Builder addInterceptors() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }
    }

    private static final class RestServiceHolder {
        public static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    private static final class ParamsHolder {
        public static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }
}
