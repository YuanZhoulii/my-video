package edu.wbu.video.video.entity;

import java.io.Serializable;

/**
 * @author yuanzhouli
 * @date 2020/12/7 - 10:42
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Collect implements Serializable {
    private Integer id;
    private Integer videoId;
    private Integer userId;

    public Collect() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Collect{" +
                "id=" + id +
                ", videoId=" + videoId +
                ", userId=" + userId +
                '}';
    }
}
