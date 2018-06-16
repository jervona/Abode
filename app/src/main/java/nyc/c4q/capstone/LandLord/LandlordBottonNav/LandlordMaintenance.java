package nyc.c4q.capstone.LandLord.LandlordBottonNav;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.LandLord.maintenance_recycleview.LandlordMainAdapter;
import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.Notification;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.Tickets;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandlordMaintenance extends Fragment {
    View rootView;
    @BindView(R.id.landlord_maintenance_rv)
    RecyclerView recyclerView;

    LandlordMainAdapter adapter;
    LinearLayoutManager layoutManager;
    FirebaseDatabase data = FirebaseDatabase.getInstance();
    TenantDataBaseHelper db;
    private HashMap<String,List<Tickets>> ticketsList;
    String TAG = "LandlordMaintenance";


    public LandlordMaintenance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_landlord_maintenance, container, false);
        ButterKnife.bind(this, rootView);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = TenantDataBaseHelper.getInstance(FirebaseDatabase.getInstance());
        adapter = new LandlordMainAdapter(db.getTicketsList());
        recyclerView.setAdapter(adapter);
        updateList();
    }

    public void updateList() {
        data.getReference().child("Maintenance").child("1521310103").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Tickets> listOfTickets = new ArrayList<>();
                GenericTypeIndicator<HashMap<String,List<Tickets>>> t = new GenericTypeIndicator<HashMap<String,List<Tickets>>>() {
                };
                ticketsList = dataSnapshot.getValue(t);
                if (ticketsList != null) {
                    for (String key:ticketsList.keySet()){
                         listOfTickets.addAll(ticketsList.get(key));
                    }
                    checkingForPending(listOfTickets);
                    Collections.reverse(listOfTickets);
                    adapter.updateTicketListItems(listOfTickets);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getDetails());
            }
        });
    }

    private void checkingForPending(List<Tickets> tickets) {
     int num=0;
        for (Tickets tix:tickets) {
            if (tix.getStatus().equals("Pending")){
                num++;
            }
            Notification notification =Notification.getInstance();
            notification.setNum(num);
            notification.setPath("Maintenance");
            assert ((MainActivity)getActivity()) != null;
            ((MainActivity)getActivity()).setBottomNotification();
        }
    }
}
