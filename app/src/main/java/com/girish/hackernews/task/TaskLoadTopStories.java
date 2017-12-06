package com.girish.hackernews.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.girish.hackernews.callbacks.TopStoriesLoadedListener;
import com.girish.hackernews.extras.HackerNewsModel;
import com.girish.hackernews.extras.NewsUtils;
import com.girish.hackernews.network.VolleySingleton;

import java.util.List;

/**
 * Created by Girish on 06-Dec-17.
 */

public class TaskLoadTopStories extends AsyncTask<Void, Void, List<HackerNewsModel>> {

    private TopStoriesLoadedListener topStoriesLoadedListener;
    private VolleySingleton singleton;
    private RequestQueue requestQueue;

    public TaskLoadTopStories(TopStoriesLoadedListener listener) {
        topStoriesLoadedListener = listener;
        singleton = VolleySingleton.getInstance();
        requestQueue = singleton.getRequestQueue();
    }

    @Override
    protected List<HackerNewsModel> doInBackground(Void... voids) {
        List<HackerNewsModel> news = NewsUtils.loadTopStories(requestQueue);
        return news;
    }

    @Override
    protected void onPostExecute(List<HackerNewsModel> hackerNewsModels) {
        super.onPostExecute(hackerNewsModels);

        if (topStoriesLoadedListener != null)
            topStoriesLoadedListener.onTopStoriesLoaded(hackerNewsModels);
    }
}
