package com.kevin.github.model;

import com.google.gson.annotations.SerializedName;

public class UserResultResponse {

    @SerializedName("login")
    private final String login;
    @SerializedName("id")
    private long id;
    @SerializedName("avatar_url")
    private final String avatarUrl;
    @SerializedName("type")
    private final String type;

    public UserResultResponse(String login, String avatarUrl, String type) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.type = type;
    }

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

    public String getType() {
        return type;
    }
}
