package nyc.c4q.capstone.maintenance;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.Tickets;

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
//    @BindView(R.id.image)
//    ImageView repairImage;

    private FirebaseStorage storage;

    public SubmittedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        storage = FirebaseStorage.getInstance();
    }


    public void onBind(Tickets tix) {
        ticketNumView.setText("Ticket Num: " + tix.getTicket_number());
        statusView.setText("Status: " + tix.getStatus());
        descriptionView.setText("Description: " + tix.getDescription());

//        if (tix.getImageUrl() !=null || !tix.getImageUrl().isEmpty()) {
//            StorageReference storageReference = storage.getReferenceFromUrl(tix.getImageUrl());
//            Glide.with(itemView.getContext())
//                    .using(new FirebaseImageLoader())
//                    .load(storageReference)
//                    .into(repairImage);
//        }

    }
}
