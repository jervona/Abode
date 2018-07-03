package nyc.c4q.capstone.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.R;

/**
 * Created by jervon.arnoldd on 3/26/18.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {


    private List<Bitmap> bitmapList;
    private List<String> repairPhoto;



    public ImageAdapter(List<Bitmap> uriList) {
        this.bitmapList = uriList;
    }

    public ImageAdapter(ArrayList<String> repairPhoto) {
        this.repairPhoto = repairPhoto;
    }


    public void addBitmapPhoto(Bitmap bitmap){
        bitmapList.add(bitmap);
        notifyDataSetChanged();
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_itemview, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {

        if (bitmapList != null) {
            bitmapList.get(position);
            holder.onBindUri(bitmapList.get(position));
        }
        if (repairPhoto != null) {
            repairPhoto.get(position);
            holder.onBindUri(repairPhoto.get(position));
        }
    }


    @Override
    public int getItemCount() {
        if (repairPhoto != null) {
            return repairPhoto.size();
        }
        return bitmapList.size();
    }



    class ImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        private FirebaseStorage storage;


        ImageHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            storage = FirebaseStorage.getInstance();
        }

        void onBindUri(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }


        void onBindUri(String url) {
            StorageReference storageReference = storage.getReferenceFromUrl(url);
            Glide.with(itemView.getContext())
                    .using(new FirebaseImageLoader())
                    .load(storageReference)
                    .into(imageView);
        }

    }
}
