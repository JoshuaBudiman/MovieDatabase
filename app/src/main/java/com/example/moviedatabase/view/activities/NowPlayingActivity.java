package com.example.moviedatabase.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.moviedatabase.R;
import com.example.moviedatabase.adapter.NowPlayingAdapter;
import com.example.moviedatabase.model.NowPlaying;
import com.example.moviedatabase.viewmodel.MovieViewModel;

public class NowPlayingActivity extends AppCompatActivity {

    private RecyclerView rv_now_playing;
    private MovieViewModel view_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        rv_now_playing = findViewById(R.id.rv_now_playing);
        view_model = new ViewModelProvider(NowPlayingActivity.this).get(MovieViewModel.class);
        view_model.getNowPlaying();
        view_model.getResultNowPlaying().observe(NowPlayingActivity.this, showNowPlaying);


    }
    private Observer<NowPlaying> showNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            rv_now_playing.setLayoutManager(new LinearLayoutManager(NowPlayingActivity.this));
            NowPlayingAdapter adapter = new NowPlayingAdapter(NowPlayingActivity.this);
            adapter.setListNowPlaying(nowPlaying.getResults());
            rv_now_playing.setAdapter(adapter);

        }
    };
}