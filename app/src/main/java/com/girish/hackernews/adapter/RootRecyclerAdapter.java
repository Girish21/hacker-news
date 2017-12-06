package com.girish.hackernews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.girish.hackernews.R;
import com.girish.hackernews.extras.HackerNewsModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Girish on 06-Dec-17.
 */

public class RootRecyclerAdapter extends RecyclerView.Adapter<RootRecyclerAdapter.RootRecyclerViewHolder> {

    private List<HackerNewsModel> news = new ArrayList<>();
    private DateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public RootRecyclerAdapter() {}

    public void setMovies(List<HackerNewsModel> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    @Override
    public RootRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new RootRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RootRecyclerViewHolder holder, int position) {
        HackerNewsModel newsModel = news.get(position);

        if (newsModel.getTitle() != null)
            holder.titleTextView.setText(newsModel.getTitle());
        if (newsModel.getBy() != null)
            holder.byTextView.setText(newsModel.getBy());
        if (newsModel.getTime() != 0) {
            Date time = new Date(newsModel.getTime() * 1000);
            String formattedDate = mFormat.format(time);
            holder.dateTextView.setText(formattedDate);
        }
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class RootRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, dateTextView, byTextView;

        RootRecyclerViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.news_card_title);
            dateTextView = itemView.findViewById(R.id.news_card_time);
            byTextView = itemView.findViewById(R.id.news_card_by_name);
        }
    }
}
