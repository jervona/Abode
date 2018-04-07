package nyc.c4q.capstone.tenant_maintenance;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.Tickets;

/**
 * Created by c4q on 3/19/18.
 */

public class SubmittedViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ticket_num)
    TextView ticketNum;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.status)
    Button status;
    @BindView(R.id.card_view)
    CardView card;
    @BindView(R.id.user_priority)
    TextView userPriority;


    private FirebaseStorage storage;
    private static final String TIX_KEY = "Tix data";

    public SubmittedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        storage = FirebaseStorage.getInstance();

    }

    public void onBind(final Tickets tix) {

        Date cal = new Date(tix.getTime());
        DateFormat df = new SimpleDateFormat("MM:dd:yy");
        String timeSTamp = df.format(cal);
        String num = String.valueOf(tix.getTime());
        ticketNum.setText(num);
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
                status.setBackgroundColor(Color.YELLOW);
                break;
            case "Completed":
                status.setBackgroundColor(Color.DKGRAY);
                break;
            case "Submitted":
                status.setBackgroundColor(Color.GREEN);
                break;

        }

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(itemView.getContext(), "Your request details", Toast.LENGTH_SHORT).show();
                NewRequestFragment requestFragment = new NewRequestFragment();
                FragmentManager manager = ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager();
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                String tixToString = gson.toJson(tix);
                bundle.putString("Tix_data", tixToString);
                requestFragment.setArguments(bundle);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.maintenance_frag, requestFragment).addToBackStack("maintenance tix");
                transaction.commit();
            }
        });

//        if (tix.getImageUrl() !=null || !tix.getImageUrl().isEmpty()) {
//            StorageReference storageReference = storage.getReferenceFromUrl(tix.getImageUrl());
//            Glide.with(itemView.getContext())
//                    .using(new FirebaseImageLoader())
//                    .load(storageReference)
//                    .into(repairImage);
//        }

    }

}
