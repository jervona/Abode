package nyc.c4q.capstone.LandLord.LandlordBottonNav;


import android.content.Intent;
import android.graphics.Color;
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

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.TenantPaymentHistoryModel;
import nyc.c4q.capstone.signupactivites.SignInActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandlordDashBoard extends Fragment {
    View rootView;

    @BindView(R.id.dashpie)
    PieChart mPieChart;

    FirebaseDatabase data = FirebaseDatabase.getInstance();
    HashMap<String, List<TenantPaymentHistoryModel>> payments;

    public LandlordDashBoard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_landlord_dash_board, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, rootView);
        mPieChart.addPieSlice(new PieModel("Rent Paid", 70, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Pending Rent", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Late", 5, Color.parseColor("#CDA67F")));
        mPieChart.startAnimation();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                startActivity(new Intent(getActivity(), SignInActivity.class));
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateList() throws NullPointerException {
        data.getReference().child("Rent").child("1521310103").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, List<TenantPaymentHistoryModel>>> t = new GenericTypeIndicator<HashMap<String, List<TenantPaymentHistoryModel>>>() {
                };
                final List<TenantPaymentHistoryModel> landlordPayList = new ArrayList<>();
                payments = dataSnapshot.getValue(t);
                if (payments != null) {
                    Log.e("Size", payments.size() + "");
                    for (String key : payments.keySet()) {
                        landlordPayList.addAll(payments.get(key));
                        Log.e("DashBoard", payments.get(key).size() + "");
                    }
                    Log.e("Payments", landlordPayList.get(0).getTenant_apt());
                    int size = landlordPayList.size();
                    updatePieChart(size);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void updatePieChart(int size) {

        int percent = size * 25;

        mPieChart.clearChart();
        mPieChart.addPieSlice(new PieModel("Rent Paid", percent, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Pending Rent", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Late", 5, Color.parseColor("#CDA67F")));
        mPieChart.startAnimation();
    }
}
