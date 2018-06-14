package nyc.c4q.capstone.Tenant.tenant_resource_controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nyc.c4q.capstone.R;

/**
 * Created by D on 3/31/18.
 */

public class DashHolder extends RecyclerView.ViewHolder {

    private ImageView resource_pic;
    private TextView resource_text;
    private Context context;
    private DashRvModel dash_rv_model;

    DashHolder(View itemView) {
        super(itemView);
        resource_pic = itemView.findViewById(R.id.rv_image);
        resource_text = itemView.findViewById(R.id.rv_textview);
        context = itemView.getContext();
    }

    public void onBind(DashRvModel data) {
        this.dash_rv_model = data;
       try {
           resource_pic.setImageResource(dash_rv_model.getResource_icon());
       } catch (OutOfMemoryError e){
           Log.e("Got the Error",e.getMessage());
           Log.e("Got the Error",dash_rv_model.getResource_name());

       }
        resource_text.setText(dash_rv_model.getResource_name());
        resource_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dash_rv_model.getUrl()));
                context.startActivity(intent);
            }
        });
    }
}
