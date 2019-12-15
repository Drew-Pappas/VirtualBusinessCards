package com.example.virtualbusinesscards;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
        StorageReference profileImageReference = getImageReference(mData.get(position).getUserID()); //TODO Fix sending incorrect IDs
        downloadImage(profileImageReference, holder.imageViewFoundContactPicture);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactViewIntent = new Intent(mContext, contactViewActivity.class);
                contactViewIntent.putExtra("Name", mData.get(position).getUserName());
                contactViewIntent.putExtra("Email", mData.get(position).getUserEmail());
                contactViewIntent.putExtra("Phone", mData.get(position).getUserPhone());
                contactViewIntent.putExtra("Role", mData.get(position).getUserRole());
                contactViewIntent.putExtra("Org", mData.get(position).getUserOrg());
                contactViewIntent.putExtra("Location", mData.get(position).getUserLocation());
                contactViewIntent.putExtra("Bio", mData.get(position).getUserBio());

                mContext.startActivity(contactViewIntent);
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

    public void downloadImage(StorageReference reference, ImageView imageViewToRender){

        reference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes .length);
                imageViewToRender.setImageBitmap(bitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(mContext, "Didn't download image", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public StorageReference getImageReference(String userID){ //TODO Fix getting references happening before download can complete
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://virtualbusinesscards-96adf.appspot.com");
        StorageReference imagesRef = storageRef.child("images/" + userID);
        //Toast.makeText(mContext, userID, Toast.LENGTH_SHORT).show();
        return imagesRef;
    }


}