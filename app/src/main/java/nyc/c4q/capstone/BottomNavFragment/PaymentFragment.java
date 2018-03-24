package nyc.c4q.capstone.BottomNavFragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import nyc.c4q.capstone.R;
import nyc.c4q.capstone.payment_history_package.PayFragment;
import nyc.c4q.capstone.payment_history_package.Payment_History_Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    private View rootView;
    private TextView textView;
    private EditText editText;
    private Button pay;
    private Button pay_history;
    private String confirmation;


    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        textView = rootView.findViewById(R.id.balance);
        editText = rootView.findViewById(R.id.payment_input);
        pay = rootView.findViewById(R.id.pay_button);
        pay_history = rootView.findViewById(R.id.payment_history_button);


        pay_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Payment_History_Fragment payment_history_fragment = new Payment_History_Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.payment_frag, payment_history_fragment);
                fragmentTransaction.addToBackStack("stuff");
                fragmentTransaction.commit();

            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmation = confirmationNumber();
                PayFragment payFragment = new PayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Amount paid", editText.getText().toString());
                bundle.putString("confirmation#",confirmation);
                Log.e("number,",confirmation+","+editText.getText().toString());
                payFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.payment_frag,payFragment);
                fragmentTransaction.addToBackStack("go Back");
                fragmentTransaction.commit();
            }
        });


        return rootView;
    }

    public static String confirmationNumber() {
        Random random = new Random();
        char [] digits = new char[10];
        String hello="";
        digits[0] = (char) (random.nextInt(9) + '1');
        for(int i=1; i<10; i++) {
            hello += random.nextInt(10);
        }
        return hello;
    }

}
