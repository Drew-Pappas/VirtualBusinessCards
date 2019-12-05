package com.example.virtualbusinesscards;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<QRSnapshot> mData ;


    public RecyclerViewAdapter(Context mContext, List<QRSnapshot> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_contact,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textViewFoundContactName.setText(mData.get(position).getUserName());
        holder.textViewFoundContactOrg.setText(mData.get(position).getUserOrg());
        holder.textViewFoundContactRole.setText(mData.get(position).getUserRole());
        holder.imageViewFoundContactPicture.setImageResource(R.drawable.ic_person_icon);



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Intent intent = new Intent(mContext,contactsActivity.class);
                // passing data to the book activity
                //intent.putExtra("Title",mData.get(position).getTitle());
                //intent.putExtra("Description",mData.get(position).getDescription());
                //intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                // start the activity
                //mContext.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewFoundContactName,
                textViewFoundContactOrg,
                textViewFoundContactRole;
        ImageView imageViewFoundContactPicture;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardViewContact);

            textViewFoundContactName = (TextView) itemView.findViewById(R.id.textViewFoundContactName);
            textViewFoundContactOrg = (TextView) itemView.findViewById(R.id.textViewFoundContactOrg);
            textViewFoundContactRole = (TextView) itemView.findViewById(R.id.textViewFoundContactRole);

            imageViewFoundContactPicture = (ImageView) itemView.findViewById(R.id.imageViewFoundContactPicture);




        }
    }



}