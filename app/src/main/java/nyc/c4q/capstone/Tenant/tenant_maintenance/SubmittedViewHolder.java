package nyc.c4q.capstone.Tenant.tenant_maintenance;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.NewRequestFragment;
import nyc.c4q.capstone.datamodels.Tickets;

/**
 * Created by c4q on 3/19/18.
 */

class SubmittedViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.note_item_title)
    TextView title;
    @BindView(R.id.note_item_body)
    TextView description;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.user_priority)
    TextView userPriority;
    @BindView(R.id.card_view)
    CardView card;

    SubmittedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void onBind(final Tickets tix) {
        Date cal = new Date(tix.getTime());
        DateFormat df = new SimpleDateFormat("MM:dd:yy");
        String timeSTamp = df.format(cal);
        String num = String.valueOf(tix.getTime());
        title.setText(tix.getTitle());
        description.setText(tix.getDescription());
        date.setText(timeSTamp);

        switch (tix.getPriority()){
            case 0:
                userPriority.setText(R.string.none);
                break;
            case 1:
                userPriority.setText(R.string.urgent);
                break;
            case 2:
                userPriority.setText(R.string.moderate);
                break;
        }

        switch (tix.getStatus()) {
            case "Pending":
                status.setTextColor(Color.parseColor("#F1C40F"));
                status.setText("Pending");
                break;
            case "Completed":
                status.setTextColor(Color.parseColor("#169e31"));
                status.setText("Completed");
                break;
            case "Submitted":
                status.setTextColor(Color.parseColor("#E67E22"));
                status.setText("Scheduled");
                break;
        }

        card.setOnClickListener(view -> {
            Toast.makeText(itemView.getContext(), "Your request details", Toast.LENGTH_SHORT).show();
            NewRequestFragment requestFragment = new NewRequestFragment();
            FragmentManager manager = ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager();
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            String tixToString = gson.toJson(tix);
            bundle.putString("Tix_data", tixToString);
            requestFragment.setArguments(bundle);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.whole_view, requestFragment).addToBackStack("maintenance tix");
            transaction.commit();
        });
    }

}
