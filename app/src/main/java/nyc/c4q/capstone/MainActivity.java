package nyc.c4q.capstone;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AHBottomNavigation bottom;
    private ArrayList<AHBottomNavigationItem> items = new ArrayList<>();

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.container);
        setBottomNav();




    }


    public void setBottomNav() {
        setupViewPager(viewPager);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Stuff", R.drawable.ic_location_searching_black_24dp);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("List", R.drawable.blank_heart);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Favorites", R.drawable.headline);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        bottom.addItems(items);
        bottom.setCurrentItem(1);
        bottom.setDefaultBackgroundColor(Color.LTGRAY);
        bottom.setAccentColor(Color.parseColor("#52c7b8"));
//        bottom.setColoredModeColors(Color.LTGRAY,Color.parseColor("#52c7b8"));
//        bottom.setAccentColor(Color.parseColor("#52c7b8"));
        bottom.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager.setCurrentItem(position);
                bottom.setCurrentItem(position, wasSelected);
                return false;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private class SectionsPageAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public SectionsPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
}
