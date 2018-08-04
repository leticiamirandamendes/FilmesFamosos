package com.example.android.filmesfamosos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.filmesfamosos.R;
import com.example.android.filmesfamosos.model.Trailer;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailer> mObjects;
    private final LayoutInflater mInflater;
    private Context mContext;
    private static ListTrailerClickListener mOnClickListener;



    public TrailerAdapter(Context context, ListTrailerClickListener listener) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mObjects = new ArrayList<>();
        mOnClickListener = listener;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.trailler_item,parent,false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        final Trailer video = mObjects.get(position);
        holder.setTrailerText(video.getName());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (mObjects ==null)? 0 : mObjects.size();
    }

    public void setTrailerList(List<Trailer> trailerList){
        this.mObjects.clear();
        this.mObjects.addAll(trailerList);
        notifyDataSetChanged();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView name;


        public TrailerViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.trailer_movie_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Trailer selectedTrailer = mObjects.get(clickedPosition);
            Log.e("URL:", selectedTrailer.getSite());
            mOnClickListener.onListTrailerClick(selectedTrailer);
        }

        void setTrailerText(String text) {
            name.setText(text);
        }
    }

    public interface ListTrailerClickListener{
        void onListTrailerClick(Trailer selected);
    }

}