package com.etone.project.core.model;


import java.io.Serializable;

/**
 */

public class CommentInfo implements Serializable {

    private Integer id;
    private Integer readAloudId;
    private String commentPersonCode;
    private String commentPerson;
    private String commentContent;
    private String commentTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReadAloudId() {
        return readAloudId;
    }

    public void setReadAloudId(Integer readAloudId) {
        this.readAloudId = readAloudId;
    }

    public String getCommentPersonCode() {
        return commentPersonCode;
    }

    public void setCommentPersonCode(String commentPersonCode) {
        this.commentPersonCode = commentPersonCode;
    }

    public String getCommentPerson() {
        return commentPerson;
    }

    public void setCommentPerson(String commentPerson) {
        this.commentPerson = commentPerson;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}