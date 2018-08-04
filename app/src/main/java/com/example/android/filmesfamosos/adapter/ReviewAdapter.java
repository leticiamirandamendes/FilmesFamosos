package com.example.android.filmesfamosos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.filmesfamosos.R;
import com.example.android.filmesfamosos.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final Review mLock = new Review();
    private List<Review> mObjects;

    public ReviewAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mObjects = new ArrayList<>();
    }

    public Review getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setReviewList(List<Review> reviewList){
        this.mObjects.clear();
        this.mObjects.addAll(reviewList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Review review = mObjects.get(position);
        holder.authorView.setText(review.getAuthor());
        holder.contentView.setText(Html.fromHtml(review.getContent()));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (mObjects ==null)? 0 : mObjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView authorView;
        public final TextView contentView;

        public ViewHolder(View view) {
            super(view);
            authorView =  view.findViewById(R.id.review_author);
            contentView =  view.findViewById(R.id.review_content);
        }
    }

}