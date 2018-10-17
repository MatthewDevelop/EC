package cn.fxn.svm.ec;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;

import cn.fxn.svm.fxn_core.app.EC;
import cn.fxn.svm.fxn_core.net.interceptors.DebugInterceptor;
import cn.fxn.svm.fxn_ec.icon.FontEcModule;

/**
 * @author:Matthew
 * @date:2018/10/12
 * @email:guocheng0816@163.com
 * @func:
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EC.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://news.baidu.com/")
                .withInterceptor(new DebugInterceptor("guonei", R.raw.test))
                .configure();
    }
}
