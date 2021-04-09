package com.wahyu.githubapi.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wahyu.githubapi.constant.Base;
import com.wahyu.githubapi.helper.ServiceGenerator;
import com.wahyu.githubapi.Model.UserDetails;
import com.wahyu.githubapi.service.GithubService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wahyu_septiadi on 29, June 2020.
 * Visit My GitHub --> https://github.com/WahyuSeptiadi
 */

public class DetailViewModel extends ViewModel {
    private final MutableLiveData<UserDetails> detailUser = new MutableLiveData<>();

    public LiveData<UserDetails> getDetailUser() {
        return detailUser;
    }

    public void setDetailUser(String username) {
        GithubService gitService = ServiceGenerator.build().create(GithubService.class);

        Call<UserDetails> callAsync = gitService.getUserDetail(username, Base.GITHUB_TOKEN);

        callAsync.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(@NonNull Call<UserDetails> call, @NonNull Response<UserDetails> response) {
                if (response.body() != null) {
                    UserDetails git = response.body();
                    Log.e("SUKSES DETAIL", String.valueOf(response.body()));
                    detailUser.setValue(git);
                } else {
                    Log.e("ERROR DETAIL", "response body = null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserDetails> call, @NonNull Throwable t) {
                Log.e("FAILURE DETAIL", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

}
