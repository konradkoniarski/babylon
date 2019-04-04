package co.uk.babylon.ui.main;

import android.os.Bundle;

import co.uk.babylon.R;
import co.uk.babylon.base.BaseActivity;
import co.uk.babylon.ui.list.ListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.screenContainer, new ListFragment())
                    .commit();
        }
    }
}
