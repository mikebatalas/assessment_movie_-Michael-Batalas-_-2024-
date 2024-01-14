package com.nick.movieexplorer.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.nick.movieexplorer.R;
import com.nick.movieexplorer.databinding.ActivityHomeBinding;
import com.nick.movieexplorer.ui.favorite.FavoriteFragment;
import com.nick.movieexplorer.ui.movies.MoviesFragment;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    Fragment firstFragment;
    Fragment secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firstFragment = new MoviesFragment();
        secondFragment = new FavoriteFragment();

        setCurrentFragment(firstFragment);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.dashboard) {
                Log.e("SMD", "onNavigationItemSelected: click 1");
                setCurrentFragment(firstFragment);
                return true;
            } else if (itemId == R.id.history) {
                Log.e("SMD", "onNavigationItemSelected: click 2");
                setCurrentFragment(secondFragment);
                return true;
            }
            return false;
        });
    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment,fragment)
                .commit();
    }
}