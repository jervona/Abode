package nyc.c4q.capstone.maintenance_tabs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nyc.c4q.capstone.R;

/**
 * Created by c4q on 3/19/18.
 */

public class SubmittedAdapter extends RecyclerView.Adapter<SubmittedViewHolder> {
    List<Tickets> submittedRequests;

    public SubmittedAdapter(List<Tickets> submittedRequests) {
        this.submittedRequests = submittedRequests;
    }

    @Override
    public SubmittedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cv = LayoutInflater.from(parent.getContext()).inflate(R.layout.submitted_repairs_itemview, parent, false);
        return new SubmittedViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(SubmittedViewHolder holder, int position) {
        Tickets tix = submittedRequests.get(position);
        holder.onBind(tix);

    }

    @Override
    public int getItemCount() {
        return submittedRequests.size();
    }
}
