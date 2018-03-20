package nyc.c4q.capstone.BottomNavFragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nyc.c4q.capstone.R;
import nyc.c4q.capstone.maintenance_tabs.CompletedFragment;
import nyc.c4q.capstone.maintenance_tabs.MaintenancePagerAdapter;
import nyc.c4q.capstone.maintenance_tabs.PendingFragment;
import nyc.c4q.capstone.maintenance_tabs.SubmittedFragment;


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
        MaintenancePagerAdapter maintenanceAdapter = new MaintenancePagerAdapter(getFragmentManager());

        maintenanceAdapter.addFragment(new SubmittedFragment(), "Submitted");
        maintenanceAdapter.addFragment(new PendingFragment(), "Pending");
        maintenanceAdapter.addFragment(new CompletedFragment(), "Completed");
        viewPager.setAdapter(maintenanceAdapter);
        maintenanceTabs.setupWithViewPager(viewPager);

        return rootView;
    }

}
