package nyc.c4q.capstone.payment_history_packages.landlord_pay_package;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.LandlordPay;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandlordPay_Fragment extends Fragment {


    View rootView;

    public LandlordPay_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_landlord_pay_, container, false);

        RecyclerView landlordRv = rootView.findViewById(R.id.landlord_payment_rv);

        List<LandlordPay> landlordPay = new ArrayList<>();
        landlordPay.add(new LandlordPay("Robyn Rivera","1419 Jesup Ave","Apt 6E","$600","3710356538","4/12/2018"));
        landlordPay.add(new LandlordPay("Celia Cruz","212 E. Burnside Ave","1st Floor","$1200","1341660481","4/10/2018"));
        landlordPay.add(new LandlordPay("Bob Marley"," 2170 Grand Concourse","Apt 2J","","2002788876","4/10/2018"));
        landlordPay.add(new LandlordPay("James Brown","1717 University Ave","","$1500","6598254010","4/9/2018"));
        landlordPay.add(new LandlordPay("Luis Miguel","59 E. 161st ","1st floor","$2000","4227984960","4/8/2018"));
        landlordPay.add(new LandlordPay("Belcalis Almanzar","1717 Ogden Ave"," Apt 5D","$1000","1176504525","4/8/2018"));


        Landlord_Pay_Adapter landlord_pay_adapter = new Landlord_Pay_Adapter(landlordPay);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        landlordRv.setAdapter(landlord_pay_adapter);
        landlordRv.setLayoutManager(linearLayoutManager);



        return rootView;
    }


}
