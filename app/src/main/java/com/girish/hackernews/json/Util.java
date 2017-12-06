package com.girish.hackernews.json;

import org.json.JSONObject;

/**
 * Created by Girish on 06-Dec-17.
 */

class Util {
    static boolean contains(JSONObject obj, String key) {
        return obj != null && obj.has(key) && !obj.isNull(key);
    }
}
