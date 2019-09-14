package android.dev.com.karan.android.kisancodingchallenge.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.dev.com.karan.android.kisancodingchallenge.javaviews.ContactsFragment;
import android.dev.com.karan.android.kisancodingchallenge.javaviews.MessagesFragment;
import android.support.v13.app.FragmentPagerAdapter;


public class MainContactsViewAdapter extends FragmentPagerAdapter {
    private String[] titles = {"Contacts", "Messages"};

    public MainContactsViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = ContactsFragment.getInstance();
                break;
            case 1:
                fragment = MessagesFragment.getInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
