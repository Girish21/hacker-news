package com.girish.hackernews.json;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.girish.hackernews.R;

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
            response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            if (e.getCause() instanceof VolleyError) {
                VolleyError error = (VolleyError) e.getCause();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    mTextError.setText(R.string.error_timeout);
                } else if (error instanceof AuthFailureError) {
//                    mTextError.setText(R.string.error_auth_failure);
                } else if (error instanceof ServerError) {
//                    mTextError.setText(R.string.error_auth_failure);
                } else if (error instanceof NetworkError) {
//                    mTextError.setText(R.string.error_network);
                } else if (error instanceof ParseError) {
//                    mTextError.setText(R.string.error_parser);
                }
            }
            e.printStackTrace();
            return null;
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
            response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            if (e.getCause() instanceof VolleyError) {
                VolleyError error = (VolleyError) e.getCause();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    mTextError.setText(R.string.error_timeout);
                } else if (error instanceof AuthFailureError) {
//                    mTextError.setText(R.string.error_auth_failure);
                } else if (error instanceof ServerError) {
//                    mTextError.setText(R.string.error_auth_failure);
                } else if (error instanceof NetworkError) {
//                    mTextError.setText(R.string.error_network);
                } else if (error instanceof ParseError) {
//                    mTextError.setText(R.string.error_parser);
                }
            }
            e.printStackTrace();
            return null;
        }

        return response;
    }
}
