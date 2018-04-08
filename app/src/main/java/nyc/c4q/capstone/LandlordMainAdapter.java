package nyc.c4q.capstone;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.diffutilies.TicketsDiffCallback;

/**
 * Created by c4q on 4/7/18.
 */

public class LandlordMainAdapter extends RecyclerView.Adapter<LandlordMainViewHolder> {
    private List<Tickets> tixRequests;

    public LandlordMainAdapter(List<Tickets> tixRequests) {
        if (tixRequests == null) {
            this.tixRequests = new ArrayList<>();
        } else {
            this.tixRequests = tixRequests;
        }
    }

    @Override
    public LandlordMainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_landlord_maintenance, parent, false);
        return new LandlordMainViewHolder(view);
    }

    public void updateTicketListItems(List<Tickets> tickets) {
        final TicketsDiffCallback diffCallback = new TicketsDiffCallback(this.tixRequests, tickets);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.tixRequests.clear();
        this.tixRequests.addAll(tickets);
        diffResult.dispatchUpdatesTo(this);
    }


    @Override
    public void onBindViewHolder(LandlordMainViewHolder holder, int position) {
        Tickets tix = tixRequests.get(position);
        holder.onBind(tix);
    }

    @Override
    public int getItemCount() {
        return tixRequests.size();
    }
}
