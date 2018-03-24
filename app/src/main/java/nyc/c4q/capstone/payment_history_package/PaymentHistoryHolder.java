package nyc.c4q.capstone.payment_history_package;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.PaymentHistoryModel;

/**
 * Created by D on 3/18/18.
 */

public class PaymentHistoryHolder extends RecyclerView.ViewHolder {


    private TextView month;
    private TextView amount_paid;


    public PaymentHistoryHolder(View itemView) {
        super(itemView);


        amount_paid = itemView.findViewById(R.id.amount_paid);
        month = itemView.findViewById(R.id.month);

    }

    public void onBind(PaymentHistoryModel paymentModel) {

        month.setText(paymentModel.getMonth());
        amount_paid.setText(paymentModel.getAmount_paid());


    }
}
