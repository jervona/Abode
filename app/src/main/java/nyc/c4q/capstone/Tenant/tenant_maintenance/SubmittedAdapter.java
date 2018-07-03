package nyc.c4q.capstone.Tenant.tenant_maintenance;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.R;
import nyc.c4q.capstone.diffutilies.TicketsDiffCallback;
import nyc.c4q.capstone.datamodels.Tickets;

/**
 * Created by c4q on 3/19/18.
 */

public class SubmittedAdapter extends RecyclerView.Adapter<SubmittedViewHolder> {
    private List<Tickets> requestsList;


    public SubmittedAdapter() {
        this.requestsList = new ArrayList<>();
    }

    public SubmittedAdapter(List<Tickets> requestsList) {
        if (requestsList == null) {
            this.requestsList = new ArrayList<>();
        } else {
            this.requestsList = requestsList;
        }
    }

    @Override
    public SubmittedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cv = LayoutInflater.from(parent.getContext()).inflate(R.layout.maintenance_repairs_itemview, parent, false);
        return new SubmittedViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(SubmittedViewHolder holder, int position) {
        Tickets tix = requestsList.get(position);
        holder.onBind(tix);
    }

    public void updateTicketListItems(List<Tickets> tickets) {
        final TicketsDiffCallback diffCallback = new TicketsDiffCallback(this.requestsList, tickets);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.requestsList.clear();
        this.requestsList.addAll(tickets);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

}
