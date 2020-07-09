package com.example.poketlearning;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LearningAdapter extends RecyclerView.Adapter<LearningAdapter.LearningViewHolder>{
    ArrayList<PoketLearning> learning;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    public LearningAdapter() {
        //FirebaseUtil.openFbReference("poketlearning");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        learning = FirebaseUtil.mLearning;
        mChildEventListener = new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                PoketLearning pl = snapshot.getValue(PoketLearning.class);
                Log.d("Learning: ", pl.getTitle());
                pl.setId(snapshot.getKey());
                learning.add(pl);
                notifyItemInserted(learning.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }
    @NonNull
    @Override
    public LearningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_row, parent, false);
        return new LearningViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LearningViewHolder holder, int position) {
        PoketLearning learn = learning.get(position);
        holder.bind(learn);
    }

    @Override
    public int getItemCount() {
        return learning.size();
    }

    public class LearningViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {



        TextView tvTitle;
        TextView tvDescription;
        TextView tvPrice;

        public LearningViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);

            itemView.setOnClickListener(this);
        }

        public void bind(PoketLearning learning) {
            tvTitle.setText((learning.getTitle()));
            tvDescription.setText(learning.getDescription());
            tvPrice.setText(learning.getPrice());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("Click", String.valueOf(position));
            PoketLearning selectedLearning = learning.get(position);
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra("Learning", selectedLearning);

            view.getContext().startActivity(intent);

        }
    }

}
