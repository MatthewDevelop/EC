package cn.fxn.svm.fxn_ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * @author:Matthew
 * @date:2018/10/12
 * @email:guocheng0816@163.com
 * @func:定义图标字符
 */
public enum EcIcon implements Icon {
    font_ali_pay('\ue634'),
    font_wechat_pay('\ue612'),
    font_scan('\ue674');

    private char character;

    EcIcon(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
