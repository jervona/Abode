package nyc.c4q.capstone.LandLord.payment_recycleview;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.R;
import nyc.c4q.capstone.diffutilies.PaymentDiffCallback;
import tenant_data_models.TenantPaymentHistoryModel;

/**
 * Created by D on 3/24/18.
 */

public class Landlord_Pay_Adapter extends RecyclerView.Adapter<Landlord_Pay_Holder> {

    List<TenantPaymentHistoryModel> landlordPayList;

    public Landlord_Pay_Adapter(List<TenantPaymentHistoryModel> landlordPayList) {
        if (landlordPayList == null) {
            this.landlordPayList = new ArrayList<>();
        } else {
            this.landlordPayList = landlordPayList;
        }
    }

    @Override
    public Landlord_Pay_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.landlord_pay_itemview,parent,false);
        return new Landlord_Pay_Holder(view);
    }

    @Override
    public void onBindViewHolder(Landlord_Pay_Holder holder, int position) {

        TenantPaymentHistoryModel landlordPay = landlordPayList.get(position);
        holder.onBind(landlordPay);
    }


    public void updateTicketListItems(List<TenantPaymentHistoryModel> payment) {
        final PaymentDiffCallback diffCallback = new PaymentDiffCallback(this.landlordPayList, payment);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.landlordPayList.clear();
        this.landlordPayList.addAll(payment);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return landlordPayList.size();
    }
}
