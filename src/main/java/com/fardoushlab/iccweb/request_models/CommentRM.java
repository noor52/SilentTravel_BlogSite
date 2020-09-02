package com.fardoushlab.iccweb.request_models;


import com.fardoushlab.iccweb.models.Post;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CommentRM implements Serializable {

    private long id;
    private Post post;
    private long postId;
    private User woner;
    private long wonerId;
    private String commentText;
    private LocalDateTime commentTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getWoner() {
        return woner;
    }

    public void setWoner(User woner) {
        this.woner = woner;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDateTime getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(LocalDateTime commentTime) {
        this.commentTime = commentTime;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getWonerId() {
        return wonerId;
    }

    public void setWonerId(long wonerId) {
        this.wonerId = wonerId;
    }
}

