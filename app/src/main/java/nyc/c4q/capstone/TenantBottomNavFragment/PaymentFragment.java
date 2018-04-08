package nyc.c4q.capstone.TenantBottomNavFragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.paypal.android.sdk.payments.PayPalConfiguration;

import java.util.Collections;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.signupactivites.SignInActivity;
import tenant_data_models.TenantPaymentHistoryModel;
import nyc.c4q.capstone.datamodels.UserInfo;
import nyc.c4q.capstone.payment_history_packages.tenant_pay_package.Tenant_Pay_Adapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements MainActivity.UserDBListener {

    private View rootView;

    Dialog confirmationPopup;

    @BindView(R.id.balance)
    TextView textView;
    @BindView(R.id.payment_input)
    EditText editText;
    @BindView(R.id.payment_history_rv)
    RecyclerView recyclerView;

    TenantDataBaseHelper db;
    FirebaseDatabase data = FirebaseDatabase.getInstance();
    List<TenantPaymentHistoryModel> payments;
    Tenant_Pay_Adapter adapter;
    static String id = "67b975b0d30f2";
    private static PayPalConfiguration config;
    private static final int REQUEST_CODE = 94;

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(id);

        return rootView;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = TenantDataBaseHelper.getInstance(FirebaseDatabase.getInstance());
        adapter = new Tenant_Pay_Adapter(db.getPayments());
        recyclerView.setAdapter(adapter);
        updateList();
//        vault();

    }

    public String confirmationNumber() {
        Random random = new Random();
        String confirmation = "";
        for (int i = 1; i < 10; i++) {
            confirmation += random.nextInt(10);
        }
        return confirmation;
    }

//    @OnClick(R.id.pay_button)
//    public void makePayment() {
//        String num = confirmationNumber();
//        if (!editText.getText().toString().isEmpty()) {
//            Date date = Calendar.getInstance().getTime();
//            String month = date.toString().substring(4, 7);
//            TenantPaymentHistoryModel payment = new TenantPaymentHistoryModel(month, "$" + editText.getText().toString(), num);
//            db.upLoadRent(payment);
//            popUp(editText.getText().toString(), num);
//        } else {
//            Toast.makeText(rootView.getContext(), "Please Enter an Amount", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void updateList() throws NullPointerException {


        data.getReference().child("Rent").child("7M").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<TenantPaymentHistoryModel>> t = new GenericTypeIndicator<List<TenantPaymentHistoryModel>>() {
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

    public void popUp(String amountPay, String confirmationNum) {
        confirmationPopup = new Dialog(rootView.getContext());
        confirmationPopup.setTitle("    Payment Confirmation");
        View view = getLayoutInflater().inflate(R.layout.fragment_pay, null);
        final TextView confirmation = (TextView) view.findViewById(R.id.confirmation);
        final TextView amount = (TextView) view.findViewById(R.id.amount_paid);
        confirmation.setText(confirmationNum);
        amount.setText(amountPay);
        confirmationPopup.setContentView(view);
        confirmationPopup.show();
    }

    public void testing() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://your-server/client_token", new TextHttpResponseHandler() {
            public Object clientToken;

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {}

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                this.clientToken = clientToken;
            }
        });

    }

    @OnClick(R.id.paypal_button)
    public void onBraintreeSubmit() {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI2YjdhZTdlMjQ1NDNjZjZmN2ViOTdkOTUwYmEyZWZiZGQ1ZDNiY2QxN2Q2YTQ4Y2IxMDVjOTcyNzgyNzQxMDU4fGNyZWF0ZWRfYXQ9MjAxOC0wNC0wM1QxNDoyOToyMC4zNzM4Mjg5MDgrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=");
        startActivityForResult(dropInRequest.getIntent(rootView.getContext()), REQUEST_CODE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String stringNonce = nonce.getNonce();
                Log.e("mylog", "Result: " + stringNonce);
                String num = confirmationNumber();
                Date date = Calendar.getInstance().getTime();
                String month = date.toString().substring(4, 7);
                TenantPaymentHistoryModel payment = new TenantPaymentHistoryModel(month, "$" + editText.getText().toString(), num);
                db.upLoadRent(payment);
                popUp("$" + editText.getText().toString(), num);

                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Toast.makeText(rootView.getContext(), "Payment Canceled", Toast.LENGTH_SHORT).show();
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.e("Error", error.getMessage());
            }
        }
    }

    void postNonceToServer(String nonce) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("payment_method_nonce", nonce);
        client.post("http://your-server/checkout", params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                    // Your implementation here
                }
        );
    }



//    void vault() {
//        DropInResult.fetchDropInResult(rootView.getContext(), clientToken, new DropInResult.DropInResultListener() {
//            @Override
//            public void onError(Exception exception) {
//                // an error occurred
//            }
//
//            @Override
//            public void onResult(DropInResult result) {
//                if (result.getPaymentMethodType() != null) {
//                    // use the icon and name to show in your UI
//                    int icon = result.getPaymentMethodType().getDrawable();
//                    int name = result.getPaymentMethodType().getLocalizedName();
//
//                    PaymentMethodType paymentMethodType = result.getPaymentMethodType();
//                    if (paymentMethodType == PaymentMethodType.ANDROID_PAY || paymentMethodType == PaymentMethodType.GOOGLE_PAYMENT) {
//                        // The last payment method the user used was Android Pay or Google Pay.
//                        // The Android/Google Pay flow will need to be performed by the
//                        // user again at the time of checkout.
//                    } else {
//                        // Use the payment method show in your UI and charge the user
//                        // at the time of checkout.
//                        PaymentMethodNonce paymentMethod = result.getPaymentMethodNonce();
//                    }
//                } else {
//                    // there was no existing payment method
//                }
//            }
//        });
//    }

    @Override
    public void delegateUser(UserInfo user) {
        Log.e("payment",user.getFirst_name());
    }
}
