package nyc.c4q.capstone;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.datamodels.Tickets;

/**
 * Created by c4q on 4/7/18.
 */

public class LandlordMainViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ticket_num)
    TextView tixNum;
    @BindView(R.id.status)
    Button status;
    @BindView(R.id.tenant_name)
    TextView tenantName;
    @BindView(R.id.tenants_apt)
    TextView aptNum;
    @BindView(R.id.tenant_issue)
    TextView tenantIssue;
    @BindView(R.id.date)
    TextView dateRequested;
    @BindView(R.id.user_priority)
    TextView userPriority;

    public LandlordMainViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);

    }

    public void onBind(Tickets tix) {
        tixNum.setText(tix.getTicket_number());
        aptNum.setText(tix.getApt());
        tenantIssue.setText(tix.getTitle());
        userPriority.setText(tix.getPriority());

        switch (tix.getPriority()) {
            case 0:
                userPriority.setText(R.string.none);
                break;
            case 1:
                userPriority.setText(R.string.moderate);
                break;
            case 2:
                userPriority.setText(R.string.urgent);
                break;
        }

        switch (tix.getStatus()) {
            case "Pending":
                status.setBackgroundColor(Color.YELLOW);
                break;
            case "Completed":
                status.setBackgroundColor(Color.DKGRAY);
                break;
            case "Submitted":
                status.setBackgroundColor(Color.GREEN);
                break;
        }

    }
}
