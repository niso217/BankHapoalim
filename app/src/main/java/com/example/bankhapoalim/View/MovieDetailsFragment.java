package com.example.bankhapoalim.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.bumptech.glide.Glide;
import com.example.bankhapoalim.Model.Movie;
import com.example.bankhapoalim.R;
import com.example.bankhapoalim.ViewModel.MainViewModel;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static com.example.bankhapoalim.View.MoviesFragment.MOVIE_DATA;

public class MovieDetailsFragment extends Fragment {

    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getArguments() != null) {
            final Movie movie = getArguments().getParcelable(MOVIE_DATA);
            if (movie != null) {
                View v = inflater.inflate(R.layout.fragment_details, container, false);

                final ImageView movieImage = v.findViewById(R.id.movieImage);
                final TextView movieTitle = v.findViewById(R.id.movieTitle);
                final TextView movieYear = v.findViewById(R.id.movieYear);
                final RatingBar movieRating = v.findViewById(R.id.movieRating);
                final TextView movieDescription = v.findViewById(R.id.movieDescription);
                final ToggleButton toggleButton = v.findViewById(R.id.buttonFavorite);
                mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
                toggleButton.setChecked(mainViewModel.find(movie.getId()));


                toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (compoundButton.isChecked()) {
                            mainViewModel.insert(movie);
                        } else {
                            mainViewModel.delete(movie);
                        }
                    }

                });
                Glide.with(getContext()).load(movie.getImage()).into(movieImage);
                movieTitle.setText(movie.getTitle());
                movieYear.setText(movie.getReleaseDate());
                movieRating.setRating(Float.valueOf(movie.getRating()));
                movieDescription.setText(movie.getDescription());
                return v;
            }
        }

        return null;
    }

}
