package com.example.wellington_brito.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wellington_brito.R;
import com.example.wellington_brito.model.Repository;
import com.example.wellington_brito.view.MostStarredHome;
import com.example.wellington_brito.view.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Repository> repositories;
    Context context;

    public RecyclerAdapter(Context context, List<Repository> repositories ) {
        this.context = context;
        this.repositories = repositories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.repository_adapter_item_list,viewGroup, false);
        return new ViewHolder(view, this.repositories);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Repository repositorie = repositories.get(position);
        holder.repositoryName.setText(repositorie.getRepositoryName());
        holder.repositoryDescription.setText(repositorie.getRepositoryDescricpiton());
        holder.fork.setText(repositorie.getCountForks());
        holder.stars.setText(repositorie.getCountStars());
        holder.userName.setText(repositorie.getUsername());
        holder.lastName.setText(repositorie.getLastName());


        //holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }



//    public List<Repository> getListRepoFiltered(){
//        return filtrado ? itemsFiltered : repositories;
//    }

    public interface RepositoryListener {
        void onSelected(Repository item);
    }


}
