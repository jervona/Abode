package nyc.c4q.capstone;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
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
    public AHBottomNavigation bottom;
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
    SectionsPageAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    TenantDataBaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottom = findViewById(R.id.bottom_navigation);

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
            Log.e("User", firebaseUser.getUid());
            db.getUserInfoFromDataBase(firebaseUser.getUid(), new UserDBListener() {
                @Override
                public void delegateUser(UserInfo user) {
                    switch (user.getStatus()) {
                        case "Tenant":
                            setupViewPager(viewPager,user.getStatus());
                            setBottomNav(setTenantBottomNav());
                            break;
                        case "PM":
                            setupViewPager(viewPager,user.getStatus());
                            setBottomNav(setPMBottomNav());
                            break;
                    }
                }
            });
//            bar.setVisibility(View.VISIBLE);
        }
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    public interface UserDBListener {
        void delegateUser(UserInfo user);
    }

    public void setBottomNav(ArrayList<AHBottomNavigationItem> items) {
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


    private ArrayList<AHBottomNavigationItem> setTenantBottomNav(){
        ArrayList<AHBottomNavigationItem> tenant = new ArrayList<>();
        tenant.add(new AHBottomNavigationItem("DashBoard", R.drawable.dash));
        tenant.add(new AHBottomNavigationItem("Payment", R.drawable.payment));
        tenant.add(new AHBottomNavigationItem("Maintenance", R.drawable.maintenance));
        return tenant;
    }

    private ArrayList<AHBottomNavigationItem> setPMBottomNav(){
        ArrayList<AHBottomNavigationItem> pm = new ArrayList<>();
        pm.add(new AHBottomNavigationItem("DashBoard", R.drawable.dash));
        pm.add(new AHBottomNavigationItem("Payment", R.drawable.payment));
        pm.add(new AHBottomNavigationItem("Maintenance", R.drawable.maintenance));
        pm.add(new AHBottomNavigationItem("Docs", R.mipmap.building));
        return pm;
    }





}
