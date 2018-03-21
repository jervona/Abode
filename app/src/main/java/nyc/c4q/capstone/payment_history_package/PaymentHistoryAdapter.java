package nyc.c4q.capstone.payment_history_package;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nyc.c4q.capstone.R;

/**
 * Created by D on 3/18/18.
 */

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryHolder>{



    List<PaymentHistoryModel> paymentModelList;

    public PaymentHistoryAdapter(List<PaymentHistoryModel> paymentModelList) {
        this.paymentModelList = paymentModelList;
    }


    @Override
    public PaymentHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_itemview_layout,parent,false);
        return new PaymentHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentHistoryHolder holder, int position) {

        PaymentHistoryModel paymentModel = paymentModelList.get(position);
        holder.onBind(paymentModel);
    }

    @Override
    public int getItemCount() {
        return paymentModelList.size();
    }
}
