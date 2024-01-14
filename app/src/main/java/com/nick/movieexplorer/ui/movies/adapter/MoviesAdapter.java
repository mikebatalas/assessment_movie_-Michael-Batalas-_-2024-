package com.nick.movieexplorer.ui.movies.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nick.movieexplorer.databinding.ItemMoviesBinding;
import com.nick.movieexplorer.ui.details.MovieDetailsActivity;
import com.nick.movieexplorer.ui.movies.model.MoviesData;
import com.nick.movieexplorer.ui.movies.model.MoviesItemData;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private List<MoviesItemData> moviesData;
    private Context context;

    public MoviesAdapter(Context context, List<MoviesItemData> moviesData){
        this.moviesData = moviesData;
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemMoviesBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.tvMovieName.setText(moviesData.get(position).getTitle());
        Glide.with(context).load(moviesData.get(position).getPosterPath()).into(holder.binding.ivPoster);

        holder.binding.ivPoster.setOnClickListener(view -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("ID", moviesData.get(position).getId().toString());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemMoviesBinding binding;
        public ViewHolder(ItemMoviesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

    }
}
