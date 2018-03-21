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


    public MaintanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.maintenance_repairs_itemview, container, false);
        ButterKnife.bind(this,rootView);

        return rootView;
    }



}
