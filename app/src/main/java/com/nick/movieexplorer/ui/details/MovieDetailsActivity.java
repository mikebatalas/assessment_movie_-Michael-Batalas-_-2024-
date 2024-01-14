package com.nick.movieexplorer.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nick.movieexplorer.R;
import com.nick.movieexplorer.databinding.ActivityMovieDetailsBinding;
import com.nick.movieexplorer.domain.entity.Favorites;
import com.nick.movieexplorer.ui.details.models.MovieDetails;
import com.nick.movieexplorer.ui.movies.model.Errors;
import com.nick.movieexplorer.utils.Utils;

public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding binding;
    private MovieDetailsViewModel viewModel;
    private MovieDetails movieDetailsData;
    KProgressHUD progressBar;
    private boolean isFavorite = false;
    private boolean valueBool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        showProgress();
        if (Utils.networkChecker(this)){
            viewModel.getMovieDetails(getIntent().getStringExtra("ID"));
        } else {
            Toast.makeText(this, "No Internet Available!", Toast.LENGTH_SHORT).show();
            progressBar.dismiss();
        }



        viewModel.movieDetails.observe(this, new Observer<MovieDetails>() {
            @Override
            public void onChanged(MovieDetails movieDetails) {
                movieDetailsData = movieDetails;
                binding.tvMovieName.setText(movieDetails.getTitle());
                binding.tvDescription.setText(movieDetails.getOverview());
                Glide.with(MovieDetailsActivity.this).load(movieDetails.getBackdropPath()).into(binding.ivPoster);
                valueBool = Utils.getFavorite(MovieDetailsActivity.this, movieDetails.getId().toString());
                showFav();
                progressBar.dismiss();

            }
        });

        viewModel.errorData.observe(this, new Observer<Errors>() {
            @Override
            public void onChanged(Errors errors) {
                Toast.makeText(MovieDetailsActivity.this, errors.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnFav.setOnClickListener(view -> {
            if (isFavorite == valueBool) {
                binding.btnFav.setImageResource(R.drawable.baseline_favorite_24);
                Utils.setFavorite(this, movieDetailsData.getId().toString(), true);
                viewModel.insertFavorite(new Favorites(movieDetailsData.getId(), movieDetailsData.getTitle(), movieDetailsData.getOverview(), movieDetailsData.getPosterPath()));
                isFavorite = true;
            } else {
                binding.btnFav.setImageResource(R.drawable.baseline_favorite_border_24);
                viewModel.removeFavorites(new Favorites(movieDetailsData.getId(), movieDetailsData.getTitle(), movieDetailsData.getOverview(), movieDetailsData.getPosterPath()));
                Utils.setFavorite(this, movieDetailsData.getId().toString(), false);
                isFavorite = false;
            }
        });

        binding.btnShare.setOnClickListener(view -> {
            String text = "See this movie: " + movieDetailsData.getTitle() + " " ;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, text + " " + movieDetailsData.getPosterPath());
            startActivity(Intent.createChooser(shareIntent, "Share link using"));
        });

        binding.ivBack.setOnClickListener(view -> finish());

    }

    private void showFav(){
        if (isFavorite != valueBool) {
            binding.btnFav.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            binding.btnFav.setImageResource(R.drawable.baseline_favorite_border_24);
        }
    }

    private void showProgress(){
        progressBar = KProgressHUD.create(MovieDetailsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
}