package kr.ac.dankook.autoinfo.network;

import okhttp3.OkHttpClient;


public class HttpClient {
    private static OkHttpClient instance;

    public static OkHttpClient get() {
        if (instance == null) {
            instance = new OkHttpClient();
        }
        return instance;
    }
}
