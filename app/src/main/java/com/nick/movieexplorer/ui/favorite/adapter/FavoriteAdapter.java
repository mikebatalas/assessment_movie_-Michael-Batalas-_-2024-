package com.nick.movieexplorer.ui.favorite.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nick.movieexplorer.databinding.ItemFavoriteBinding;
import com.nick.movieexplorer.databinding.ItemMoviesBinding;
import com.nick.movieexplorer.domain.entity.Favorites;
import com.nick.movieexplorer.ui.details.MovieDetailsActivity;
import com.nick.movieexplorer.ui.movies.adapter.MoviesAdapter;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<Favorites> favorites;
    private Context context;

    public FavoriteAdapter(Context context, List<Favorites> favorites){
        this.context = context;
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemFavoriteBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        holder.binding.tvMovieName.setText(favorites.get(position).getTitle());
        Glide.with(context).load(favorites.get(position).getPoster_path()).into(holder.binding.ivPoster);

        holder.binding.ivPoster.setOnClickListener(view -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("ID", ""+favorites.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemFavoriteBinding binding;
        public ViewHolder(@NonNull ItemFavoriteBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
