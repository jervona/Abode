package nyc.c4q.capstone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.BottomNavFragment.DashBoardFragment;
import nyc.c4q.capstone.BottomNavFragment.DocsFragment;
import nyc.c4q.capstone.BottomNavFragment.MaintanceFragment;
import nyc.c4q.capstone.BottomNavFragment.PaymentFragment;

/**
 * Created by jervon.arnoldd on 3/18/18.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public void initTenanetBottomNav(){
        mFragmentList.add(new DashBoardFragment());
        mFragmentList.add(new PaymentFragment());
        mFragmentList.add(new MaintanceFragment());
        mFragmentList.add(new DocsFragment());
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
