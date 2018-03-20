package nyc.c4q.capstone.payment_history_package;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nyc.c4q.capstone.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    View rootView;
    TextView textView;
    EditText editText;
    Button pay;
    Button pay_history;




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
                fragmentTransaction.replace(R.id.payment_frag,payment_history_fragment);
                fragmentTransaction.addToBackStack("stuff");
                fragmentTransaction.commit();

            }
        });

        return rootView;
    }

}
