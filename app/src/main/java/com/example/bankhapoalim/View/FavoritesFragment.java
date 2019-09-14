package com.example.bankhapoalim.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.bankhapoalim.Model.Movie;
import com.example.bankhapoalim.R;
import com.example.bankhapoalim.ViewModel.MainViewModel;

import java.util.List;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.bankhapoalim.View.MoviesFragment.MOVIE_DATA;

public class FavoritesFragment extends Fragment {

    private MainViewModel mainViewModel;
    private ProgressBar progressBar;
    private ImageView noResultImage;

    public static void startFavoritesFragment(Activity activity, Bundle bundle) {
        NavController navController = Navigation.findNavController(activity, R.id.navHostFragment);
        navController.navigate(R.id.movieDetailsFragment, bundle,
                new NavOptions.Builder().setEnterAnim(R.anim.enter).setExitAnim(R.anim.exit)
                        .setLaunchSingleTop(true).build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies, container, false);

        progressBar = v.findViewById(R.id.progressBar) ;
        noResultImage = v.findViewById(R.id.noResultImage);
        RecyclerView moviesRecyclerView = v.findViewById(R.id.moviesRecyclerView);
        moviesRecyclerView.setHasFixedSize(true);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        final MovieAdapter adapter = new MovieAdapter();
        moviesRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                final Bundle bundle = new Bundle();
                bundle.putParcelable(MOVIE_DATA, movie);
                FavoritesFragment.startFavoritesFragment(getActivity(),bundle);
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.fetchFavoritesFromDB().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                progressBar.setVisibility(View.GONE);
                adapter.submitList(movies);
                noResultImage.setVisibility(movies.isEmpty() ? View.VISIBLE : View.GONE);
            }
        });
        return v;
    }
}
