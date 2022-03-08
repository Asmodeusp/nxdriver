package com.saimawzc.freight.dto.my.set;

import java.io.Serializable;

public class SuggestDto implements Serializable {
    private int browseStatus;//浏览状态 已读未读
    private String content;
    private String id;
    private String picture;
    private String replyContent;
    private int replyStatus;
    private String replyTime;

    public int getBrowseStatus() {
        return browseStatus;
    }

    public void setBrowseStatus(int browseStatus) {
        this.browseStatus = browseStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public int getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(int replyStatus) {
        this.replyStatus = replyStatus;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }
}
