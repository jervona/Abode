package nyc.c4q.capstone;


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

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.tenant_maintenance.SubmittedAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandlordMaintenance extends Fragment {
    View rootView;
    @BindView(R.id.landlord_maintenance_rv)
    RecyclerView landlordRV;
    LandlordMainAdapter adapter;
    LinearLayoutManager layoutManager;
    FirebaseDatabase data = FirebaseDatabase.getInstance();
    TenantDataBaseHelper db;
    List<Tickets> ticketsList;
    String TAG = "LandlordMaintenance";


    public LandlordMaintenance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_landlord_maintenance, container, false);
        ButterKnife.bind(this, rootView);

        layoutManager = new LinearLayoutManager(getContext());
        landlordRV.setLayoutManager(layoutManager);

        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = TenantDataBaseHelper.getInstance(FirebaseDatabase.getInstance());
        adapter = new LandlordMainAdapter(db.getTicketsList());
        landlordRV.setAdapter(adapter);

    }

    public void updateList() {
        data.getReference().child("Maintenance").child(String.valueOf(db.getUser().getBuilding_id())).child(db.getUser().getAPT()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Tickets>> t = new GenericTypeIndicator<List<Tickets>>() {
                };
                ticketsList = dataSnapshot.getValue(t);
                if (ticketsList != null) {
                    Collections.reverse(ticketsList);
                    adapter.updateTicketListItems(ticketsList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getDetails());
            }
        });
    }
}
