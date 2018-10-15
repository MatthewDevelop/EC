package cn.fxn.svm.fxn_core.app;

import android.content.Context;

import java.util.HashMap;

/**
 * @author:Matthew
 * @date:2018/10/12
 * @email:guocheng0816@163.com
 * @func:初始化工具类
 */
public final class EC {
    /**
     * 初始化Context
     * @param context
     * @return
     */
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context);
        return Configurator.getInstance();
    }

    public static HashMap<String,Object> getConfigurations(){
        return Configurator.getInstance().getEcConfigs();
    }

    /**
     * 获取context
     * @return
     */
    public static Context getApplication(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
