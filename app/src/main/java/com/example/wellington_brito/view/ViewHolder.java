package com.example.wellington_brito.view;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wellington_brito.R;
import com.example.wellington_brito.adapters.RecyclerAdapter;
import com.example.wellington_brito.model.Repository;

import java.text.BreakIterator;
import java.util.List;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView fork;
    public TextView stars;
    public TextView userName;
    public TextView lastName;
    public TextView repositoryName;
    public TextView repositoryDescription;
    public TextView name;
    ImageView myImageView;
    public RecyclerAdapter.RepositoryListener listener;

    public ViewHolder(View itemView, List<Repository> itemsFiltered) {
        super(itemView);
        this.name = itemView.findViewById(R.id.tvName);
        this.fork = itemView.findViewById(R.id.textViewCountFork);
        this.stars = itemView.findViewById(R.id.textViewCountStar);
        this.userName = itemView.findViewById(R.id.textViewUserName);
        this.lastName = itemView.findViewById(R.id.textViewLastName);
        this.repositoryName = itemView.findViewById(R.id.textViewRepositoryName);
        this.repositoryDescription = itemView.findViewById(R.id.textViewRepositoryDescription);
        this.myImageView = (ImageView) itemView.findViewById(R.id.imageView);
        myImageView.setImageResource(R.drawable.ic_profile);

//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ViewHolder.this.listener.onSelected(itemsFiltered.get(getAdapterPosition()));
//            }
//        });
    }
}
