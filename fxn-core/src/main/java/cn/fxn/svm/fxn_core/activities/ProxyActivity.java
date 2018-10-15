package cn.fxn.svm.fxn_core.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.widget.FrameLayout;

import cn.fxn.svm.fxn_core.R;
import cn.fxn.svm.fxn_core.delegates.EcDelegate;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author:Matthew
 * @date:2018/10/13
 * @email:guocheng0816@163.com
 * @func:
 */
public abstract class ProxyActivity extends SupportActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
        final FrameLayout container = new FrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    public abstract EcDelegate setRootDelegate();
}
