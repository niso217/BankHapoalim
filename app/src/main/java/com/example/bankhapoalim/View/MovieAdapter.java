package com.example.bankhapoalim.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.bankhapoalim.Model.Movie;
import com.example.bankhapoalim.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends ListAdapter<Movie, MovieAdapter.MovieHolder> {

    private OnItemClickListener listener;

    public MovieAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Movie>DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(Movie oldItem,Movie newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Movie oldItem,Movie newItem) {
            return oldItem.equals(oldItem);
        }
    };
    @NonNull
    @Override
    public MovieAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieHolder holder, int position) {
        Movie currentMovie = getItem(position);
        Glide.with(holder.image.getContext()).load(currentMovie.getImage()).into(holder.image);
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        public MovieHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.movieImage);
            image.requestLayout();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}