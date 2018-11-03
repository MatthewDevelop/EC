package cn.fxn.svm.app;

import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


import cn.fxn.svm.core.app.EC;
import cn.fxn.svm.app.event.TestEvent;
import cn.fxn.svm.core.net.interceptors.DebugInterceptor;
import cn.fxn.svm.ec.database.DatabaseManager;
import cn.fxn.svm.ec.icon.FontEcModule;

/**
 * @author:Matthew
 * @date:2018/10/12
 * @email:guocheng0816@163.com
 * @func:
 */
public class ExampleApp extends MultiDexApplication {

    public static final String HOME_URL="http://192.168.0.101:8080/";
    public static final String WORK_URL="http://192.168.137.38:8080/";


    @Override
    public void onCreate() {
        super.onCreate();
        EC.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
//                .withApiHost(HOME_URL)
                .withApiHost(WORK_URL)
                .withWebHost("https://baidu.com/")
                .withInterceptor(new DebugInterceptor("hello", R.raw.test))
                .withWeChatAppId("wxfcdcecd9df8e0faa")
                .withWeChatAppSecret("a0560f75335b06e3ebea70f29ff219bf")
                .withJavaScriptInterface("ec")
                .withWebEvent("test", new TestEvent())
                .configure();
        Utils.init(EC.getApplication());
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
