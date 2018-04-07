package nyc.c4q.capstone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.TenantBottomNavFragment.DashBoardFragment;
import nyc.c4q.capstone.TenantBottomNavFragment.DocsFragment;
import nyc.c4q.capstone.TenantBottomNavFragment.MaintanceFragment;
import nyc.c4q.capstone.TenantBottomNavFragment.PaymentFragment;

/**
 * Created by jervon.arnoldd on 3/18/18.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public void initBottomNav(String key){
        switch (key){
            case "Tenant":
                mFragmentList.add(new DashBoardFragment());
                mFragmentList.add(new PaymentFragment());
                mFragmentList.add(new MaintanceFragment());
                break;
            case "PM":
                mFragmentList.add(new DashBoardFragment());
                mFragmentList.add(new PaymentFragment());
                mFragmentList.add(new MaintanceFragment());
                mFragmentList.add(new DocsFragment());
                break;
        }
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
