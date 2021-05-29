package edu.wbu.video.video.entity;

import java.io.Serializable;

/**
 * @author yuanzhouli
 * @date 2020/12/7 - 10:43
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Video implements Serializable {
    private Integer id;
    private String title;
    private String desc;
    private Integer viewCount;
    private String createTime;
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", viewCount=" + viewCount +
                ", createTime=" + createTime +
                ", userId=" + userId +
                '}';
    }
}
