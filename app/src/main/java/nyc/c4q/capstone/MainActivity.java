package nyc.c4q.capstone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.BottomNavFragment.DashBoardFragment;
import nyc.c4q.capstone.BottomNavFragment.DocsFragment;
import nyc.c4q.capstone.BottomNavFragment.MaintanceFragment;
import nyc.c4q.capstone.BottomNavFragment.PaymentFragment;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.container)ViewPager viewPager;
    @BindView(R.id.bottom_navigation)AHBottomNavigation bottom;

    private ArrayList<AHBottomNavigationItem> items = new ArrayList<>();

    private static final String TAG = "MainActivity";
    public static final String ANONYMOUS = "anonymous";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences preferences;
    private GoogleApiClient googleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottom = findViewById(R.id.bottom_navigation);
        setBottomNav();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
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
            if (firebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = firebaseUser.getPhotoUrl().toString();
            }
        }
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    public void setBottomNav() {
        setupViewPager(viewPager);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("DashBoard", R.drawable.ic_location_searching_black_24dp);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Payment", R.drawable.blank_heart);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Maintenance", R.drawable.headline);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Docs", R.drawable.ic_launcher_foreground);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        bottom.addItems(items);
        bottom.setCurrentItem(0);
        bottom.setDefaultBackgroundColor(Color.LTGRAY);
        bottom.setAccentColor(Color.parseColor("#52c7b8"));
//        bottom.setColoredModeColors(Color.LTGRAY,Color.parseColor("#52c7b8"));
//        bottom.setAccentColor(Color.parseColor("#52c7b8"));
        bottom.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager.setCurrentItem(position);
                bottom.setCurrentItem(position, wasSelected);
                return false;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashBoardFragment());
        adapter.addFragment(new PaymentFragment());
        adapter.addFragment(new MaintanceFragment());
        adapter.addFragment(new DocsFragment());
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                firebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(googleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private class SectionsPageAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);

        }

        public SectionsPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
