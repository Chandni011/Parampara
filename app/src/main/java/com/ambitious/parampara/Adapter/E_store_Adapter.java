package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;


/**
 * Created by user1 on 11/25/2017.
 */

public class E_store_Adapter extends RecyclerView.Adapter<E_store_Adapter.ViewHolder> {
   private Activity activity;
 //  private List<Get_SubCategory_Result> task;
    int count = 1;
    HashMap hashMap,hashMapprice;
    /*public E_store_Adapter(Activity activity, List<Get_SubCategory_Result> task, HashMap hashMap, HashMap hashMapprice) {
        this.activity = activity;
        this.task = task;
        this.hashMap =hashMap;
        this.hashMapprice=hashMapprice;
    }*/

    public E_store_Adapter(Activity activity) {
        this.activity = activity;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_e_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {



       // holder.txt_sub_catName.setText(task.get(position).getServicesSubName());
       // holder.txt_price.setText("र  "+task.get(position).getPrice());




    }

    @Override
    public int getItemCount() {
        return 11;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_sub_catName,txt_price;
        EditText quantity;
        TextView increaseInteger, remove_item;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txt_price=itemView.findViewById(R.id.txt_price);
            quantity = itemView.findViewById(R.id.iteam_amount);
            increaseInteger = itemView.findViewById(R.id.add_item);
            remove_item = itemView.findViewById(R.id.remove_item);

        //    quantity.setOnClickListener(this);
          //  increaseInteger.setOnClickListener(this);
        //    remove_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == quantity) {
                quantity.setCursorVisible(true);
            } else if (view == increaseInteger) {
                count = Integer.parseInt(quantity.getText().toString());
                count++;
                quantity.setText("" + count);
                quantity.setCursorVisible(true);
             //  int i1= Integer.parseInt(task.get(getAdapterPosition()).getPrice());
               int i1= Integer.parseInt(txt_price.getText().toString());
                i1=i1*count;
                txt_price.setText("र  "+i1);
               /* hashMap.put(""+task.get(getAdapterPosition()).getSid(),""+count);
                hashMapprice.put(""+task.get(getAdapterPosition()).getSid(),task.get(getAdapterPosition()).getPrice());*/

             //   Log.e("size------>",""+hashMap.size());

            } else if (view == remove_item) {
                count = Integer.parseInt(quantity.getText().toString());
                if (count > 0) {
                    count--;
                    quantity.setText("" + count);
                    quantity.setCursorVisible(true);
                //    int i1= Integer.parseInt(task.get(getAdapterPosition()).getPrice());
                    int i1= Integer.parseInt(txt_price.getText().toString());
                    i1=i1*count;
                    txt_price.setText("र  "+i1);
                  /*  hashMap.put(""+task.get(getAdapterPosition()).getSid(),""+count);
                    hashMapprice.put(""+task.get(getAdapterPosition()).getSid(),task.get(getAdapterPosition()).getPrice());
                    if (count==0){
                        hashMap.remove(task.get(getAdapterPosition()).getSid());
                        hashMapprice.remove(task.get(getAdapterPosition()).getSid());
                    }
                    Log.e("size----->",""+hashMap.size());*/
                }
            }
           /* String servicesId=task.get(getAdapterPosition()).get();
            saveData(activity,"service_id",servicesId);
            activity.startActivity(new Intent(activity, PanditBooking_Desc.class));
            Animatoo.animateZoom(activity);*/




        }
    }

    /*public void updateList(List<Get_SubCategory_Result> list) {
        task = list;
        notifyDataSetChanged();
    }*/
}