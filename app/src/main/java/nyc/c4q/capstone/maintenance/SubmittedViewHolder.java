package nyc.c4q.capstone.maintenance;

import android.graphics.Color;
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


    private FirebaseStorage storage;

    public SubmittedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        storage = FirebaseStorage.getInstance();

    }


    public void onBind(Tickets tix) {

        Date cal = new Date(tix.getTime());
        DateFormat df = new SimpleDateFormat("MM:dd:yy");
        String timeSTamp = df.format(cal);
        String num = String.valueOf(tix.getTime());
        ticketNum.setText(num);
        title.setText(tix.getTitle());
        description.setText(tix.getDescription());
        date.setText(timeSTamp);
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
//        if (tix.getImageUrl() !=null || !tix.getImageUrl().isEmpty()) {
//            StorageReference storageReference = storage.getReferenceFromUrl(tix.getImageUrl());
//            Glide.with(itemView.getContext())
//                    .using(new FirebaseImageLoader())
//                    .load(storageReference)
//                    .into(repairImage);
//        }

    }
}
