package com.tony.router.util;

import android.net.Uri;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by tony on 10/26/16.
 */
public class RouterUtils {
    private static final String TAG = "RouterUtils";

    public static String getScheme(String url) {
        return Uri.parse(url).getScheme();
    }

    public static String getHost(String url) {
        return Uri.parse(url).getHost();
    }

    public static List<String> getPathSegments(String url) {
        return Uri.parse(url).getPathSegments();
    }

    public static int getPort(String url) {
        return Uri.parse(url).getPort();
    }

    public static HashMap<String, String> getQueryParameter(String url) {
        HashMap<String, String> parameters = new HashMap<>();
        try {
            Uri uri = Uri.parse(url);
            Set<String> keys = uri.getQueryParameterNames();
            for (String key : keys) {
                parameters.put(key, uri.getQueryParameter(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parameters;
    }

    public static String addQueryParameters(String url, String key, String value){
        try{
            Uri uri = Uri.parse(url);
            return uri.buildUpon().appendQueryParameter(key, value).build().toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }
}
