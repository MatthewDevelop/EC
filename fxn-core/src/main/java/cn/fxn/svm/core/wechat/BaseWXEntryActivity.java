package cn.fxn.svm.core.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import cn.fxn.svm.core.net.RestClient;
import cn.fxn.svm.core.net.callback.IError;
import cn.fxn.svm.core.net.callback.IFailure;
import cn.fxn.svm.core.net.callback.ISuccess;
import cn.fxn.svm.core.util.log.EcLogger;

/**
 * @author:Matthew
 * @date:2018/10/22
 * @email:guocheng0816@163.com
 * @func:
 */
public abstract class BaseWXEntryActivity extends BaseWxActivity {
    private static final String TAG = "BaseWXEntryActivity";
    /**
     * 用户登录后回调
     */
    protected abstract void onSignSuccess(String userInfo);

    /**
     * 微信发送请求到第三方应用后的回调
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {

    }

    /**
     * 第三方应用请求发送到微信后的回调
     *
     * @param baseResp
     */
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp) baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl
                .append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(EcWeChat.APP_ID)
                .append("&secret=")
                .append(EcWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        EcLogger.d(TAG,authUrl.toString());
    }

    private void getAuth(String authUrl){
        RestClient.builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject authObj=JSON.parseObject(response);
                        final String accessToken=authObj.getString("access_token");
                        final String openId=authObj.getString("openid");

                        final StringBuilder userInfoUrl=new StringBuilder();
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");

                        EcLogger.d("userInfoUrl", userInfoUrl.toString());
                        getUserInfo(userInfoUrl.toString());
                    }
                })
                .build()
                .get();
    }

    private void getUserInfo(String userInfoUrl) {
        RestClient.builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onSignSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }

}
