package nyc.c4q.capstone.payment_history_packages.tenant_pay_package;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.diffutilies.PaymentDiffCallback;
import nyc.c4q.capstone.R;
import tenant_data_models.TenantPaymentHistoryModel;

/**
 * Created by D on 3/18/18.
 */

public class Tenant_Pay_Adapter extends RecyclerView.Adapter<Tenant_Pay_Holder> {


    private List<TenantPaymentHistoryModel> paymentModelList;

    public Tenant_Pay_Adapter(List<TenantPaymentHistoryModel> paymentModelList) {
        if (paymentModelList == null) {
            this.paymentModelList = new ArrayList<>();
        } else {
            this.paymentModelList = paymentModelList;
        }
    }

    public Tenant_Pay_Adapter() {
    }

    @Override
    public Tenant_Pay_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_itemview_layout, parent, false);
        return new Tenant_Pay_Holder(view);
    }

    @Override
    public void onBindViewHolder(Tenant_Pay_Holder holder, int position) {

        TenantPaymentHistoryModel paymentModel = paymentModelList.get(position);
        holder.onBind(paymentModel);
    }


    public void updateTicketListItems(List<TenantPaymentHistoryModel> payment) {
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
