package edu.wbu.video.video.service;

import edu.wbu.video.video.entity.Page;
import edu.wbu.video.video.entity.Response;
import edu.wbu.video.video.entity.Video;

/**
 * @author yuanzhouli
 * @date 2020/12/11 - 17:44
 */
public interface CollectService {
    Response find(Integer userId, Integer videoId);

    Response save(Integer userId, Integer videoId);

    Response delete(Integer userId, Integer videoId);

    Page<Video> pageQuery(Integer currentPage, Integer pageSize, Integer userId);
}
