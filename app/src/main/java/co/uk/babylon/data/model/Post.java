package co.uk.babylon.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright (c) 2019 Mobile Vision Technologies LTD. All rights reserved.
 * Created on 4/3/19.
 */
public class Post {
    @SerializedName("userId")
    private String userId;
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;

    private String commentNumber;
    private String userName;

    public Post() {
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getCommentNumber() {
        return commentNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setCommentNumber(final String commentNumber) {
        this.commentNumber = commentNumber;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }
}
