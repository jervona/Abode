package nyc.c4q.capstone.BottomNavFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.DataBaseTesting;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.SignInActivity;
import nyc.c4q.capstone.maintenance.MaintenancePagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MaintanceFragment extends Fragment {

    View rootView;
    @BindView(R.id.maintenance_tabs)
    TabLayout maintenanceTabs;
    @BindView(R.id.maintenance_pager)
    ViewPager viewPager;


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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataBaseTesting db=DataBaseTesting.getInstance(FirebaseDatabase.getInstance());
//this is where we have recycle view would go
        Log.e("Size of list",db.getMessages().size()+"");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Toast.makeText(getActivity(), "Adding new Notes", Toast.LENGTH_SHORT).show();
//lunch framentment to added new notes
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
