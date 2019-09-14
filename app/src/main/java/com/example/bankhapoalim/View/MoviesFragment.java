package com.example.bankhapoalim.View;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesFragment extends Fragment {

    private MainViewModel mainViewModel;
    public static String MOVIE_DATA = "movie_data";
    private ProgressBar progressBar;
    private ImageView noResultImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies, container, false);

        RecyclerView moviesRecyclerView = v.findViewById(R.id.moviesRecyclerView);
        moviesRecyclerView.setHasFixedSize(true);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        progressBar = v.findViewById(R.id.progressBar);
        noResultImage = v.findViewById(R.id.noResultImage);


        final MovieAdapter adapter = new MovieAdapter();
        moviesRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                final Bundle bundle = new Bundle();
                bundle.putParcelable(MOVIE_DATA, movie);
                FavoritesFragment.startFavoritesFragment(getActivity(), bundle);
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.fetchMovies().observe(this, new Observer<List<Movie>>() {
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
