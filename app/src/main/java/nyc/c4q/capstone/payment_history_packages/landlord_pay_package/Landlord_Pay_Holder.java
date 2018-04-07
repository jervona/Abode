package nyc.c4q.capstone.payment_history_packages.landlord_pay_package;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.LandlordPay;

/**
 * Created by D on 3/24/18.
 */

public class Landlord_Pay_Holder extends RecyclerView.ViewHolder {

    private TextView tenant_name;
    private TextView address;
    private TextView address2;
    private TextView landlordPayConfirmation;
    private TextView datePaid;

    public Landlord_Pay_Holder(View itemView) {
        super(itemView);

        tenant_name = itemView.findViewById(R.id.tenant);
        address = itemView.findViewById(R.id.address);
        address2 = itemView.findViewById(R.id.address2);
        landlordPayConfirmation = itemView.findViewById(R.id.landlord_pay_confirmation);
        //datePaid = itemView.findViewById(R.id.date_paid);
    }

    public void onBind (LandlordPay landlordPay){

        tenant_name.setText(landlordPay.getTenantName());
        address.setText(landlordPay.getAddressLine());
        address2.setText(landlordPay.getAddressLine2());
        landlordPayConfirmation.setText(landlordPay.getConfirmation());
        //datePaid.setText((CharSequence) landlordPay.getDate());
    }
}
