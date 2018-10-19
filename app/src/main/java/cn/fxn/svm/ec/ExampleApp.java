package cn.fxn.svm.ec;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


import cn.fxn.svm.fxn_core.app.EC;
import cn.fxn.svm.fxn_core.net.interceptors.DebugInterceptor;
import cn.fxn.svm.fxn_ec.database.DatabaseManager;
import cn.fxn.svm.fxn_ec.icon.FontEcModule;

/**
 * @author:Matthew
 * @date:2018/10/12
 * @email:guocheng0816@163.com
 * @func:
 */
public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EC.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://192.168.137.38:8080/")
                .withInterceptor(new DebugInterceptor("hello", R.raw.test))
                .configure();
        Logger.addLogAdapter(new AndroidLogAdapter());
        initStetho();
        DatabaseManager.getInstance().init(this);
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
