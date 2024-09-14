package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.R;
import com.github.islamkhsh.CardSliderAdapter;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;


public class ImageViewpagerAdapter extends CardSliderAdapter<ImageViewpagerAdapter.MovieViewHolder> {

    private Activity activity;
    String symbol;
    ArrayList<String> movies;

    int flag = 0;


    public ImageViewpagerAdapter(Activity activity, ArrayList<String> movies) {
        this.activity = activity;
        this.movies = movies;
    }


    @Override
    public int getItemCount() {

        return movies.size();

    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, parent, false);


        return new MovieViewHolder(view);
    }


    @Override
    public void bindVH(MovieViewHolder movieViewHolder, int i) {

        Picasso.get().load("http://vedicparampara.com/panel/" + movies.get(i))
                .error(R.drawable.image_not_found)
                .into(movieViewHolder.imageView);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;


        public MovieViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imge);


        }
    }
}