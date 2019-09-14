package com.example.bankhapoalim.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites_movie_table")
public class Movie implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    private long id;
    @SerializedName("vote_average")
    private String rating;
    @SerializedName("original_title")
    private String title;
    @SerializedName("overview")
    private String description;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("poster_path")
    private String image;

    public Movie(){

    }

    public Movie(long id,
                 String voteAverage,
                 String originalTitle,
                 String overview,
                 String releaseDate,
                 String posterPath)
    {
        this.id = id;
        this.rating = voteAverage;
        this.title = originalTitle;
        this.description = overview;
        this.releaseDate = releaseDate;
        this.image = posterPath;
    }

    protected Movie(Parcel in){
        id = in.readLong();
        rating = in.readString();
        title = in.readString();
        description = in.readString();
        releaseDate = in.readString();
        image = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
          return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(rating);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(releaseDate);
        parcel.writeString(image);
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Nullable
    public String getImage() {
        if (image != null && !image.isEmpty()) {

            if(!image.toLowerCase().startsWith("http")){
                return "https://image.tmdb.org/t/p/w342" + image;
            }else{
                return image;
            }

        }
        return null;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                Objects.equals(rating, movie.rating) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(image, movie.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, title, description, releaseDate, image);
    }
}
