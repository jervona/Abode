package nyc.c4q.capstone.maintenance_tabs;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.R;

/**
 * Created by c4q on 3/19/18.
 */

public class SubmittedViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.submitted_card_view)
    CardView submittedView;
    @BindView(R.id.ticket_num_view)
    TextView ticketNumView;
    @BindView(R.id.location_view)
    TextView locationView;
    @BindView(R.id.description_view)
    TextView descriptionView;
    @BindView(R.id.status_view)
    TextView statusView;
    @BindView(R.id.date_reported_view)
    TextView dateReportedView;
    @BindView(R.id.repair_date_view)
    TextView repairDateView;
    @BindView(R.id.cancel_view)
    Button cancelView;
    @BindView(R.id.add_notes)
    Button addNotes;

    public SubmittedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void onBind(Tickets tix) {

    }
}
