package com.lepak.surahviewer.service;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIClient {

    private static OkHttpClient _client = null;

    public static OkHttpClient getClient() {
        if (_client == null)
            _client = new OkHttpClient();

        return _client;
    }

    public static String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        try (Response response = getClient().newCall(request).execute()) {
            return response.body().string();
        }
    }
}
