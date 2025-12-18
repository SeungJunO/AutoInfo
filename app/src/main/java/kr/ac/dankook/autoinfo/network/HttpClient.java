package kr.ac.dankook.autoinfo.network;

import okhttp3.OkHttpClient;

/**
 * OkHttpClient를 앱 전체에서 재사용하기 위한 싱글톤
 */
public class HttpClient {
    private static OkHttpClient instance;

    public static OkHttpClient get() {
        if (instance == null) {
            instance = new OkHttpClient();
        }
        return instance;
    }
}
