package com.techease.pacific.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.techease.pacific.Models.imageMode;
import com.techease.pacific.R;
import java.util.List;

/**
 * Created by Adamnoor on 7/24/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    Activity activity;
    List<imageMode> imageModes;

    public ImageAdapter(Activity activity, List<imageMode> imageModes) {
        this.activity=activity;
        this.imageModes=imageModes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  imageMode model=imageModes.get(position);
        Glide.with(activity).load(model.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageModes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.ivShowImage);
        }
    }
}
