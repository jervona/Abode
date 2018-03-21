package nyc.c4q.capstone.BottomNavFragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.maintenance.MaintenancePagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MaintanceFragment extends Fragment {

    View rootView;
    @BindView(R.id.maintenance_tabs)TabLayout maintenanceTabs;
    @BindView(R.id.maintenance_pager) ViewPager viewPager;


    public MaintanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_maintance, container, false);
        ButterKnife.bind(this,rootView);
        setupTabs();

        return rootView;
    }

    private void setupTabs() {
        MaintenancePagerAdapter maintenanceAdapter = new MaintenancePagerAdapter(getFragmentManager());
        maintenanceAdapter.addFragment(new SubmittedFragment(), "Submitted");
        maintenanceAdapter.addFragment(new PendingFragment(), "Pending");
        maintenanceAdapter.addFragment(new CompletedFragment(), "Completed");
        viewPager.setAdapter(maintenanceAdapter);
        maintenanceTabs.setupWithViewPager(viewPager);
    }

}
