package nyc.c4q.capstone.signupactivites;


import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.UserInfo;

public class LandLordSignUpActivity extends AppCompatActivity {
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
    EditText confirmPw;
    @BindViews({R.id.landlord_firstname, R.id.landlord_lastname, R.id.landlord_email, R.id.landlord_phone, R.id.landlordPW, R.id.landlord_confirmPW})
    List<EditText> feilds;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_lord_sign_up);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.landlord_signup)
    public void checkfields() {
        if (isPasswordEmpty()) {
            if (isBothPasswordsMatch()) {
                if (password.getText().toString().length() <= 6) {
                    Toast.makeText(this, "Passwords Length Needs be Longer then 6 Characters ", Toast.LENGTH_SHORT).show();
                } else {
                    createUser(emailaddress.getText().toString(), password.getText().toString());
                }
            }
        }
    }

    public boolean isPasswordEmpty() {
        for (EditText editText : feilds) {
            if (editText.getText().toString().isEmpty()) {
                Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean isBothPasswordsMatch() {
        if (!password.getText().toString().equals((confirmPw.getText().toString()))) {
            Toast.makeText(this, "Passwords Doesnt Match", Toast.LENGTH_SHORT).show();
        }
        return password.getText().toString().equals((confirmPw.getText().toString()));
    }

    private void createUser(final String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        UserInfo userInfo = new UserInfo(user.getUid()
                                , firstName.getText().toString()
                                , lastName.getText().toString()
                                , phone.getText().toString()
                                , email
                                , "PM");

                        FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid()).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.e("Im Funning","..........");
                                    startActivity(new Intent(LandLordSignUpActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

                    } else {
                        Log.w("LandLordSignUpActivity", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(LandLordSignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}