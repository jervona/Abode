package nyc.c4q.capstone.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import nyc.c4q.capstone.R;

/**
 * Created by jervon.arnoldd on 3/26/18.
 */

public class ImagiveAdapter extends RecyclerView.Adapter<ImagiveAdapter.ImageHolder> {


    List<Uri> uriList;

    public ImagiveAdapter() {
    }

    public ImagiveAdapter(List<Uri> uriList) {
        this.uriList = uriList;
    }


    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_itemview, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        uriList.get(position);
        holder.onBind(uriList.get(position));
    }

    public void itemAdded(Uri uri){
        uriList.add(uri);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return uriList.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
       ImageView imageView;


        public ImageHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }


        public void onBind(Uri uri) {
            Glide.with(itemView.getContext())
                    .load(uri)
                    .centerCrop()
                    .into(imageView);

        }
    }
}
