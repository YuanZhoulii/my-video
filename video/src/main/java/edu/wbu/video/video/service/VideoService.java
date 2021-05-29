package edu.wbu.video.video.service;

import edu.wbu.video.video.entity.Page;
import edu.wbu.video.video.entity.Response;
import edu.wbu.video.video.entity.Video;

import java.util.List;

/**
 * @author yuanzhouli
 * @date 2020/12/8 - 13:11
 */
public interface VideoService {

    List<Video> findAll();

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<Video> pageQuery(Integer currentPage, Integer pageSize);

    Response save(Video video);

    Response update(Video video);

    Response info(Integer videoId);

    Response updateCount(int videoId,int newCount);

    Page<Video> pageQueryByWord(Integer currentPage, Integer pageSize,String keyword);
}
