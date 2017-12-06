package com.girish.hackernews.service;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.girish.hackernews.callbacks.NewStoriesLoadedListener;
import com.girish.hackernews.extras.HackerNewsModel;
import com.girish.hackernews.task.TaskLoadNewStories;

import java.util.List;

/**
 * Created by Girish on 06-Dec-17.
 */

public class ServiceNewStories extends JobService implements NewStoriesLoadedListener {

    JobParameters parameters;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        parameters = jobParameters;
        new TaskLoadNewStories(this).execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public void onNewStoriesLoaded(List<HackerNewsModel> news) {
        jobFinished(parameters, false);
    }
}
