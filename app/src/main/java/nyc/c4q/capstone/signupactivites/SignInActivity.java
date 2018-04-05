package nyc.c4q.capstone.signupactivites;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nyc.c4q.capstone.BottomNavFragment.DashBoardFragment;
import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;

public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {


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
        hideSoftKeyboard();
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
                .setPositiveButton("Tenant", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SignInActivity.this, "Launch Tenant Sign up", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this, TenantSignUpActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("PropertyManager", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SignInActivity.this, "Launch PropertyManager Sign up", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this, LandLordSignUpActivity.class));
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @OnClick(R.id.login_button)
    public void login() {
        loginBuilder = new Dialog(SignInActivity.this);
        View view = getLayoutInflater().inflate(R.layout.login, null);
        final EditText email = (EditText) view.findViewById(R.id.editText_email);
        final EditText password = (EditText) view.findViewById(R.id.editText_password);
        Button button = (Button) view.findViewById(R.id.login_button);
        SignInButton googleSignIn = (SignInButton) view.findViewById(R.id.google_sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    signUser(email.getText().toString(), password.getText().toString());
                } else {
                    Toast.makeText(SignInActivity.this, "Please fill any empty fields", Toast.LENGTH_LONG).show();
                }
            }
        });
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        loginBuilder.setContentView(view);
        loginBuilder.show();
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            loginBuilder.dismiss();
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
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

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        } else {
                            loginBuilder.dismiss();
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        assert imm != null;
//        imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
    }
}