package com.girish.hackernews.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.girish.hackernews.callbacks.BestStoriesLoadedListener;
import com.girish.hackernews.extras.HackerNewsModel;
import com.girish.hackernews.extras.NewsUtils;
import com.girish.hackernews.network.VolleySingleton;

import java.util.List;

/**
 * Created by Girish on 06-Dec-17.
 */

public class TaskLoadBestStories extends AsyncTask<Void, Void, List<HackerNewsModel>> {

    private BestStoriesLoadedListener bestStoriesLoadedListener;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadBestStories(BestStoriesLoadedListener listener) {
        bestStoriesLoadedListener = listener;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected void onPostExecute(List<HackerNewsModel> hackerNewsModels) {
        super.onPostExecute(hackerNewsModels);

        if (bestStoriesLoadedListener != null)
            bestStoriesLoadedListener.onBestStoriesLoaded(hackerNewsModels);
    }

    @Override
    protected List<HackerNewsModel> doInBackground(Void... voids) {
        List<HackerNewsModel> news = NewsUtils.loadBestStories(requestQueue);
        return news;
    }
}
