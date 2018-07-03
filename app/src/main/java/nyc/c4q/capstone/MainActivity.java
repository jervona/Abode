package nyc.c4q.capstone;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.UserInfo;
import nyc.c4q.capstone.signupactivites.SignInActivity;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener{

    @BindView(R.id.container) ViewPager viewPager;
    @BindView(R.id.bottom_navigation) AHBottomNavigation bottom;
    @BindView(R.id.progressBar) ProgressBar bar;
    @Inject FirebaseAuth firebaseAuth;
    @Inject FirebaseDatabase database;
    @Inject TenentDataBaseHelper helper;

    private static final String TAG = "MainActivity";
    public static final String ANONYMOUS = "anonymous";

    private FirebaseUser firebaseUser;
    public static String mUsername;
    public static GoogleApiClient googleApiClient;

    TenantDataBaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BaseApp application = (BaseApp) getApplicationContext();
        application.myComponent.inject(this);

        db = TenantDataBaseHelper.getInstance(database);
        mUsername = ANONYMOUS;
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
            db.getUserInfoFromDataBase(firebaseUser.getUid(), user -> {
                switch (user.getStatus()) {
                    case "Tenant":
                        helper.setUser(user);
                        bar.setVisibility(View.GONE);
                        startActivity(new Intent(MainActivity.this,TenantActivity.class));
                        break;
                    case "PM":
                        bar.setVisibility(View.GONE);
                       //Landlorad radioGroup
                        break;
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

}