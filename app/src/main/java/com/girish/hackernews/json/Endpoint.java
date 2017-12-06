package com.girish.hackernews.json;

import com.girish.hackernews.extras.URLEndpoints;

/**
 * Created by Girish on 05-Dec-17.
 */

public class Endpoint {
    public static String getURL(long id) {
        return URLEndpoints.URL_HACKER_NEWS_BASE + id + URLEndpoints.URL_EXTENSION;
    }
}
