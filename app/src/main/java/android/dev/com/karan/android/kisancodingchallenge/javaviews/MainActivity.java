package android.dev.com.karan.android.kisancodingchallenge.javaviews;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.dev.com.karan.android.kisancodingchallenge.R;
import android.dev.com.karan.android.kisancodingchallenge.adapters.MainContactsViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tlMain)
    TabLayout tlMain;
    @BindView(R.id.viewPagerMain)
    ViewPager viewPagerMain;
    @BindView(R.id.toolbarMain)
    Toolbar toolbarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbarSetup();
        setupViewPager();
    }

    private void toolbarSetup() {
        toolbarMain.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbarMain);
    }

    private void setupViewPager() {
        MainContactsViewAdapter contactsViewAdapter = new MainContactsViewAdapter(getFragmentManager());
        viewPagerMain.setAdapter(contactsViewAdapter);
        tlMain.setupWithViewPager(viewPagerMain);
    }
}
