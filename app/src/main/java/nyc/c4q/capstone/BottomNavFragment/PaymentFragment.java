package nyc.c4q.capstone.BottomNavFragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Calendar;
import java.util.Date;
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
    private static PayPalConfiguration config;

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
        recyclerView.setLayoutManager(linearLayoutManager);
        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId("YOUR CLIENT ID");

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            Date date = Calendar.getInstance().getTime();
            String month = date.toString().substring(4, 7);
            PaymentHistoryModel payment = new PaymentHistoryModel(month, "$" + editText.getText().toString(), num);
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
                    Collections.reverse(payments);
                    adapter.updateTicketListItems(payments);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void popUp(String s, String num) {
        Dialog dialog = new Dialog(rootView.getContext());
        dialog.setContentView(R.layout.fragment_pay);
        dialog.setTitle("    Payment Confirmation");
        TextView confirmation = (TextView) dialog.findViewById(R.id.confirmation);
        TextView amount = (TextView) dialog.findViewById(R.id.amount_paid);
        confirmation.setText(num);
        amount.setText(s);
        dialog.show();
    }

    public static void sendUser(String apt) {

    }

    @OnClick(R.id.paypal_button)
    public void beginPayment() {
        Intent serviceConfig = new Intent(rootView.getContext(), PayPalService.class);
        serviceConfig.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        rootView.getContext().startService(serviceConfig);

        PayPalPayment payment = new PayPalPayment(new BigDecimal("5.65"),
                "USD", "My Awesome Item", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent paymentConfig = new Intent(rootView.getContext(), PaymentActivity.class);
        paymentConfig.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        paymentConfig.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(paymentConfig, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(
                    PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("sampleapp", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification

                } catch (JSONException e) {
                    Log.e("sampleapp", "no confirmation data: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("sampleapp", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("sampleapp", "Invalid payment / config set");
        }
    }

    @Override
    public void onDestroy(){
        rootView.getContext().stopService(new Intent(rootView.getContext(), PayPalService.class));
        super.onDestroy();
    }
}
