package nyc.c4q.capstone.payment_history_packages.tenant_pay_package;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nyc.c4q.capstone.R;
import tenant_data_models.TenantPaymentHistoryModel;

/**
 * Created by D on 3/18/18.
 */

public class Tenant_Pay_Holder extends RecyclerView.ViewHolder {


    private TextView month;
    private TextView amount_paid;
    private TextView con;


    public Tenant_Pay_Holder(View itemView) {
        super(itemView);
        amount_paid = itemView.findViewById(R.id.amount_paid);
        month = itemView.findViewById(R.id.month);
        con = itemView.findViewById(R.id.con0);
    }

    public void onBind(TenantPaymentHistoryModel paymentModel) {
        month.setText(paymentModel.getMonth());
        amount_paid.setText(paymentModel.getAmount_paid());
        con.setText(paymentModel.getConf());
    }
}
