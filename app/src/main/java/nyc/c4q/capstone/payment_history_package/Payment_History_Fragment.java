package nyc.c4q.capstone.payment_history_package;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.R;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class Payment_History_Fragment extends Fragment {

    View rootView;
    RecyclerView recyclerView;

    public Payment_History_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_payment__history_, container, false);
        recyclerView = rootView.findViewById(R.id.payment_history_rv);
        List<PaymentHistoryModel> paymentModelList = new ArrayList<>();

        paymentModelList.add(new PaymentHistoryModel("June","$600"));
        paymentModelList.add(new PaymentHistoryModel("Dec","$700"));
        paymentModelList.add(new PaymentHistoryModel("Sept","$5700"));
        paymentModelList.add(new PaymentHistoryModel("May","$5700"));
        paymentModelList.add(new PaymentHistoryModel("Jan","$505350"));
        paymentModelList.add(new PaymentHistoryModel("April","$5035350"));
        paymentModelList.add(new PaymentHistoryModel("Feb","$503530"));
        paymentModelList.add(new PaymentHistoryModel("Nov","$50350"));
        paymentModelList.add(new PaymentHistoryModel("Dec","$50320"));
        paymentModelList.add(new PaymentHistoryModel("May","$500"));
        paymentModelList.add(new PaymentHistoryModel("Feb","$50230"));
        paymentModelList.add(new PaymentHistoryModel("March","$50230"));
        paymentModelList.add(new PaymentHistoryModel("Jan","$500"));
        paymentModelList.add(new PaymentHistoryModel("Idk","$32500"));
        paymentModelList.add(new PaymentHistoryModel("Idk","$500"));
        paymentModelList.add(new PaymentHistoryModel("Hello","$23500"));

        PaymentHistoryAdapter historyAdapter = new PaymentHistoryAdapter(paymentModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(historyAdapter);


        return rootView;
    }

}
