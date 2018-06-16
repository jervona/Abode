package nyc.c4q.capstone;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


import butterknife.BindView;
import butterknife.ButterKnife;

import nyc.c4q.capstone.adapters.SectionsPageAdapter;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.UserInfo;
import nyc.c4q.capstone.signupactivites.SignInActivity;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottom;
    @BindView(R.id.progressBar)
    ProgressBar bar;
    FloatingActionButton fab;

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

    TextView textview;
    RelativeLayout.LayoutParams layoutparams;

    RecyclerView recyclerView;
    private boolean enable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setAppBar();
        adapter = new SectionsPageAdapter(getSupportFragmentManager());
        db = TenantDataBaseHelper.getInstance(database);
        mUsername = ANONYMOUS;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        recyclerView = findViewById(R.id.ticket_rv);
        fab = findViewById(R.id.fab);
        checkSignIn();
        setBottomNav();
        setAppBar();
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
                            setupViewPager(viewPager, user.getStatus());
                            navigationAdapter = new AHBottomNavigationAdapter(MainActivity.this, R.menu.tenant_bottom_nav);
                            navigationAdapter.setupWithBottomNavigation(bottom);
                            setBottomNav();
                            enable = true;
                            break;
                        case "PM":
                            enable = false;
                            bar.setVisibility(View.GONE);
                            setupViewPager(viewPager, user.getStatus());
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
                    setFabDisplay(position);
                } catch (StackOverflowError e) {
                    Log.e("Caught Error", e.getMessage());
                }
                return false;
            }
        });

    }

    private void setupViewPager(ViewPager viewPager, String status) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.initBottomNav(status);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
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
        }
    }

    public void setAppBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textview = new TextView(getApplicationContext());
        textview.setLayoutParams(layoutparams);
        textview.setText(getResources().getString(R.string.app_name));
        textview.setTextColor(Color.WHITE);
        textview.setGravity(Gravity.CENTER);
        textview.setTextSize(20);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(textview);
    }

    public void setFabDisplay(int position) {
        if (enable) {
            if (position == 2) {
                fab.setVisibility(View.VISIBLE);
            } else {
                fab.setVisibility(View.GONE);
            }
        }
    }

    public void setBottomNotification() {
        Notification notification =Notification.getInstance();
        switch (notification.getPath()) {
            case "Payment":
                bottom.setNotification(String.valueOf(notification.getNum()), 1);
                break;
            case "Maintenance":
                bottom.setNotification(String.valueOf(notification.getNum()), 2);
                break;


        }
    }
}