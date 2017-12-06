package com.girish.hackernews.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.girish.hackernews.MyApplication;
import com.girish.hackernews.R;
import com.girish.hackernews.adapter.RootRecyclerAdapter;
import com.girish.hackernews.callbacks.NewStoriesLoadedListener;
import com.girish.hackernews.database.DBNews;
import com.girish.hackernews.extras.HackerNewsModel;
import com.girish.hackernews.task.TaskLoadNewStories;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewStoriesFragment extends Fragment implements NewStoriesLoadedListener, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    RootRecyclerAdapter adapter;
    ProgressBar progressBar;

    List<HackerNewsModel> news = new ArrayList<>();


    public NewStoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_stories, container, false);

        recyclerView = view.findViewById(R.id.new_stories_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = view.findViewById(R.id.new_stories_progress);

        refreshLayout = view.findViewById(R.id.refresh_new_stories_layout);
        refreshLayout.setOnRefreshListener(this);

        adapter = new RootRecyclerAdapter();

        recyclerView.setAdapter(adapter);

        news = MyApplication.getWritableDatabase().getNews(DBNews.NEW_STORIES);
        if (news.isEmpty())
            new TaskLoadNewStories(this).execute();
        adapter.setMovies(news);

        return view;
    }

    @Override
    public void onNewStoriesLoaded(List<HackerNewsModel> news) {
        adapter.setMovies(news);
        try {
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            if (refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        new TaskLoadNewStories(this).execute();
    }
}
