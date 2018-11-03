package cn.fxn.svm.core.util.callback;

import java.util.WeakHashMap;

/**
 * @author:Matthew
 * @date:2018/11/3
 * @email:guocheng0816@163.com
 * @func:
 */
public class CallbackManager {

    private static final WeakHashMap<Object, IGlobalCallback> CALLBACKS = new WeakHashMap<>();

    private CallbackManager() {

    }

    public static CallbackManager getInstance() {
        return Holder.INSTANCE;
    }

    public CallbackManager addCallback(Object tag, IGlobalCallback globalCallback) {
        CALLBACKS.put(tag, globalCallback);
        return this;
    }

    public IGlobalCallback getCallback(Object tag) {
        return CALLBACKS.get(tag);
    }

    private static class Holder {
        private static final CallbackManager INSTANCE = new CallbackManager();
    }

}
