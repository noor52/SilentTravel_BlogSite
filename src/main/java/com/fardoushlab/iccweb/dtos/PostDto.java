package com.fardoushlab.iccweb.dtos;



import com.fardoushlab.iccweb.models.Comment;
import com.fardoushlab.iccweb.models.Like;
import com.fardoushlab.iccweb.models.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDto {
    private String postDesc;
    private boolean isActive;


    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private long id;
    private User user;
    private List<String> postImages =  new ArrayList<>();
    private String postText;
    private List<Comment> comments  = new ArrayList<>();
    private List<Like> likes = new ArrayList<>();
    private LocalDateTime postTime;

    private String postTimeString;
    private long totalLike;
    private long totalComment;
    private boolean isLiked;

    public PostDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getPostImages() {
        return postImages;
    }

    public void setPostImages(List<String> postImages) {
        this.postImages = postImages;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public LocalDateTime getPostTime() {
        return postTime;
    }

    public void setPostTime(LocalDateTime postTime) {
        this.postTime = postTime;
    }

    public String getPostTimeString() {
        return postTimeString;
    }

    public void setPostTimeString(String postTimeString) {
        this.postTimeString = postTimeString;
    }

    public long getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(long totalLike) {
        this.totalLike = totalLike;
    }

    public long getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(long totalComment) {
        this.totalComment = totalComment;
    }

    public boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }
}



