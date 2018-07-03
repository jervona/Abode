package nyc.c4q.capstone.signupactivites;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.OnClick;
import durdinapps.rxfirebase2.RxFirebaseAuth;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;

public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Dialog loginBuilder;
    private FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        setupGoogleSignIn();

    }

    public void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
    }

    @OnClick(R.id.sign_up)
    public void alertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Sign Up")
                .setMessage("Are You Signing as a Tenant or PropertyManager")
                .setPositiveButton("Tenant", (dialog, which) -> {
                    Toast.makeText(SignInActivity.this, "Launch Tenant Sign up", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, TenantSignUpActivity.class));
                    finish();
                })
                .setNegativeButton("PropertyManager", (dialog, which) -> {
                    Toast.makeText(SignInActivity.this, "Launch PropertyManager Sign up", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, LandLordSignUpActivity.class));
                    finish();
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @OnClick(R.id.login_button)
    public void login() {
        loginBuilder = new Dialog(SignInActivity.this);
        View view = getLayoutInflater().inflate(R.layout.login, null);
        final TextInputLayout emaileWrapper = view.findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper =  view.findViewById(R.id.passwordWrapper);
        emaileWrapper.setHint("Username");
        passwordWrapper.setHint("Password");
        Button button = view.findViewById(R.id.login_button);
        SignInButton googleSignIn =  view.findViewById(R.id.google_sign_in_button);
        button.setOnClickListener(v -> {
            hideKeyboard();

            String username = emaileWrapper.getEditText().getText().toString();
            String password = passwordWrapper.getEditText().getText().toString();

            if (!validateEmail(username)) {
                emaileWrapper.setError("Not a valid email address!");
            } else if (!validatePassword(password)) {
                passwordWrapper.setError("Not a valid password!");
            } else {
                emaileWrapper.setErrorEnabled(false);
                passwordWrapper.setErrorEnabled(false);
                signUser(username, password);
            }
        });

        googleSignIn.setOnClickListener(v -> signIn());
        loginBuilder.setContentView(view);
        loginBuilder.show();

        }


    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signUser(String email, String password) {
        RxFirebaseAuth.signInWithEmailAndPassword(firebaseAuth, email, password)
                .subscribeOn(Schedulers.io())
                .map(authResult -> authResult.getUser() != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(logged -> {
                    loginBuilder.dismiss();
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                },throwable -> {
                    Log.w(TAG, "signInWithEmail:failure: "+ throwable.getMessage());
                    Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        RxFirebaseAuth.signInWithCredential(firebaseAuth,credential)
                .subscribeOn(Schedulers.io())
                .map(authResult -> authResult.getUser() != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(logged -> {
                    loginBuilder.dismiss();
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                },throwable -> {
                    Log.w(TAG, "signInWithEmail:failure: "+ throwable.getMessage());
                    Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                });
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.e(TAG, "Google Sign-In failed.");
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}