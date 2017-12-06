package com.girish.hackernews.json;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Girish on 05-Dec-17.
 */

public class Requester {

    public static JSONArray requestCategoryJSON(RequestQueue requestQueue, String url) {
        JSONArray response = null;
        RequestFuture<JSONArray> requestFuture = RequestFuture.newFuture();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                requestFuture,
                requestFuture
        );
        requestQueue.add(request);

        try {
            response = requestFuture.get(1800000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static JSONObject requestNews(RequestQueue requestQueue, String url) {
        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                requestFuture,
                requestFuture
        );

        requestQueue.add(request);

        try {
            response = requestFuture.get(1800000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        return response;
    }
}
