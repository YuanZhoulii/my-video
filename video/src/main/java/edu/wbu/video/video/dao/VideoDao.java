package edu.wbu.video.video.dao;

import edu.wbu.video.video.entity.Video;

import java.util.List;

/**
 * @author yuanzhouli
 * @date 2020/12/6 - 22:28
 */
public interface VideoDao {
    /**
     * 查询视频总记录数
     */
    public int findTotalCount();

    /**
     * 查询当前页的数据集合
     */
    public List<Video> findByPage(Integer start,Integer pageSize);
    public List<Video> findAll();
    /**
     * 保存视频信息
     */
    public int save(Video video);

    int update(Video video);

    Video findById(Integer videoId);

    int updateCount(int videoId,int newCount);

    List<Video> findKeyWordByPage(Integer start, Integer pageSize, String keyword);

    int findTotalCountByKeyword(String keyword);
}
