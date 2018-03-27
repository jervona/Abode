package nyc.c4q.capstone.BottomNavFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.PaymentHistoryModel;
import nyc.c4q.capstone.payment_history_package.PaymentHistoryAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    private View rootView;
    private TextView textView;
    private EditText editText;
    private Button pay;
    private Button pay_history;
    private String confirmation;
    RecyclerView recyclerView;


    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        textView = rootView.findViewById(R.id.balance);
        editText = rootView.findViewById(R.id.payment_input);
        pay = rootView.findViewById(R.id.pay_button);
        recyclerView = rootView.findViewById(R.id.payment_history_rv);
        mockPayHistoryScreen();

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmation = confirmationNumber();
            }
        });


        return rootView;
    }

    public static String confirmationNumber() {
        Random random = new Random();
        char [] digits = new char[10];
        String hello="";
        digits[0] = (char) (random.nextInt(9) + '1');
        for(int i=1; i<10; i++) {
            hello += random.nextInt(10);
        }
        return hello;
    }



    public void mockPayHistoryScreen(){
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
    }

}
