package nyc.c4q.capstone.signupactivites;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.rxbinding2.widget.RxAdapterView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nyc.c4q.capstone.BaseApp;
import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.UserInfo;

public class LandLordSignUpActivity extends AppCompatActivity implements Rename {
    @BindView(R.id.landlord_firstname)
    EditText firstName;
    @BindView(R.id.landlord_lastname)
    EditText lastName;
    @BindView(R.id.landlord_email)
    EditText emailaddress;
    @BindView(R.id.landlord_phone)
    EditText phone;
    @BindView(R.id.landlordPW)
    EditText password;
    @BindView(R.id.landlord_confirmPW)
    Spinner user_status_spinner;
    @BindViews({R.id.landlord_firstname, R.id.landlord_lastname, R.id.landlord_email, R.id.landlord_phone, R.id.landlordPW, R.id.landlord_confirmPW})
    List<EditText> fields;
    @BindView(R.id.landlord_confirmPW)
    EditText confirmPw;

    @Inject FirebaseAuth firebaseAuth;
    @Inject CreatUser stuff;
    private String userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_lord_sign_up);
        ButterKnife.bind(this);
        BaseApp application = (BaseApp) getApplicationContext();
        application.myComponent.inject(this);
        stuff.addActivity(this);
    }

    @OnClick(R.id.landlord_signup)
    public void checkfields() {
        RxAdapterView.itemSelections(user_status_spinner)
                .subscribe(integer -> userStatus=user_status_spinner.getItemAtPosition(integer).toString());
        String toastString =stuff.checkfields(fields,password.getText().toString(),emailaddress.getText().toString());
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
    }

    private void createUser(final String email, String password) {
        stuff.createUserWithEmailAndPassword(email, password)
                .map(authResult -> stuff.addUserToCloudDataBase(authResult,firstName.getText().toString()
                        ,lastName.getText().toString()
                        ,phone.getText().toString(),email,userStatus))
                .doOnError(throwable -> {
                    Log.w("LandLordSignUpActivity", "createUserWithEmail:failure", throwable.fillInStackTrace());
                    Toast.makeText(LandLordSignUpActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                })
                .doOnComplete(() -> {
                    startActivity(new Intent(LandLordSignUpActivity.this, MainActivity.class));
                    finish();
                })
                .subscribe();
    }

    @Override
    public void creatUser() {
         createUser(emailaddress.getText().toString(),password.getText().toString());
    }
}