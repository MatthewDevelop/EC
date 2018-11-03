package cn.fxn.svm.core.net.download;

import android.os.AsyncTask;

import java.util.WeakHashMap;

import cn.fxn.svm.core.net.RestCreator;
import cn.fxn.svm.core.net.callback.IError;
import cn.fxn.svm.core.net.callback.IFailure;
import cn.fxn.svm.core.net.callback.IRequest;
import cn.fxn.svm.core.net.callback.ISuccess;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author:Matthew
 * @date:2018/10/17
 * @email:guocheng0816@163.com
 * @func:
 */
public class DownloadHandler {

    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final String URL;
    //下载到路径
    private final String DOWNLOAD_DIR;

    public DownloadHandler(String url, String downloadDir, String extension,
                           String name, IRequest request, ISuccess success,
                           IFailure failure, IError error) {
        this.URL = url;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    //文件后缀
    private final String EXTENSION;
    //下载的文件名
    private final String NAME;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public final void handleDownload(){
        if(REQUEST!=null){
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            final ResponseBody body = response.body();
                            final SaveFileTask saveFileTask = new SaveFileTask(SUCCESS);
                            saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, body, NAME);
                            if (saveFileTask.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        }else {
                            if(ERROR!=null){
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE!=null){
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
