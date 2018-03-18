package nyc.c4q.capstone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nyc.c4q.capstone.BottomNavFragment.DashBoardFragment;

public class LandLordSignUpActivity extends AppCompatActivity {

    private Button LandlordSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_lord_sign_up);

        LandlordSignUp = (Button)findViewById(R.id.landlord_signup);


        LandlordSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LandLordSignUpActivity.this, SignInActivity.class);
                startActivity(intent);

            }
        });

}
}





