package com.girish.hackernews.json;

import com.girish.hackernews.extras.HackerNewsModel;
import com.girish.hackernews.extras.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Girish on 05-Dec-17.
 */

public class Parser {

    public static List<Long> parseNewsIDJson(JSONArray list) {
        List<Long> newsId = new ArrayList<>();

        int size = 20;

        if (list != null) {
            if (list.length() < 20)
                size = list.length();

            for (int i = 0; i < size; i++) {
                try {
                    newsId.add(list.getLong(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return newsId;
    }

    public static HackerNewsModel parseNewsJson(JSONObject obj) {
        HackerNewsModel model = null;
        String title = "";
        String by = "";
        String url = "";
        long time = 0, id = 0;

        try {
            if (obj != null) {
                model = new HackerNewsModel();
                if (Util.contains(obj, Keys.KEY_ID))
                    id = obj.getLong(Keys.KEY_ID);
                if (Util.contains(obj, Keys.KEY_TIME))
                    time = obj.getLong(Keys.KEY_TIME);
                if (Util.contains(obj, Keys.KEY_BY))
                    by = obj.getString(Keys.KEY_BY);
                if (Util.contains(obj, Keys.KEY_TITLE))
                    title = obj.getString(Keys.KEY_TITLE);
                if (Util.contains(obj, Keys.KEY_URL))
                    url = obj.getString(Keys.KEY_URL);

                model.setBy(by);
                model.setTime(time);
                model.setTitle(title);
                model.setId(id);
                model.setUrl(url);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }
}
