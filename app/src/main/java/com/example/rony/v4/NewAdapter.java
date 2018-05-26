package com.example.rony.v4;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ImageHolder> {
    private ArrayList<MainActivity.ImageModel> mImages;

    public NewAdapter(ArrayList<MainActivity.ImageModel> images) {
        mImages = images;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.view_image, null);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        holder.bindViewHolder(mImages.get(position));
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }

        public void bindViewHolder(MainActivity.ImageModel image) {
            if (!TextUtils.isEmpty(image.getImageURL())) {
                Picasso.with(imageView.getContext())
                        .load(image.getImageURL())
                        .into(imageView);
            }
        }
    }
}
