package com.kevin.github.helper;

import com.kevin.github.constant.BaseConst;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static Retrofit build() {
        return new Retrofit.Builder()
                .baseUrl(BaseConst.GITHUB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}
