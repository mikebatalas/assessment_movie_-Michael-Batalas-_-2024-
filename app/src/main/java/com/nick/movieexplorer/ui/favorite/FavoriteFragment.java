package com.nick.movieexplorer.ui.favorite;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nick.movieexplorer.R;
import com.nick.movieexplorer.databinding.FragmentFavoriteBinding;
import com.nick.movieexplorer.databinding.FragmentMoviesBinding;
import com.nick.movieexplorer.domain.entity.Favorites;
import com.nick.movieexplorer.ui.favorite.adapter.FavoriteAdapter;
import com.nick.movieexplorer.ui.movies.adapter.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FavoriteViewModel mViewModel;
    private FavoriteAdapter adapter;
    private List<Favorites> favoritesList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(LayoutInflater.from(getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        initRcv();

        mViewModel.listLiveData.observe(requireActivity(), new Observer<List<Favorites>>() {
            @Override
            public void onChanged(List<Favorites> favorites) {
                favoritesList.clear();
                favoritesList.addAll(favorites);
                if (favoritesList.size()==0){
                    binding.noItemFound.setVisibility(View.VISIBLE);
                } else {
                    binding.noItemFound.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initRcv() {
        adapter = new FavoriteAdapter(requireContext(), favoritesList);
        binding.rcvFav.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rcvFav.setAdapter(adapter);
    }

}