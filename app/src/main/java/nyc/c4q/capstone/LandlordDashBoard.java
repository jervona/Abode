package nyc.c4q.capstone;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.signupactivites.SignInActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandlordDashBoard extends Fragment {
    View rootView;
    TenantDataBaseHelper db;
    FirebaseDatabase data = FirebaseDatabase.getInstance();
    private HashMap<String,List<Tickets>> ticketsList;

    public LandlordDashBoard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_landlord_dash_board, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = TenantDataBaseHelper.getInstance(FirebaseDatabase.getInstance());
        updateList();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dash_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(MainActivity.googleApiClient);
                MainActivity.mUsername = MainActivity.ANONYMOUS;
                startActivity(new Intent(getActivity(), SignInActivity.class));
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateList() {
       final List<Tickets> hello =new ArrayList<>();
        data.getReference().child("Maintenance").child("1521310103").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String,List<Tickets>>> t = new GenericTypeIndicator<HashMap<String,List<Tickets>>>() {
                };
                ticketsList = dataSnapshot.getValue(t);
                if (ticketsList != null) {
                    for (String key:ticketsList.keySet()){
                       hello.addAll(ticketsList.get(key));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("LandlordDashBoard", "onCancelled: " + databaseError.getDetails());
            }
        });
    }
}
