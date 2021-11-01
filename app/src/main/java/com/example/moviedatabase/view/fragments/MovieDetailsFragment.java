package com.example.moviedatabase.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviedatabase.R;
import com.example.moviedatabase.adapter.ProductionCompanyAdapter;
import com.example.moviedatabase.helper.Const;
import com.example.moviedatabase.helper.ItemClickSupport;
import com.example.moviedatabase.helper.LoadingDialog;
import com.example.moviedatabase.model.Movies;
import com.example.moviedatabase.viewmodel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();
    }

    private TextView lbl_movie_title, lbl_movie_tagline, lbl_movie_genres,
            lbl_movie_release_date,lbl_movie_vote,lbl_movie_popularity,lbl_movie_overview;
    private ImageView img_backdrop, img_poster;
    private MovieViewModel viewModel;
    private String movie_id, movie_title, movie_tagline,movie_genres,
            movie_date,movie_avgvote,movie_vote,movie_popularity,movie_overview,movie_backdrop_path,movie_poster_path;
    private RecyclerView rv_production_company;
    private LoadingDialog loadingDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        lbl_movie_title = view.findViewById(R.id.lbl_title_movie_details);
        lbl_movie_tagline = view.findViewById(R.id.lbl_tagline_movie_details);
        lbl_movie_genres = view.findViewById(R.id.lbl_genres_movie_details);
        lbl_movie_release_date = view.findViewById(R.id.lbl_release_date_movie_details);
        lbl_movie_vote = view.findViewById(R.id.lbl_rating_movie_details);
        lbl_movie_popularity = view.findViewById(R.id.lbl_popularity_movie_details);
        lbl_movie_overview = view.findViewById(R.id.lbl_overview_movie_details);
        img_backdrop = view.findViewById(R.id.img_backdrop_movie_details);
        img_poster = view.findViewById(R.id.img_poster_movie_details);

        movie_id = getArguments().getString("movieId");
        rv_production_company = view.findViewById(R.id.rv_production_company);
        viewModel = new ViewModelProvider(MovieDetailsFragment.this).get(MovieViewModel.class);
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(getActivity(), showMovieDetails);

        return view;
    }

    private Observer<Movies> showMovieDetails = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            movie_title = movies.getTitle();
            movie_tagline = movies.getTagline();
            movie_genres = "";
            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (movie_genres.equalsIgnoreCase("")){
                    movie_genres = movies.getGenres().get(i).getName();
                }else{
                    movie_genres +=","+movies.getGenres().get(i).getName();
                }
            }
            movie_date = movies.getRelease_date();
            movie_avgvote = String.valueOf(movies.getVote_average());
            movie_vote = String.valueOf(movies.getVote_count());
            movie_popularity = String.valueOf(movies.getPopularity());
            movie_overview = movies.getOverview();
            movie_backdrop_path = Const.IMG_URL + movies.getBackdrop_path();
            movie_poster_path = Const.IMG_URL + movies.getPoster_path().toString();

            Glide.with(getContext()).load(movie_backdrop_path).into(img_backdrop);
            Glide.with(getContext()).load(movie_poster_path).into(img_poster);
            lbl_movie_title.setText(movie_title);
            lbl_movie_tagline.setText(movie_tagline);
            lbl_movie_genres.setText("Genres: "+movie_genres);
            lbl_movie_release_date.setText("Release date: "+movie_date);
            lbl_movie_vote.setText("Avg.vote: "+movie_avgvote+" ("+movie_vote+")");
            lbl_movie_popularity.setText("Popularity: "+movie_popularity);
            lbl_movie_overview.setText(movie_overview);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_production_company.setLayoutManager(linearLayoutManager);
            ProductionCompanyAdapter adapter = new ProductionCompanyAdapter(getActivity());
            adapter.setListProductionCompany(movies.getProduction_companies());
            rv_production_company.setAdapter(adapter);

            loadingDialog.dissmissDialog();
            ItemClickSupport.addTo(rv_production_company).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Toast.makeText(getActivity(), movies.getProduction_companies().get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };


}