package nyc.c4q.capstone.BottomNavFragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nyc.c4q.capstone.R;
import nyc.c4q.capstone.maintenance_tabs.MaintenancePagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MaintanceFragment extends Fragment {

    View rootView;
    TabLayout maintenanceTabs;
    ViewPager viewPager;


    public MaintanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_maintance, container, false);
        maintenanceTabs = rootView.findViewById(R.id.maintenance_tabs);
        viewPager = rootView.findViewById(R.id.maintenance_pager);

        maintenanceTabs.addTab(maintenanceTabs.newTab().setText("Pending"));
        maintenanceTabs.addTab(maintenanceTabs.newTab().setText("Submitted"));
        maintenanceTabs.addTab(maintenanceTabs.newTab().setText("Completed"));
        maintenanceTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        MaintenancePagerAdapter maintenanceAdapter = new MaintenancePagerAdapter(getFragmentManager(), maintenanceTabs.getTabCount());
        viewPager.setAdapter(maintenanceAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(maintenanceTabs));

        return rootView;
    }

}
