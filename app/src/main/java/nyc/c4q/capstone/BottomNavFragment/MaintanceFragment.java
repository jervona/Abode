package nyc.c4q.capstone.BottomNavFragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import nyc.c4q.capstone.DataBaseTesting;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.maintenance.NewRequestFragment;
import nyc.c4q.capstone.maintenance.SubmittedAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MaintanceFragment extends Fragment {

    View rootView;
    TextView pendingView;
    TextView scheduledView;
    TextView completedView;
    RecyclerView pendingRV;
    RecyclerView scheduledRV;
    RecyclerView completedRV;
    SubmittedAdapter adapter;
    LinearLayoutManager pendingLayoutManager;
    LinearLayoutManager scheduledLayoutManager;
    LinearLayoutManager completedLayoutManager;
    private ScrollView maintenanceScroll;

    public MaintanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_maintenance, container, false);
//        ButterKnife.bind(this,rootView);
        pendingView = rootView.findViewById(R.id.pending_view);
        scheduledView = rootView.findViewById(R.id.scheduled_view);
        completedView = rootView.findViewById(R.id.completed_view);
        maintenanceScroll = rootView.findViewById(R.id.maintenance_scroll);
        pendingRV = rootView.findViewById(R.id.pending_tix_rv);
        scheduledRV = rootView.findViewById(R.id.scheduled_tix_rv);
        completedRV = rootView.findViewById(R.id.completed_tix_rv);
        pendingLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        scheduledLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        completedLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataBaseTesting db = DataBaseTesting.getInstance(FirebaseDatabase.getInstance());

        //this is where we have recycle view would go

        adapter = new SubmittedAdapter(db.getMessages());
        pendingRV.setLayoutManager(pendingLayoutManager);
//        pendingRV.setAdapter(adapter);
        scheduledRV.setLayoutManager(scheduledLayoutManager);
//        scheduledRV.setAdapter(adapter);
        completedRV.setLayoutManager(completedLayoutManager);
//        completedRV.setAdapter(adapter);

//        Log.e("Size of list", db.getMessages().size() + "");

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
                Toast.makeText(getActivity(), "New Repair Request ", Toast.LENGTH_SHORT).show();
//lunch framentment to added new notes
                NewRequestFragment requestFragment = new NewRequestFragment();
                FragmentManager fragManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragManager.beginTransaction();
                fragmentTransaction.replace(R.id.maintenance_frag, requestFragment).addToBackStack("maintenance frag");
                fragmentTransaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
