package com.cursoandroid.wellington.projetoteste.model;

/**
 * Created by Wellington on 27/01/18.
 */

public class User {

    private String username;
    private String token;
    private String photoUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
