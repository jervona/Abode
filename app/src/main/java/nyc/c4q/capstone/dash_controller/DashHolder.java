package nyc.c4q.capstone.dash_controller;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nyc.c4q.capstone.R;

/**
 * Created by D on 3/31/18.
 */

public class DashHolder extends RecyclerView.ViewHolder {

    private ImageView resource_pic;
    private TextView  resource_text ;

    public DashHolder(View itemView) {
        super(itemView);

        resource_pic = itemView.findViewById(R.id.rv_image);
        resource_text = itemView.findViewById(R.id.rv_textview);

    }

    public void onBind(Dash_Rv_Model dash_rv_model){
        resource_pic.setImageResource(dash_rv_model.getResource_icon());
        resource_text.setText(dash_rv_model.getResource_name());

    }
}
