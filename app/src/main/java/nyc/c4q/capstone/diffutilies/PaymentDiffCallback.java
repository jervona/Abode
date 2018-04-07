package nyc.c4q.capstone.diffutilies;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import tenant_data_models.TenantPaymentHistoryModel;

/**
 * Created by jervon.arnoldd on 4/1/18.
 */

public class PaymentDiffCallback extends DiffUtil.Callback {


    private final List<TenantPaymentHistoryModel> oldRentList;
    private final List<TenantPaymentHistoryModel> newRentList;

    public PaymentDiffCallback(List<TenantPaymentHistoryModel> oldEmployeeList, List<TenantPaymentHistoryModel> newEmployeeList) {
        this.oldRentList = oldEmployeeList;
        this.newRentList = newEmployeeList;
    }

    @Override
    public int getOldListSize() {
        return oldRentList.size();
    }

    @Override
    public int getNewListSize() {
        return newRentList.size();
    }


    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRentList.get(oldItemPosition).getConf() == newRentList.get(
                newItemPosition).getConf();
    }


    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final TenantPaymentHistoryModel oldPayment = oldRentList.get(oldItemPosition);
        final TenantPaymentHistoryModel newPayment = newRentList.get(newItemPosition);
        return oldPayment.getAmount_paid().equals(newPayment.getAmount_paid());
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

