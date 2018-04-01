package nyc.c4q.capstone.payment_history_package;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.PaymentDiffCallback;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.TicketsDiffCallback;
import nyc.c4q.capstone.datamodels.PaymentHistoryModel;
import nyc.c4q.capstone.datamodels.Tickets;

/**
 * Created by D on 3/18/18.
 */

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryHolder> {


    private List<PaymentHistoryModel> paymentModelList;

    public PaymentHistoryAdapter(List<PaymentHistoryModel> paymentModelList) {
        if (paymentModelList == null) {
            this.paymentModelList = new ArrayList<>();
        } else {
            this.paymentModelList = paymentModelList;
        }
    }

    public PaymentHistoryAdapter() {
    }

    @Override
    public PaymentHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_itemview_layout, parent, false);
        return new PaymentHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentHistoryHolder holder, int position) {

        PaymentHistoryModel paymentModel = paymentModelList.get(position);
        holder.onBind(paymentModel);
    }


    public void updateTicketListItems(List<PaymentHistoryModel> payment) {
        final PaymentDiffCallback diffCallback = new PaymentDiffCallback(this.paymentModelList, payment);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.paymentModelList.clear();
        this.paymentModelList.addAll(payment);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return paymentModelList.size();
    }
}
