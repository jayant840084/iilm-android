/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package models;

import com.google.gson.annotations.SerializedName;

import constants.LoginAttributes;

/**
 * Created by jayan on 16-11-2016.
 */

public class LoginModel {

    @SerializedName(LoginAttributes.TOKEN)
    private String token;

    @SerializedName(LoginAttributes.SCOPE)
    private String scope;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
