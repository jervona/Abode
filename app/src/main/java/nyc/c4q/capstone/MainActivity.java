package nyc.c4q.capstone;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import nyc.c4q.capstone.adapter.SectionsPageAdapter;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.UserInfo;
import nyc.c4q.capstone.signupactivites.SignInActivity;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottom;
    @BindView(R.id.progressBar)
    ProgressBar bar;

    private static final String TAG = "MainActivity";
    public static final String ANONYMOUS = "anonymous";
    private static FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    public static String mUsername;
    private SharedPreferences preferences;
    public static GoogleApiClient googleApiClient;
    SectionsPageAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    TenantDataBaseHelper db;
    AHBottomNavigationAdapter navigationAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new SectionsPageAdapter(getSupportFragmentManager());
        db = TenantDataBaseHelper.getInstance(database);
        mUsername = ANONYMOUS;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        checkSignIn();
    }

    public void checkSignIn() {
        if (firebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = firebaseUser.getDisplayName();
            Log.e("User", firebaseUser.getUid());
            bar.setVisibility(View.VISIBLE);
            db.getUserInfoFromDataBase(firebaseUser.getUid(), new UserDBListener() {
                @Override
                public void delegateUser(UserInfo user) {
                    switch (user.getStatus()) {
                        case "Tenant":
                            bar.setVisibility(View.GONE);
                            setupViewPager(viewPager,user.getStatus());
                            navigationAdapter = new AHBottomNavigationAdapter(MainActivity.this, R.menu.tenant_bottom_nav);
                            navigationAdapter.setupWithBottomNavigation(bottom);
                            setBottomNav();
                            break;
                        case "PM":
                            setupViewPager(viewPager,user.getStatus());
                            navigationAdapter = new AHBottomNavigationAdapter(MainActivity.this, R.menu.pm_bottom_nav);
                            navigationAdapter.setupWithBottomNavigation(bottom);
                            setBottomNav();
                            break;
                    }
                }
            });

        }
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    public interface UserDBListener {
        void delegateUser(UserInfo user);
    }

    public void setBottomNav() {
        bottom.setCurrentItem(0);
        bottom.setDefaultBackgroundColor(Color.WHITE);
        bottom.setAccentColor(Color.BLACK);
        bottom.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                try {
                    viewPager.setCurrentItem(position);
                    bottom.setCurrentItem(position, wasSelected);
                } catch (StackOverflowError e) {
                    Log.e("Caught Error", e.getMessage());
                }
                return false;
            }
        });
//        bottom.setNotification("10", 2);
    }

    private void setupViewPager(ViewPager viewPager,String status) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.initBottomNav(status);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}