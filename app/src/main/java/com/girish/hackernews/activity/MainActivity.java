package com.girish.hackernews.activity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;

import com.girish.hackernews.R;
import com.girish.hackernews.fragments.BestStoriesFragment;
import com.girish.hackernews.fragments.NewStoriesFragment;
import com.girish.hackernews.fragments.TopStoriesFragment;
import com.girish.hackernews.service.ServiceBestStories;
import com.girish.hackernews.service.ServiceNewStories;
import com.girish.hackernews.service.ServiceTopStories;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private JobScheduler scheduler;

    //    private static final long POLL_FREQUENCY = 1800000;
    private static final long POLL_FREQUENCY = 300000;


    private static final int TOP_STORIES_JOB_ID = 100;
    private static final int NEW_STORIES_JOB_ID = 101;
    private static final int BEST_STORIES_JOB_ID = 102;

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.root_toolbar);
        tabLayout = findViewById(R.id.root_tab_layout);
        viewPager = findViewById(R.id.root_pager);

        setSupportActionBar(toolbar);

        List<Fragment> tabs = new ArrayList<>();
        tabs.add(new TopStoriesFragment());
        tabs.add(new NewStoriesFragment());
        tabs.add(new BestStoriesFragment());

        RootPagerAdapter adapter = new RootPagerAdapter(getSupportFragmentManager(), tabs);

        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        scheduleJob();

    }

    private void scheduleJob() {
        scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                buildJob();
            }
        }, 20000);
    }

    private void buildJob() {
        JobInfo.Builder builderTopStories = new JobInfo.Builder(TOP_STORIES_JOB_ID,
                new ComponentName(this, ServiceTopStories.class));
        JobInfo.Builder builderNewStories = new JobInfo.Builder(NEW_STORIES_JOB_ID,
                new ComponentName(this, ServiceNewStories.class));
        JobInfo.Builder builderBestStories = new JobInfo.Builder(BEST_STORIES_JOB_ID,
                new ComponentName(this, ServiceBestStories.class));

        builderTopStories.setPeriodic(POLL_FREQUENCY)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true);
        builderNewStories.setPeriodic(POLL_FREQUENCY)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true);
        builderBestStories.setPeriodic(POLL_FREQUENCY)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true);

        scheduler.schedule(builderTopStories.build());
        scheduler.schedule(builderNewStories.build());
        scheduler.schedule(builderBestStories.build());

        clearCache(this);
    }

    private void clearCache(Context context) {
        clearCacheFolder(context.getCacheDir());
    }

    private void clearCacheFolder(File cacheDir) {
        if (cacheDir != null && cacheDir.isDirectory()) {
            long currentTime = new Date().getTime();
            try {
                for (File child : cacheDir.listFiles()) {
                    if (child.isDirectory())
                        clearCacheFolder(child);
                    if (child.lastModified() < currentTime - DateUtils.DAY_IN_MILLIS)
                        child.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class RootPagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> tabs;

        RootPagerAdapter(FragmentManager fm, List<Fragment> tabs) {
            super(fm);
            this.tabs = tabs;
        }

        @Override
        public Fragment getItem(int position) {
            return tabs.get(position);
        }

        @Override
        public int getCount() {
            return tabs.size();
        }
    }
}
