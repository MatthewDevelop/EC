package cn.fxn.svm.core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;
import java.io.InputStream;

import cn.fxn.svm.core.app.EC;
import cn.fxn.svm.core.net.callback.ISuccess;
import cn.fxn.svm.core.util.file.FileUtil;
import okhttp3.ResponseBody;

/**
 * @author:Matthew
 * @date:2018/10/17
 * @email:guocheng0816@163.com
 * @func:
 */
public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final ISuccess SUCCESS;

    public SaveFileTask(ISuccess success) {
        SUCCESS = success;
    }


    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream in = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        if (name == null) {
            return FileUtil.writeToDisk(in, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(in, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS!=null){
            SUCCESS.onSuccess(file.getPath());
        }
        autoInstallApk(file);
    }

    /**
     * 跳转安装apk
     * @param file
     */
    private void autoInstallApk(File file){
        if(FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install=new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            EC.getApplication().startActivity(install);
        }
    }
}
