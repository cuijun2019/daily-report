package com.etone.project.core.model;


import java.io.Serializable;

/**
 */

public class ReadInfo implements Serializable {
    public Integer getSave() {
        return isSave;
    }

    public void setSave(Integer save) {
        isSave = save;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getThought() {
        return thought;
    }

    public void setThought(String thought) {
        this.thought = thought;
    }

    private Integer id;
    private String studentNo;
    private String studentName;
    private String article;
    private String thought;
    private String createTime;
    private Integer isSave;
}