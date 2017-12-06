package com.girish.hackernews.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.girish.hackernews.callbacks.NewStoriesLoadedListener;
import com.girish.hackernews.extras.HackerNewsModel;
import com.girish.hackernews.extras.NewsUtils;
import com.girish.hackernews.network.VolleySingleton;

import java.util.List;

/**
 * Created by Girish on 06-Dec-17.
 */

public class TaskLoadNewStories extends AsyncTask<Void, Void, List<HackerNewsModel>> {

    private NewStoriesLoadedListener newStoriesLoadedListener;
    private VolleySingleton singleton;
    private RequestQueue requestQueue;

    public TaskLoadNewStories(NewStoriesLoadedListener listener) {
        newStoriesLoadedListener = listener;
        singleton = VolleySingleton.getInstance();
        requestQueue = singleton.getRequestQueue();
    }

    @Override
    protected List<HackerNewsModel> doInBackground(Void... voids) {
        List<HackerNewsModel> news = NewsUtils.loadNewStories(requestQueue);
        return news;
    }

    @Override
    protected void onPostExecute(List<HackerNewsModel> hackerNewsModels) {
        super.onPostExecute(hackerNewsModels);
        if (newStoriesLoadedListener != null)
            newStoriesLoadedListener.onNewStoriesLoaded(hackerNewsModels);
    }
}
