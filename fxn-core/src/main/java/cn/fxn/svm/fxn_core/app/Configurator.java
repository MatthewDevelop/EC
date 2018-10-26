package cn.fxn.svm.fxn_core.app;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import cn.fxn.svm.fxn_core.delegates.web.event.Event;
import cn.fxn.svm.fxn_core.delegates.web.event.EventManager;
import okhttp3.Interceptor;

/**
 * @author:Matthew
 * @date:2018/10/12
 * @email:guocheng0816@163.com
 * @func:初始化配置
 */
public class Configurator {

    private static final HashMap<Object, Object> EC_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final Handler HANDLER=new Handler();

    private Configurator() {
        EC_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), false);
    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getEcConfigs() {
        return EC_CONFIGS;
    }

    public final void configure() {
        initIcons();
        EC_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), true);
        EC_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    /**
     * 初始化图标库
     */
    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                Iconify.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withApiHost(String host) {
        EC_CONFIGS.put(ConfigKeys.API_HOST.name(), host);
        return this;
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        Iconify.with(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        EC_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        EC_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withJavaScriptInterface(@NonNull String name){
        EC_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
        return this;
    }

    public final Configurator withWebEvent(@NonNull String name, @NonNull Event event){
        final EventManager eventManager=EventManager.getInstance();
        eventManager.addEvent(name, event);
        return this;
    }

    public final Configurator withWeChatAppId(String appId) {
        EC_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {
        EC_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        EC_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = EC_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + "IS NULL");
        }
        return (T) value;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) EC_CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Config not ready,call configure!");
        }
    }

    //单例
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

}
