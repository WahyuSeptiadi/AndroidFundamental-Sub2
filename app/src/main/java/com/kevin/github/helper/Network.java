package com.kevin.github.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    public static Retrofit build() {
        return new Retrofit.Builder()
                .baseUrl(BaseConst.GITHUB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}
