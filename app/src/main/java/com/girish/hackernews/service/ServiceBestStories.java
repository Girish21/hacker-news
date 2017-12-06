package com.girish.hackernews.service;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.girish.hackernews.callbacks.BestStoriesLoadedListener;
import com.girish.hackernews.extras.HackerNewsModel;
import com.girish.hackernews.task.TaskLoadBestStories;

import java.util.List;

/**
 * Created by Girish on 06-Dec-17.
 */

public class ServiceBestStories extends JobService implements BestStoriesLoadedListener {

    private JobParameters parameters;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        parameters = jobParameters;
        new TaskLoadBestStories(this).execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public void onBestStoriesLoaded(List<HackerNewsModel> news) {
        jobFinished(parameters, false);
    }
}
