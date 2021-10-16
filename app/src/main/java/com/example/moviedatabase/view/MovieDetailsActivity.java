package com.example.moviedatabase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedatabase.R;
import com.example.moviedatabase.helper.Const;
import com.example.moviedatabase.model.Movies;
import com.example.moviedatabase.viewmodel.MovieViewModel;


public class MovieDetailsActivity extends AppCompatActivity {
    private MovieViewModel viewModel;
    private ImageView img_detail;
    private TextView lbl_title, lbl_rating, lbl_desc, lbl_date;
    private String movie_id,movie_title, movie_desc, movie_date,movie_rating, movie_img_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        lbl_title = findViewById(R.id.lbl_movie_details);
        lbl_desc = findViewById(R.id.lbl_movie_details_desc);
        lbl_rating = findViewById(R.id.lbl_movie_details_rating);
        lbl_date = findViewById(R.id.lbl_movie_details_release);
        img_detail=findViewById(R.id.img_poster_details);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");
        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showDetailsMovie);

    }

    private Observer<Movies> showDetailsMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            movie_title = movies.getTitle();
            movie_desc = movies.getOverview();
            movie_date = movies.getRelease_date();
            movie_rating = String.valueOf(movies.getVote_average());
            movie_img_path = Const.IMG_URL + movies.getPoster_path().toString();

            Glide.with(MovieDetailsActivity.this).load(movie_img_path).into(img_detail);
            lbl_title.setText(movie_title);
            lbl_desc.setText(movie_desc);
            lbl_date.setText(movie_date);
            lbl_rating.setText(movie_rating);

        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }
}