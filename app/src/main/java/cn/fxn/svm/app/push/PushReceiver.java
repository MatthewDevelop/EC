package cn.fxn.svm.app.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;


import com.alibaba.fastjson.JSONObject;

import java.util.Set;

import cn.fxn.svm.app.ExampleActivity;
import cn.fxn.svm.core.util.log.EcLogger;
import cn.jpush.android.api.JPushInterface;

/**
 * @author:Matthew
 * @date:2018/11/3
 * @email:guocheng0816@163.com
 * @func:推送广播处理
 */
public class PushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        EcLogger.e("PushReceiver","received");
        final Bundle bundle = intent.getExtras();
        final Set<String> keys = bundle.keySet();
        final JSONObject json = new JSONObject();
        for (String key : keys) {
            final Object value = bundle.get(key);
            json.put(key, value);
        }
        EcLogger.e("PushReceiver",json.toJSONString());

        final String pushAction = intent.getAction();

        if (pushAction.equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
            //处理收到的消息
            onReceivedMessage(bundle);
        } else if (pushAction.equals(JPushInterface.ACTION_NOTIFICATION_OPENED)) {
            //打开相应的通知
            onOpenNotification(context, bundle);
        }
    }

    private void onReceivedMessage(Bundle bundle) {
        final String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        final String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        final int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        final String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
    }

    private void onOpenNotification(Context context, Bundle bundle) {
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final Bundle newBundle = new Bundle();
        newBundle.putString("extra", extra);
        Intent intent = new Intent(context, ExampleActivity.class);
        intent.putExtras(newBundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ContextCompat.startActivity(context, intent, null);
    }
}
