package nyc.c4q.capstone.LandLord.payment_recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.R;
import tenant_data_models.TenantPaymentHistoryModel;

/**
 * Created by D on 3/24/18.
 */

public class Landlord_Pay_Holder extends RecyclerView.ViewHolder {

    @BindView(R.id.apt_num)
    TextView apt;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.month)
    TextView date;
    @BindView(R.id.amount_paid)
    TextView amount;
    @BindView(R.id.confirmation)
    TextView confirmation;

    public Landlord_Pay_Holder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }

    public void onBind (TenantPaymentHistoryModel landlordPay){
         apt.setText(landlordPay.getTenant_apt());
         name.setText(landlordPay.getTenant_name());
         date.setText(landlordPay.getMonth());
         amount.setText(landlordPay.getAmount_paid());
         confirmation.setText(landlordPay.getConf());
    }
}
