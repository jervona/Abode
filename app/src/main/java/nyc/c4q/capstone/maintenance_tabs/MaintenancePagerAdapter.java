package nyc.c4q.capstone.maintenance_tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by c4q on 3/18/18.
 */

public class MaintenancePagerAdapter extends FragmentPagerAdapter{

    int numOfTabs;

    public MaintenancePagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PendingFragment penTab = new PendingFragment();
                return penTab;
            case 1:
                SubmittedFragment subTab = new SubmittedFragment();
                return subTab;
            case 2:
                CompletedFragment compFrag = new CompletedFragment();
                return compFrag;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
