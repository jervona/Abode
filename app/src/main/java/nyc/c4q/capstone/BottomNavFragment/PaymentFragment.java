package nyc.c4q.capstone.BottomNavFragment;


import android.app.Dialog;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.PaymentHistoryModel;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.payment_history_package.PaymentHistoryAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    private View rootView;
    private TextView textView;
    private EditText editText;
    RecyclerView recyclerView;
    TenantDataBaseHelper db;
    FirebaseDatabase data = FirebaseDatabase.getInstance();
    List<PaymentHistoryModel> payments;
    PaymentHistoryAdapter adapter;
    static String id;

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this, rootView);
        textView = rootView.findViewById(R.id.balance);
        editText = rootView.findViewById(R.id.payment_input);
        recyclerView = rootView.findViewById(R.id.payment_history_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mockPayHistoryScreen();
        db = TenantDataBaseHelper.getInstance(FirebaseDatabase.getInstance());
        adapter = new PaymentHistoryAdapter(db.getPayments());
        recyclerView.setAdapter(adapter);
        updateList();

    }

    public String confirmationNumber() {
        Random random = new Random();
        String confirmation = "";
        for (int i = 1; i < 10; i++) {
            confirmation += random.nextInt(10);
        }
        return confirmation;
    }

    @OnClick(R.id.pay_button)
    public void makePayment() {
        String num = confirmationNumber();
        if (!editText.getText().toString().isEmpty()) {
            PaymentHistoryModel payment = new PaymentHistoryModel("Month", "$" + editText.getText().toString(), num);
            db.upLoadRent(payment);
            popUp(editText.getText().toString(), num);
        } else {
            Toast.makeText(rootView.getContext(), "Please Enter an Amount", Toast.LENGTH_SHORT).show();
        }
    }


    public void updateList() throws NullPointerException {
            data.getReference().child("Rent").child("7M").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<List<PaymentHistoryModel>> t = new GenericTypeIndicator<List<PaymentHistoryModel>>() {
                    };
                    payments = dataSnapshot.getValue(t);
                    if (payments != null) {
                        adapter.updateTicketListItems(payments);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
    }

    public void mockPayHistoryScreen() {
        List<PaymentHistoryModel> paymentModelList = new ArrayList<>();
        paymentModelList.add(new PaymentHistoryModel("Jun", "$1200", "9268863064"));
        paymentModelList.add(new PaymentHistoryModel("Jul", "$1200", "6784726008"));
        paymentModelList.add(new PaymentHistoryModel("Aug", "$1200", "3075034981"));
        paymentModelList.add(new PaymentHistoryModel("Sep", "$1200", "8925426011"));
        paymentModelList.add(new PaymentHistoryModel("Oct", "$1200", "9076432267"));
        paymentModelList.add(new PaymentHistoryModel("Nov", "$1200", "2581505430"));
        paymentModelList.add(new PaymentHistoryModel("Dec", "$1200", "9411325325"));
        paymentModelList.add(new PaymentHistoryModel("Jan", "$1200", "7495554878"));
        paymentModelList.add(new PaymentHistoryModel("Feb", "$1200", "4813002897"));
        paymentModelList.add(new PaymentHistoryModel("Mar", "$1200", "9974548592"));
        paymentModelList.add(new PaymentHistoryModel("Apr", "$1200", "7474971228"));
        paymentModelList.add(new PaymentHistoryModel("Jun", "$1200", "8860335417"));
        paymentModelList.add(new PaymentHistoryModel("Jul", "$1200", "7140368486"));
        paymentModelList.add(new PaymentHistoryModel("Aug", "$1200", "3120933002"));
        paymentModelList.add(new PaymentHistoryModel("Sep", "$1200", "8671655665"));
        paymentModelList.add(new PaymentHistoryModel("Oct", "$1200", "1735607548"));

        PaymentHistoryAdapter historyAdapter = new PaymentHistoryAdapter(paymentModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(historyAdapter);
    }

    public void popUp(String s, String num) {
        Dialog dialog = new Dialog(rootView.getContext());
        dialog.setContentView(R.layout.fragment_pay);
        dialog.setTitle("Your Payment Is");
        TextView confirmation = (TextView) dialog.findViewById(R.id.confirmation);
        TextView amount = (TextView) dialog.findViewById(R.id.amount_paid);
        confirmation.setText(num);
        amount.setText(s);
        dialog.show();
    }


    public static void sendUser(String apt) {

    }
}
