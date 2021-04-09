package com.kevin.github.view.search;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kevin.github.constant.Base;
import com.kevin.github.helper.ServiceGenerator;
import com.kevin.github.model.SearchUser;
import com.kevin.github.service.GithubService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private final MutableLiveData<SearchUser> searchUser = new MutableLiveData<>();

    public void setSearchData(String username) {
        loadJSON(username);
    }

    public LiveData<SearchUser> getSearchData() {
        return searchUser;
    }

    public void loadJSON(String username) {
        GithubService gitService = ServiceGenerator.build().create(GithubService.class);

        Call<SearchUser> callAsync = gitService.cariUser(username, Base.GITHUB_TOKEN);

        callAsync.enqueue(new Callback<SearchUser>() {
            @Override
            public void onResponse(@NonNull Call<SearchUser> call, @NonNull Response<SearchUser> response) {
                if (response.body() != null) {
                    SearchUser git = response.body();
                    Log.e("SUCCESS SEARCH", String.valueOf(response.body()));
                    searchUser.setValue(git);
                } else {
                    Log.e("ERROR SEARCH", "response body = null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchUser> call, @NonNull Throwable t) {
                Log.e("FAILURE SEARCH", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}