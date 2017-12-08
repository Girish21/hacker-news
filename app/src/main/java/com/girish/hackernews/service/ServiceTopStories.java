package com.girish.hackernews.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.girish.hackernews.callbacks.TopStoriesLoadedListener;
import com.girish.hackernews.extras.HackerNewsModel;
import com.girish.hackernews.task.TaskLoadTopStories;

import java.util.List;

/**
 * Created by Girish on 06-Dec-17.
 */

public class ServiceTopStories extends JobService implements TopStoriesLoadedListener {

    JobParameters parameters;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        parameters = jobParameters;
        Log.i("Job", "Top");
        new TaskLoadTopStories(this).execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public void onTopStoriesLoaded(List<HackerNewsModel> news) {
        jobFinished(parameters, false);
    }
}
