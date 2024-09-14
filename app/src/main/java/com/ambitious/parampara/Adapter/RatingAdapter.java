package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Activity.others.RatingAndReviewFragment;
import com.ambitious.parampara.Model.rating_model;
import com.ambitious.parampara.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {
    private Activity activity;
    ArrayList<rating_model> rating_model;


    public RatingAdapter(RatingAndReviewFragment ratingAndReviewFragment, ArrayList<rating_model> rating_model) {
        this.activity = ratingAndReviewFragment;
        this.rating_model = rating_model;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_rating, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.review_txt.setText(rating_model.get(position).getReview());
        holder.rating_count.setText(rating_model.get(position).getRating());
        holder.user_name.setText(rating_model.get(position).getUser_name());
        try {
            if (rating_model.get(position).getPic() != null) {
                if (rating_model.get(position).getPic().isEmpty()) {
                    Picasso.get()
                            .load(R.drawable.usericon)
                            .error(R.drawable.ut)
                            .into(holder.user_img);
                } else {
                    Picasso.get()
                            .load(rating_model.get(position).getPic())
                            .error(R.drawable.ut)
                            .into(holder.user_img);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return rating_model.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView user_img;
        TextView user_name, review_txt, rating_count;

        public ViewHolder(final View itemView) {
            super(itemView);
            user_img = itemView.findViewById(R.id.user_pic);
            user_name = itemView.findViewById(R.id.username);
            review_txt = itemView.findViewById(R.id.review_txt);
            rating_count = itemView.findViewById(R.id.rating_count);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
