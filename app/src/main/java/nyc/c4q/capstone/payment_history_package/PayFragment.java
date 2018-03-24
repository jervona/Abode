package nyc.c4q.capstone.payment_history_package;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nyc.c4q.capstone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayFragment extends Fragment {

    private View rootView;
    private TextView payView;
    private TextView confirmationView;

    public PayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pay, container, false);
        payView = rootView.findViewById(R.id.pay_textview);
        confirmationView = rootView.findViewById(R.id.confirmation);
        Bundle bundle = this.getArguments();
        String paid = bundle.getString("Amount paid");

        String confirm = bundle.getString("confirmation#");

        payView.setText(paid);
        confirmationView.setText(confirm);

        return rootView;
    }

}
