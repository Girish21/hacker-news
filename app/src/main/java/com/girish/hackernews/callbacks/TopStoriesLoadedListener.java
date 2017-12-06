package com.girish.hackernews.callbacks;

import com.girish.hackernews.extras.HackerNewsModel;

import java.util.List;

/**
 * Created by Girish on 05-Dec-17.
 */

public interface TopStoriesLoadedListener {
    public void onTopStoriesLoaded(List<HackerNewsModel> news);
}
