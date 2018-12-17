package asiantech.internship.summer.filestorage;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    private String listTab[] = {"Share Preference", "Store", "Database"};
    private SharePreferenceFragment mSharePreferenceFragment;
    private StoreFragment mStoreFragment;
    private DatabaseFragment mDatabaseFragment;

    TabLayoutAdapter(FragmentManager fm) {
        super(fm);
        mSharePreferenceFragment = new SharePreferenceFragment();
        mStoreFragment = new StoreFragment();
        mDatabaseFragment = new DatabaseFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return mSharePreferenceFragment;
        } else if (position == 1) {
            return mStoreFragment;
        } else if (position == 2) {
            return mDatabaseFragment;
        }
        return null;
     }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}