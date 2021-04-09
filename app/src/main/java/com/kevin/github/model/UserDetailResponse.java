package com.kevin.github.model;

import com.google.gson.annotations.SerializedName;

public class UserDetailResponse {
    @SerializedName("login")
    String login;
    @SerializedName("id")
    long id;
    @SerializedName("avatar_url")
    String avatarUrl;
    @SerializedName("name")
    String name;
    @SerializedName("company")
    String company;
    @SerializedName("location")
    String location;
    @SerializedName("public_repos")
    int publicRepos;
    @SerializedName("followers")
    int followers;
    @SerializedName("following")
    int following;

    public String getLogin() {
        return login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }
}
