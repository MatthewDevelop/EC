package cn.fxn.svm.core.wechat;

import android.app.Activity;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.fxn.svm.core.app.ConfigKeys;
import cn.fxn.svm.core.app.EC;
import cn.fxn.svm.core.wechat.callbacks.IWeChatSignInCallback;

/**
 * @author:Matthew
 * @date:2018/10/22
 * @email:guocheng0816@163.com
 * @func:
 */
public class EcWeChat {
    static final String APP_ID = EC.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
    static final String APP_SECRET = EC.getConfiguration(ConfigKeys.WE_CHAT_APP_SECRET);
    private final IWXAPI WXAPI;
    private IWeChatSignInCallback mSignInCallback=null;

    private EcWeChat() {
        final Activity activity=EC.getConfiguration(ConfigKeys.ACTIVITY);
        WXAPI=WXAPIFactory.createWXAPI(activity, APP_ID,true);
        WXAPI.registerApp(APP_ID);
    }

    public static EcWeChat getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final EcWeChat INSTANCE = new EcWeChat();
    }

    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    public final void signIn(){
        final SendAuth.Req req=new SendAuth.Req();
        req.scope="snsapi_userinfo";
        req.state="random_state";
        WXAPI.sendReq(req);
    }


    public EcWeChat onSignInSuccess(IWeChatSignInCallback weChatSignInCallback){
        this.mSignInCallback=weChatSignInCallback;
        return this;
    }

    public IWeChatSignInCallback getSignInCallback() {
        return mSignInCallback;
    }
}
