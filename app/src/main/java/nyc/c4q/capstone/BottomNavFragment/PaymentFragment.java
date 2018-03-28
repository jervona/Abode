package nyc.c4q.capstone.BottomNavFragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mockPayHistoryScreen();


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmation = confirmationNumber();

                LayoutInflater inflater = getLayoutInflater();
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setView(inflater.inflate(R.layout.dialog_layout, null));
                alertDialog.show();

            }
        });
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
    public static String ticketNumber() {
        Random random = new Random();
        char [] digits = new char[4];
        String hello="";
        digits[0] = (char) (random.nextInt(9) + '1');
        for(int i=1; i<10; i++) {
            hello += random.nextInt(10);
        }
        return hello;
    }






    public void mockPayHistoryScreen(){
        List<PaymentHistoryModel> paymentModelList = new ArrayList<>();
        paymentModelList.add(new PaymentHistoryModel("Jun","$1200","9268863064"));
        paymentModelList.add(new PaymentHistoryModel("Jul","$1200","6784726008"));
        paymentModelList.add(new PaymentHistoryModel("Aug","$1200","3075034981"));
        paymentModelList.add(new PaymentHistoryModel("Sep","$1200","8925426011"));
        paymentModelList.add(new PaymentHistoryModel("Oct","$1200","9076432267"));
        paymentModelList.add(new PaymentHistoryModel("Nov","$1200","2581505430"));
        paymentModelList.add(new PaymentHistoryModel("Dec","$1200","9411325325"));
        paymentModelList.add(new PaymentHistoryModel("Jan","$1200","7495554878"));
        paymentModelList.add(new PaymentHistoryModel("Feb","$1200","4813002897"));
        paymentModelList.add(new PaymentHistoryModel("Mar","$1200","9974548592"));
        paymentModelList.add(new PaymentHistoryModel("Apr","$1200","7474971228"));
        paymentModelList.add(new PaymentHistoryModel("Jun","$1200","8860335417"));
        paymentModelList.add(new PaymentHistoryModel("Jul","$1200","7140368486"));
        paymentModelList.add(new PaymentHistoryModel("Aug","$1200","3120933002"));
        paymentModelList.add(new PaymentHistoryModel("Sep","$1200","8671655665"));
        paymentModelList.add(new PaymentHistoryModel("Oct","$1200","1735607548"));

        PaymentHistoryAdapter historyAdapter = new PaymentHistoryAdapter(paymentModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(historyAdapter);
    }

}
