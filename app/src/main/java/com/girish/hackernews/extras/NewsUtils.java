package com.girish.hackernews.extras;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.girish.hackernews.MyApplication;
import com.girish.hackernews.database.DBNews;
import com.girish.hackernews.json.Endpoint;
import com.girish.hackernews.json.Parser;
import com.girish.hackernews.json.Requester;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Girish on 06-Dec-17.
 */

public class NewsUtils {

    public static List<HackerNewsModel> loadBestStories(RequestQueue requestQueue) {
        List<HackerNewsModel> response = new ArrayList<>();
        JSONArray jsonArray = Requester.requestCategoryJSON(requestQueue, URLEndpoints.URL_HACKER_NEWS_BEST_STORIES);
        List<Long> newsId = Parser.parseNewsIDJson(jsonArray);
        for (long id : newsId) {
            String url = Endpoint.getURL(id);
            JSONObject object = Requester.requestNews(requestQueue, url);
            response.add(Parser.parseNewsJson(object));
        }
        if (response.size() > 0) {
            try {
                MyApplication.getWritableDatabase().insertNews(DBNews.BEST_STORIES, response, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public static List<HackerNewsModel> loadNewStories(RequestQueue requestQueue) {
        List<HackerNewsModel> response = new ArrayList<>();
        JSONArray jsonArray = Requester.requestCategoryJSON(requestQueue, URLEndpoints.URL_HACKER_NEWS_NEW_STORIES);
        List<Long> newsId = Parser.parseNewsIDJson(jsonArray);
        for (long id : newsId) {
            String url = Endpoint.getURL(id);
            JSONObject object = Requester.requestNews(requestQueue, url);
            response.add(Parser.parseNewsJson(object));
        }
        if (response.size() > 0) {
            try {
                MyApplication.getWritableDatabase().insertNews(DBNews.NEW_STORIES, response, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public static List<HackerNewsModel> loadTopStories(RequestQueue requestQueue) {
        List<HackerNewsModel> response = new ArrayList<>();
        JSONArray jsonArray = Requester.requestCategoryJSON(requestQueue, URLEndpoints.URL_HACKER_NEWS_TOP_STORIES);
        List<Long> newsId = Parser.parseNewsIDJson(jsonArray);
        for (long id : newsId) {
            String url = Endpoint.getURL(id);
            JSONObject object = Requester.requestNews(requestQueue, url);
            response.add(Parser.parseNewsJson(object));
        }
        if (response.size() > 0) {
            try {
                MyApplication.getWritableDatabase().insertNews(DBNews.TOP_STORIES, response, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

}
