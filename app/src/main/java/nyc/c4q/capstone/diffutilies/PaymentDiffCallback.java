package nyc.c4q.capstone.diffutilies;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import nyc.c4q.capstone.datamodels.PaymentHistoryModel;
import nyc.c4q.capstone.datamodels.Tickets;

/**
 * Created by jervon.arnoldd on 4/1/18.
 */

public class PaymentDiffCallback extends DiffUtil.Callback {


    private final List<PaymentHistoryModel> oldRentList;
    private final List<PaymentHistoryModel> newRentList;

    public PaymentDiffCallback(List<PaymentHistoryModel> oldEmployeeList, List<PaymentHistoryModel> newEmployeeList) {
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
        final PaymentHistoryModel oldPayment = oldRentList.get(oldItemPosition);
        final PaymentHistoryModel newPayment = newRentList.get(newItemPosition);
        return oldPayment.getAmount_paid().equals(newPayment.getAmount_paid());
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

