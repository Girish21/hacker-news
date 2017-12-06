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

import com.girish.hackernews.MyApplication;
import com.girish.hackernews.R;
import com.girish.hackernews.adapter.RootRecyclerAdapter;
import com.girish.hackernews.callbacks.TopStoriesLoadedListener;
import com.girish.hackernews.database.DBNews;
import com.girish.hackernews.extras.HackerNewsModel;
import com.girish.hackernews.task.TaskLoadTopStories;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends Fragment implements TopStoriesLoadedListener, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    RootRecyclerAdapter adapter;
    List<HackerNewsModel> news = new ArrayList<>();
    ProgressDialog dialog;


    public TopStoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);

        recyclerView = view.findViewById(R.id.top_stories_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout = view.findViewById(R.id.refresh_top_stories_layout);
        refreshLayout.setOnRefreshListener(this);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Fetching news...");

        adapter = new RootRecyclerAdapter();

        recyclerView.setAdapter(adapter);
        dialog.show();

        news = MyApplication.getWritableDatabase().getNews(DBNews.TOP_STORIES);
        if (news.isEmpty())
            new TaskLoadTopStories(this).execute();
        adapter.setMovies(news);
        dialog.hide();

        return view;
    }

    @Override
    public void onTopStoriesLoaded(List<HackerNewsModel> news) {
        adapter.setMovies(news);
        try {
            if (dialog.isShowing())
                dialog.hide();

            if (refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        new TaskLoadTopStories(this).execute();
    }
}
