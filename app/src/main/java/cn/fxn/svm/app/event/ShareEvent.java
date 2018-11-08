package cn.fxn.svm.app.event;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.fxn.svm.core.delegates.web.event.Event;
import cn.fxn.svm.core.util.log.EcLogger;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * @author:Matthew
 * @date:2018/11/8
 * @email:guocheng0816@163.com
 * @func:
 */
public class ShareEvent extends Event {
    @Override
    public String execute(String params) {
        EcLogger.d(params);
        final JSONObject object = JSON.parseObject(params).getJSONObject("params");
        final String title = object.getString("title");
        final String url = object.getString("url");
        final String imageUrl = object.getString("imageUrl");
        final String text = object.getString("text");

        final OnekeyShare onekeyShare = new OnekeyShare();
        //关闭
        onekeyShare.disableSSOWhenAuthorize();
        onekeyShare.setTitle(title);
        onekeyShare.setImageUrl(imageUrl);
        onekeyShare.setText(text);
        onekeyShare.setUrl(url);
        onekeyShare.show(getContext());
        return null;
    }
}
