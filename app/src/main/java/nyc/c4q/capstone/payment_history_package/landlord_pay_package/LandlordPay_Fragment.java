package nyc.c4q.capstone.payment_history_package.landlord_pay_package;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nyc.c4q.capstone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandlordPay_Fragment extends Fragment {


    public LandlordPay_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landlord_pay_, container, false);
    }

}
