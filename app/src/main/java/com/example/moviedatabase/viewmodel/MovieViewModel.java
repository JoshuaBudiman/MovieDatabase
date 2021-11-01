package com.example.moviedatabase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedatabase.model.Movies;
import com.example.moviedatabase.model.NowPlaying;
import com.example.moviedatabase.model.UpComing;
import com.example.moviedatabase.repositories.MovieRepository;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository repository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = MovieRepository.getInstance();
    }

    //==Begin of viewmodel get movie by id
    private MutableLiveData<Movies> resultGetMovieById = new MutableLiveData<>();
    public void getMovieById(String movieId){
        resultGetMovieById = repository.getMovieData(movieId);
    }
    public LiveData<Movies> getResultGetMovieById(){
        return resultGetMovieById;
    }
    //==End of viewmodel get movie by id


    //==Begin of viewmodel get now playing
    private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();
    public void getNowPlaying(){
        resultGetNowPlaying= repository.getNowPlayingData();
    }
    public LiveData<NowPlaying> getResultNowPlaying(){
        return resultGetNowPlaying;
    }
    //==End of viewmodel get now playing

    //==Begin of viewmodel get Upcoming
    private MutableLiveData<UpComing> resultGetUpComing = new MutableLiveData<>();
    public void getUpComing(){
        resultGetUpComing= repository.getUpComingData();
    }
    public LiveData<UpComing> getResulUpcoming(){
        return resultGetUpComing;
    }
    //==End of viewmodel get Upcoming
}
