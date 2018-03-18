package nyc.c4q.capstone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TenantSignUpActivity extends AppCompatActivity {

    private Button tenantSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_sign_up);

        tenantSignUp = (Button)findViewById(R.id.tenant_signup);


        tenantSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TenantSignUpActivity.this, SignInActivity.class);
                startActivity(intent);

            }
        });




            }




}

