package edu.wbu.video.video.dao;

import edu.wbu.video.video.entity.Video;

import java.util.List;

/**
 * @author yuanzhouli
 * @date 2020/12/6 - 22:28
 */
public interface CollectDao {
    int findByUserIdAndVideoId(Integer userId, Integer videoId);

    int save(Integer userId, Integer videoId);

    int delete(Integer userId, Integer videoId);

    int findTotalCountByUserId(Integer userId);

    List<Video> findPageByUserId(Integer start, Integer pageSize, Integer userId);
}
