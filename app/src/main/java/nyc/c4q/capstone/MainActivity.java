package nyc.c4q.capstone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;

import cz.msebera.android.httpclient.entity.mime.Header;
import nyc.c4q.capstone.adapter.SectionsPageAdapter;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.UserInfo;
import nyc.c4q.capstone.signupactivites.SignInActivity;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CODE = 94;
    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottom;
    @BindView(R.id.progressBar)
    ProgressBar bar;
    private ArrayList<AHBottomNavigationItem> items = new ArrayList<>();

    private static final String TAG = "MainActivity";
    public static final String ANONYMOUS = "anonymous";
    private static FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    public static String mUsername;
    private SharedPreferences preferences;
    public static GoogleApiClient googleApiClient;



    private static final int NOTIFICATION_ID = 555;
    String NOTIFICATION_CHANNEL = "C4Q Notifications";
    SectionsPageAdapter adapter;

    FirebaseDatabase database =FirebaseDatabase.getInstance();
    TenantDataBaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottom = findViewById(R.id.bottom_navigation);
        setBottomNav();
        adapter = new SectionsPageAdapter(getSupportFragmentManager());
        db = TenantDataBaseHelper.getInstance(database);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = ANONYMOUS;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        checkSignIn();
//        viewPager.setVisibility(View.GONE);
//        viewPager.setVisibility(View.GONE);
    }

    public void checkSignIn() {
        if (firebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = firebaseUser.getDisplayName();
            Log.e("User",firebaseUser.getUid());
            db.getUserInfoFromDataBase(firebaseUser.getUid());
//            bar.setVisibility(View.VISIBLE);
//            Query query = database.getReference("user").child(firebaseUser.getUid());
//            query.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Log.e("Error", databaseError.getMessage());
//                }
//            });

        }
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    public void setBottomNav() {
        setupViewPager(viewPager);
        items.add(new AHBottomNavigationItem("DashBoard", R.drawable.dash));
        items.add(new AHBottomNavigationItem("Payment", R.drawable.payment));
        items.add(new AHBottomNavigationItem("Maintenance", R.drawable.maintenance));
        bottom.addItems(items);
        bottom.setCurrentItem(0);
        bottom.setDefaultBackgroundColor(Color.WHITE);
        bottom.setAccentColor(Color.BLACK);
//        bottom.setAccentColor(Color.parseColor("#52c7b8"));
//        bottom.setColoredModeColors(Color.WHITE,Color.BLACK);
//        bottom.setAccentColor(Color.parseColor("#52c7b8"));
        bottom.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                try {
                    viewPager.setCurrentItem(position);
                    bottom.setCurrentItem(position, wasSelected);
                    Log.e(TAG, adapter.getCount() + "");
                } catch (StackOverflowError e) {
                    Log.e("Caught Error", e.getMessage());
                }
                return false;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.initBottomNav("Tenant");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


}
