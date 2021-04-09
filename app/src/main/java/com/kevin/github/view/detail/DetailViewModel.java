package com.kevin.github.view.detail;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kevin.github.helper.BaseConst;
import com.kevin.github.helper.Network;
import com.kevin.github.model.UserDetailResponse;
import com.kevin.github.helper.ApiService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {
    private final MutableLiveData<UserDetailResponse> detailUser = new MutableLiveData<>();

    public LiveData<UserDetailResponse> getDetailUser() {
        return detailUser;
    }

    public void setDetailUser(String username) {
        ApiService gitApiService = Network.build().create(ApiService.class);

        Call<UserDetailResponse> callAsync = gitApiService.getUserDetail(username, BaseConst.GITHUB_TOKEN);

        callAsync.enqueue(new Callback<UserDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserDetailResponse> call, @NonNull Response<UserDetailResponse> response) {
                if (response.body() != null) {
                    UserDetailResponse git = response.body();
                    Log.e("SUCCESS DETAIL", String.valueOf(response.body()));
                    detailUser.setValue(git);
                } else {
                    Log.e("ERROR DETAIL", "response body = null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserDetailResponse> call, @NonNull Throwable t) {
                Log.e("FAILURE DETAIL", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

}
