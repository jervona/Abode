package nyc.c4q.capstone;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import nyc.c4q.capstone.datamodels.UserInfo;
import nyc.c4q.capstone.signupactivites.SignInActivity;
import tenant_data_models.TenantPaymentHistoryModel;


public class TenantActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.container) ViewGroup container;
    @BindView(R.id.bottom_navigation) AHBottomNavigation bottom;

    View dashView;
    View maintenanceView;
    View paymentView;
    AHBottomNavigationAdapter navigationAdapter;

    @Inject TenentDataBaseHelper helper;

    Context context;
    EditText editText;

    Dialog confirmationPopup;

    private static final int REQUEST_CODE = 94;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant);
        ButterKnife.bind(this);
        BaseApp application = (BaseApp) getApplicationContext();
        application.myComponent.inject(this);
        setupBottomNav();
        context = this;
    }

    public void setupBottomNav() {
        LayoutInflater layoutInflater = getLayoutInflater();
        dashView = layoutInflater.inflate(R.layout.tenant_dash, null);
        maintenanceView = layoutInflater.inflate(R.layout.tenant_maintance, null);
        paymentView = layoutInflater.inflate(R.layout.tenant_payment, null);
         editText = paymentView.findViewById(R.id.payment_input);

        container.addView(dashView);

        navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.tenant_bottom_nav);
        navigationAdapter.setupWithBottomNavigation(bottom);

        bottom.setDefaultBackgroundColor(Color.WHITE);
        bottom.setAccentColor(Color.BLACK);
        bottom.setOnTabSelectedListener((position, wasSelected) -> {
            if (bottom.getCurrentItem() != position) {
                switch (position) {
                    case 0:
                        container.removeAllViews();
                        container.addView(dashView);
                        bottom.setCurrentItem(position, wasSelected);
                        break;
                    case 1:
                        container.removeAllViews();
                        container.addView(paymentView);
                        bottom.setCurrentItem(position, wasSelected);
                        break;
                    case 2:
                        container.removeAllViews();
                        container.addView(maintenanceView);
                        bottom.setCurrentItem(position, wasSelected);
                        break;
                }
            }
            return false;
        });
    }


    public void paymentPopup() {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI2YjdhZTdlMjQ1NDNjZjZmN2ViOTdkOTUwYmEyZWZiZGQ1ZDNiY2QxN2Q2YTQ4Y2IxMDVjOTcyNzgyNzQxMDU4fGNyZWF0ZWRfYXQ9MjAxOC0wNC0wM1QxNDoyOToyMC4zNzM4Mjg5MDgrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=");
        startActivityForResult(dropInRequest.getIntent(context), REQUEST_CODE);
    }

    public String confirmationNumber() {
        Random random = new Random();
        StringBuilder confirmation = new StringBuilder();
        for (int i = 1; i < 10; i++) {
            confirmation.append(random.nextInt(10));
        }
        return confirmation.toString();
    }

    void postNonceToServer(String nonce) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("payment_method_nonce", nonce);
        client.post("http://your-server/checkout", params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {}

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }

                }
        );
    }

    public void popUp(String confirmationNum) {
        confirmationPopup = new Dialog(this);
        confirmationPopup.setTitle("    Payment Confirmation");
        View view = getLayoutInflater().inflate(R.layout.fragment_pay, null);
        final TextView confirmation = view.findViewById(R.id.confirmation);
        confirmation.setText(confirmationNum);
        confirmationPopup.setContentView(view);
        confirmationPopup.show();
    }

    public void openNewRequest() {
        NewRequestFragment requestFragment = new NewRequestFragment();
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragManager.beginTransaction();
        fragmentTransaction.replace(R.id.whole_view, requestFragment).addToBackStack("maintenance frag");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay:
                bottom.setCurrentItem(1);
                break;
            case R.id.maintenance_card:
                bottom.setCurrentItem(2);
                break;
            case R.id.pay_button:
                paymentPopup();
                break;
            case R.id.fab:
                openNewRequest();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dash_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                MainActivity.mUsername = MainActivity.ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("acticity","Running");
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String stringNonce = nonce.getNonce();
                Log.e("mylog", "Result: " + stringNonce);
                String num = confirmationNumber();
                Date date = Calendar.getInstance().getTime();
                String hello = data.toString();
                Log.e("Date", hello);
                String month = date.toString().substring(0,10);
                String name = helper.getUser().getFirst_name();
                TenantPaymentHistoryModel payment = new TenantPaymentHistoryModel(name, helper.getUser().getAPT(), month, "$" + editText.getText().toString(), num);
                helper.upLoadRent(payment)
                        .doOnComplete(()-> popUp(num))
                        .subscribe();
                Log.e("Amount", editText.getText().toString());
                editText.setText("");
                // use the result to update your UI and send the Payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Toast.makeText(this, "Payment Canceled", Toast.LENGTH_SHORT).show();
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.e("Error", error.getMessage());
            }
        }
    }
}
