package nyc.c4q.capstone.LandLord.LandlordBottonNav;


import android.graphics.Color;
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

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.LandLord.payment_recycleview.Landlord_Pay_Adapter;
import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.Notification;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.TenantPaymentHistoryModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandlordPay_Fragment extends Fragment {


    View rootView;
    Landlord_Pay_Adapter adapter;
    @BindView(R.id.landlord_payment_rv) RecyclerView recyclerView;
    @BindView(R.id.piechart) PieChart mPieChart;
    FirebaseDatabase data = FirebaseDatabase.getInstance();
    HashMap<String, List<TenantPaymentHistoryModel>> payments;


    public LandlordPay_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_landlord_pay_, container, false);
        ButterKnife.bind(this, rootView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        mPieChart.addPieSlice(new PieModel("Rent Paid", 70, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Pending Rent", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Late", 5, Color.parseColor("#CDA67F")));
        mPieChart.startAnimation();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new Landlord_Pay_Adapter();
        recyclerView.setAdapter(adapter);
        updateList();
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
                        Log.e("Helloe there", payments.get(key).size() + "");
                    }
                    Log.e("Payments", landlordPayList.get(0).getTenant_apt());
                    Collections.reverse(landlordPayList);
                    adapter.updateTicketListItems(landlordPayList);
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
        mPieChart.clearChart();
        int percent = size * 25;
        mPieChart.addPieSlice(new PieModel("Rent Paid", percent, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Pending Rent", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Late", 5, Color.parseColor("#CDA67F")));
        mPieChart.startAnimation();
    }
}


