package com.kevin.github.view.followers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kevin.github.constant.BaseConst;
import com.kevin.github.helper.ServiceGenerator;
import com.kevin.github.model.UserResultResponse;
import com.kevin.github.service.GithubService;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersViewModel extends ViewModel {
    private final MutableLiveData<List<UserResultResponse>> searchUserInfo = new MutableLiveData<>();

    public LiveData<List<UserResultResponse>> getFollowersData() {
        return searchUserInfo;
    }

    public void setFollowersData(String username) {
        GithubService gitService = ServiceGenerator.build().create(GithubService.class);

        Call<List<UserResultResponse>> callAsync = gitService.getUserFollowers(username, BaseConst.GITHUB_TOKEN);

        callAsync.enqueue(new Callback<List<UserResultResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserResultResponse>> call, @NonNull Response<List<UserResultResponse>> response) {
                if (response.body() != null) {
                    searchUserInfo.setValue(response.body());
                } else {
                    Log.e("ERROR FOLLOWERS", "response body = null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserResultResponse>> call, @NonNull Throwable t) {
                Log.e("FAILURE FOLLOWERS", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
