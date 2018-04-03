package nyc.c4q.capstone.diffutilies;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import nyc.c4q.capstone.datamodels.Tickets;

/**
 * Created by jervon.arnoldd on 3/29/18.
 */

public class TicketsDiffCallback extends DiffUtil.Callback {


    private final List<Tickets> oldTicketList;
    private final List<Tickets> newTicketList;

    public TicketsDiffCallback(List<Tickets> oldEmployeeList, List<Tickets> newEmployeeList) {
        this.oldTicketList = oldEmployeeList;
        this.newTicketList = newEmployeeList;
    }

    @Override
    public int getOldListSize() {
        return oldTicketList.size();
    }

    @Override
    public int getNewListSize() {
        return newTicketList.size();
    }


    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldTicketList.get(oldItemPosition).getTime() == newTicketList.get(
                newItemPosition).getTime();
    }


    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Tickets oldTicket = oldTicketList.get(oldItemPosition);
        final Tickets newTicket = newTicketList.get(newItemPosition);
        return oldTicket.getStatus().equals(newTicket.getStatus());
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
