package com.girish.hackernews.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.girish.hackernews.MyApplication;
import com.girish.hackernews.R;
import com.girish.hackernews.adapter.RootRecyclerAdapter;
import com.girish.hackernews.callbacks.TopStoriesLoadedListener;
import com.girish.hackernews.database.DBNews;
import com.girish.hackernews.extras.CheckNetworkConnection;
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
    ProgressBar progressBar;

    TextView errorText;

    List<HackerNewsModel> news = new ArrayList<>();
    boolean isConnected = false;


    public TopStoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);

        isConnected = CheckNetworkConnection.isInternetAvailable(getActivity());

        errorText = view.findViewById(R.id.top_stories_error_text);

        recyclerView = view.findViewById(R.id.top_stories_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = view.findViewById(R.id.top_stories_progress);

        refreshLayout = view.findViewById(R.id.refresh_top_stories_layout);
        refreshLayout.setOnRefreshListener(this);

        adapter = new RootRecyclerAdapter();

        recyclerView.setAdapter(adapter);

        news = MyApplication.getWritableDatabase().getNews(DBNews.TOP_STORIES);
        if (news.isEmpty() && isConnected)
            new TaskLoadTopStories(this).execute();
        adapter.setMovies(news);

        if (news.size() > 0 && progressBar.getVisibility() == View.VISIBLE) {
            errorText.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        checkConnection();

        return view;
    }

    private void checkConnection() {
        if (!isConnected && news.size() == 0) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            errorText.setVisibility(View.VISIBLE);
            errorText.setText(R.string.network_refresh_message);

        } else if (!isConnected)
            Toast.makeText(getActivity(), R.string.network_error_message, Toast.LENGTH_LONG).show();

        if (refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(false);

    }

    @Override
    public void onTopStoriesLoaded(List<HackerNewsModel> news) {
        if (news.size() > 0) {
            adapter.setMovies(news);
        }
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
        isConnected = CheckNetworkConnection.isInternetAvailable(getActivity());
        if (isConnected)
            new TaskLoadTopStories(this).execute();
        else
            checkConnection();
    }
}
