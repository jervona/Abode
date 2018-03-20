package nyc.c4q.capstone.maintenance_tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c4q on 3/18/18.
 */

public class MaintenancePagerAdapter extends FragmentPagerAdapter{

    private final List<Fragment> fragList = new ArrayList<>();
    private final List<String> tabTitles = new ArrayList<>();
    public MaintenancePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragList.add(fragment);
        tabTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

}
