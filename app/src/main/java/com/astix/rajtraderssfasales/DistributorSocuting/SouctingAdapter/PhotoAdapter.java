package com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.ImageTypeInterface;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.PhotoInterface;
import com.astix.rajtraderssfasales.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    Context context;
    List<String> photos;
    String flgPhotoType;

    private static final int MAX_WIDTH = 768;
    private static final int MAX_HEIGHT = 512;

    PhotoInterface photoInterface;
    ImageTypeInterface imageTypeInterface;

    int temp_flg = 0;

    int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

    public PhotoAdapter(Context context, List<String> photos, String flgPhotoType)
    {
        this.context = context;
        this.photos = photos;
        this.flgPhotoType = flgPhotoType;
    }
    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.photo_layout, parent, false);

        photoInterface = (PhotoInterface) context;
        imageTypeInterface = (ImageTypeInterface) context;

        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {

        Picasso.get().load(new File(photos.get(position))).resize(size, size).centerInside().into(holder.photo_view);
        holder.photo_cancel.bringToFront();
         holder.photo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photos.remove(position);
                temp_flg = 1;
                notifyDataSetChanged();

                photoInterface.delPhoto(photos, flgPhotoType);


            }
        });

        if(temp_flg == 0)
        {
         imageTypeInterface.imagetype(flgPhotoType);
        }

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView photo_view;
        ImageButton photo_cancel;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);

            photo_view = itemView.findViewById(R.id.photo_view);
            photo_cancel = itemView.findViewById(R.id.photo_cancel);
        }
    }
}
