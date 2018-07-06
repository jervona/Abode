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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.UserInfo;

public class TenantSignUpActivity extends AppCompatActivity {

    @BindView(R.id.tenant_firstname)
    EditText firstName;
    @BindView(R.id.tenant_lastname)
    EditText lastName;
    @BindView(R.id.tenant_email)
    EditText emailaddress;
    @BindView(R.id.tenant_aptNum)
    EditText apt;
    @BindView(R.id.tenant_phone)
    EditText phone;
    @BindView(R.id.tenantPW)
    EditText password;
    @BindView(R.id.tenant_confirmPW)
    EditText confirmPw;
    @BindView(R.id.verification_code)
    EditText verification;
    @BindViews({R.id.tenant_firstname, R.id.tenant_lastname, R.id.tenant_email, R.id.tenant_aptNum, R.id.tenant_phone, R.id.tenantPW, R.id.tenant_confirmPW, R.id.verification_code})
    List<EditText> feilds;
    FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_sign_up);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.tenant_signup)
    public void checkFields() {
        if (areFieldsEmpty()) {
            if (isBothPasswordsMatch()) {
                if (password.getText().toString().length() <= 6) {
                    Toast.makeText(this, "Passwords Length Needs be Longer then 6 Characters ", Toast.LENGTH_SHORT).show();
                } else {
                    createUser(emailaddress.getText().toString(), password.getText().toString());
                }
            }
        }
    }

    public boolean areFieldsEmpty() {
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
            Toast.makeText(this, "Passwords Doesn't Match", Toast.LENGTH_SHORT).show();
        }
        return password.getText().toString().equals((confirmPw.getText().toString()));
    }

    private void createUser(final String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user = firebaseAuth.getCurrentUser();
                        Log.e("Running inside creat", ".....");
                        checkDataBaseForBuilding(verification.getText().toString());
                    } else {
                        Log.w("LandLordSignUpActivity", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(TenantSignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkDataBaseForBuilding(final String code) {
        final Query query = FirebaseDatabase.getInstance().getReference("Properties");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Object>> data = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                HashMap<String, Object> buildings = dataSnapshot.getValue(data);
                assert buildings != null;
                if (checkIfDataBaseContainsCode(buildings, code)) {
                    UserInfo userInfo = new UserInfo(user.getUid()
                            , Long.parseLong(code)
                            , apt.getText().toString()
                            , firstName.getText().toString()
                            , lastName.getText().toString()
                            , phone.getText().toString()
                            , user.getEmail()
                            , "Tenant");
                    FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid()).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.e("Im starting activity", "..........");
                                startActivity(new Intent(TenantSignUpActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(TenantSignUpActivity.this, "Verification Code is incorrect", Toast.LENGTH_SHORT).show();
                    Log.e("Im gonna delete", "..........");
                    user.delete();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean checkIfDataBaseContainsCode(HashMap<String, Object> buildings, String code) {
        for (String key : buildings.keySet()) {
            if (key.equals(code)) {
                return true;
            }
        }
        return false;
    }
}