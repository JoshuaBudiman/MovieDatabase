package com.example.moviedatabase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedatabase.R;
import com.example.moviedatabase.helper.Const;
import com.example.moviedatabase.model.Movies;
import com.example.moviedatabase.model.NowPlaying;

import java.util.List;

public class ProductionCompanyAdapter extends RecyclerView.Adapter<ProductionCompanyAdapter.CardViewViewHolder> {
    private Context context;
    private List<Movies.ProductionCompanies> listProductionCompany;
    private List<Movies.ProductionCompanies> getListProductionCompany(){return listProductionCompany;}
    public void setListProductionCompany(List<Movies.ProductionCompanies> listProductionCompany){
        this.listProductionCompany = listProductionCompany;
    }
    public ProductionCompanyAdapter (Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ProductionCompanyAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_production_company,parent,false);

        return new ProductionCompanyAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionCompanyAdapter.CardViewViewHolder holder, int position) {
        final Movies.ProductionCompanies results = getListProductionCompany().get(position);
        Glide.with(context).load(Const.IMG_URL + results.getLogo_path()).into(holder.img_company);
    }

    @Override
    public int getItemCount() {
        return getListProductionCompany().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img_company;
        CardView cardView;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_production_company);
            img_company = itemView.findViewById(R.id.img_production_company);
        }
    }
}
