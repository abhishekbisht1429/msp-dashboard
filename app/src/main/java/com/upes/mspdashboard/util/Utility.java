package com.upes.mspdashboard.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

    public static boolean checkExtStorageWritePermission(Context context) {
        return ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestExtStorageWritePermission(Fragment frag) {
        frag.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},GlobalConstants.RC_WRITE_EXTERNAL_STORAGE);
    }
}
