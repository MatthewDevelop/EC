package cn.fxn.svm.fxn_core.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author:Matthew
 * @date:2018/10/12
 * @email:guocheng0816@163.com
 * @func:初始化配置
 */
public class Configurator {

    private static final HashMap<String, Object> EC_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    private Configurator() {
        EC_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<String, Object> getEcConfigs() {
        return EC_CONFIGS;
    }

    public final void configure() {
        initIcons();
        EC_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    public final Configurator withApiHost(String host) {
        EC_CONFIGS.put(ConfigType.API_HOST.name(), host);
        return this;
    }

    public final Configurator withIcon(IconFontDescriptor descriptor){
        Iconify.with(descriptor);
        return this;
    }

    /**
     * 初始化图标库
     */
    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i=1;i<ICONS.size();i++){
                Iconify.with(ICONS.get(i));
            }
        }
    }

    final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) EC_CONFIGS.get(key);
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) EC_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Config not ready,call configure!");
        }
    }
    //单例
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

}
