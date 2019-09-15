package com.upes.mspdashboard.util;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.HashMap;
import java.util.Map;

public class Utility {
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static Map<String,String> authHeader(Context context) {
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization","Token "+SessionManager.getInstance(context).getAuthToken());
        return headers;
    }
}
