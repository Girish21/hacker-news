package com.girish.hackernews.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.girish.hackernews.MyApplication;
import com.girish.hackernews.R;
import com.girish.hackernews.activity.ViewNews;
import com.girish.hackernews.adapter.RootRecyclerAdapter;
import com.girish.hackernews.callbacks.BestStoriesLoadedListener;
import com.girish.hackernews.callbacks.ClickListener;
import com.girish.hackernews.database.DBNews;
import com.girish.hackernews.extras.CheckNetworkConnection;
import com.girish.hackernews.extras.HackerNewsModel;
import com.girish.hackernews.task.TaskLoadBestStories;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BestStoriesFragment extends Fragment implements BestStoriesLoadedListener, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    RootRecyclerAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    ProgressBar progressBar;

    TextView errorText;

    List<HackerNewsModel> news = new ArrayList<>();
    boolean isConnected = false;


    public BestStoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_best_stories, container, false);

        isConnected = CheckNetworkConnection.isInternetAvailable(getActivity());

        errorText = view.findViewById(R.id.best_stories_error_text);

        recyclerView = view.findViewById(R.id.best_stories_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = view.findViewById(R.id.best_stories_progress);

        refreshLayout = view.findViewById(R.id.refresh_best_stories_layout);
        refreshLayout.setOnRefreshListener(this);

        adapter = new RootRecyclerAdapter();

        recyclerView.setAdapter(adapter);

        news = MyApplication.getWritableDatabase().getNews(DBNews.BEST_STORIES);
        if (news.isEmpty() && isConnected)
            new TaskLoadBestStories(this).execute();
        adapter.setMovies(news);

        if (news.size() > 0 && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
            errorText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Toast.makeText(getActivity(), "Clicked: " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ViewNews.class);
                intent.putExtra("URL", news.get(position).getUrl());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        checkConnection();

        return view;
    }

    private void checkConnection() {
        if (!isConnected && news.size() == 0) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            errorText.setVisibility(View.VISIBLE);
            errorText.setText(R.string.network_refresh_message);

        }
        if (!isConnected)
            Toast.makeText(getActivity(), R.string.network_error_message, Toast.LENGTH_LONG).show();

        if (refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(false);

    }

    @Override
    public void onBestStoriesLoaded(List<HackerNewsModel> news) {
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
        if (isConnected) {
            new TaskLoadBestStories(this).execute();
        } else
            checkConnection();
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        ClickListener listener;
        GestureDetector gestureDetector;

        RecyclerTouchListener(Context context, RecyclerView recyclerView, ClickListener listener) {
            this.listener = listener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
//                    Log.i("Touch", "SingleTapTouchEvent " + e.toString());
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
//                    Log.i("Touch", "LongPressTouchEvent " + e.toString());
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//            Log.i("Touch", "InterceptTouchEvent " + gestureDetector.onTouchEvent(e) + " " + e.toString());
            View view = rv.findChildViewUnder(e.getX(), e.getY());
            if (listener != null && view != null && gestureDetector.onTouchEvent(e))
                listener.onClick(view, rv.getChildLayoutPosition(view));
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//            Log.i("Touch", "TouchEvent " + e.toString());
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
