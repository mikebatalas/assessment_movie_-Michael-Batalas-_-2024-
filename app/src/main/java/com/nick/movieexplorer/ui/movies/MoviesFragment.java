package com.nick.movieexplorer.ui.movies;

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
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.nick.movieexplorer.R;
import com.nick.movieexplorer.databinding.FragmentMoviesBinding;
import com.nick.movieexplorer.ui.details.MovieDetailsActivity;
import com.nick.movieexplorer.ui.movies.adapter.MoviesAdapter;
import com.nick.movieexplorer.ui.movies.model.Errors;
import com.nick.movieexplorer.ui.movies.model.MoviesData;
import com.nick.movieexplorer.ui.movies.model.MoviesItemData;
import com.nick.movieexplorer.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment {

    private FragmentMoviesBinding binding;
    private MoviesViewModel mViewModel;
    private MoviesAdapter adapter;
    private List<MoviesItemData> moviesDataList = new ArrayList<>();
    KProgressHUD progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMoviesBinding.inflate(LayoutInflater.from(getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);

        initRcv();
        showProgress();
        if (Utils.networkChecker(getActivity())){
            mViewModel.getMovies();
        } else {
            Toast.makeText(getContext(), "No Internet Available!", Toast.LENGTH_SHORT).show();
            progressBar.dismiss();
        }


        mViewModel.movieDataList.observe(getViewLifecycleOwner(), new Observer<MoviesData>() {
            @Override
            public void onChanged(MoviesData moviesData) {
                moviesDataList.clear();
                moviesDataList.addAll(moviesData.getMoviesItemData());
                adapter.notifyDataSetChanged();
                progressBar.dismiss();
            }
        });

        mViewModel.errorData.observe(this, new Observer<Errors>() {
            @Override
            public void onChanged(Errors errors) {
                Toast.makeText(requireActivity(), errors.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initRcv() {
        adapter = new MoviesAdapter(requireContext(), moviesDataList);
        binding.rcvMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rcvMovies.setAdapter(adapter);
    }

    private void showProgress(){
        progressBar = KProgressHUD.create(requireActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

}