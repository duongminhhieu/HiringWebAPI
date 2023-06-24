package com.setqt.Hiring.DTO.APIResponse;

import java.util.Date;

public class NotificationResponse {
    private String image;
    private String status;
    private String title;
    private String content;
    private Date time;

    public NotificationResponse(String image, String status, String title, String content, Date time) {
        this.image = image;
        this.status = status;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
