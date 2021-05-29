package edu.wbu.video.video.service.impl;

import edu.wbu.video.video.dao.VideoDao;
import edu.wbu.video.video.entity.Page;
import edu.wbu.video.video.entity.Response;
import edu.wbu.video.video.entity.Video;
import edu.wbu.video.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuanzhouli
 * @date 2020/12/8 - 13:11
 */
@Transactional
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoDao videoDao;

    @Override
    public List<Video> findAll() {
        return videoDao.findAll();
    }

    @Override
    public Page<Video> pageQuery(Integer currentPage, Integer pageSize) {
        //封装Page
        Page<Video> p=new Page<Video>();
        //设置当前页码
        p.setCurrentPage(currentPage);
        //设置每页显示条数
        p.setPageSize(pageSize);
        //获取总记录数
        int totalCount=videoDao.findTotalCount();
        //设置总记录数
        p.setTotalCount(totalCount);
        /*
        开始的记录数
        比如第一页 (1-1)*16=0开始
        第二页 (2-1)*16=16开始
         */
        Integer start=(currentPage-1)*pageSize;
        List<Video> list=videoDao.findByPage(start,pageSize);
        p.setList(list);
        /*
        设置总页数=总记录数除以每页显示条数
        除不尽加一页
         */
        int totalPage=0;
        if(totalCount%pageSize==0){
            totalPage=totalCount/pageSize;
        }else{
            totalPage=(totalCount/pageSize)+1;
        }
        p.setTotalPage(totalPage);

        return p;
    }

    @Override
    public Response save(Video video) {
        if(videoDao.save(video)==1){
            return Response.ok("保存视频信息成功").put("video",video);
        }
        return Response.error("保存视频信息失败");
    }

    @Override
    public Response update(Video video) {
        if(videoDao.update(video)==1){
            return Response.ok("修改视频信息成功").put("video",video);
        }
        return Response.error("修改视频信息失败");
    }

    @Override
    public Response info(Integer videoId) {
        Video video=videoDao.findById(videoId);
        if(video!=null){
            return Response.ok("查询视频信息成功").put("video",video);
        }
        return Response.error("该视频信息不存在");
    }

    @Override
    public Response updateCount(int videoId,int newCount) {
        if(videoDao.updateCount(videoId,newCount)==1){
            return Response.ok("更新观看数成功");
        }
        return Response.error("更新观看数失败");
    }

    @Override
    public Page<Video> pageQueryByWord(Integer currentPage, Integer pageSize,String keyword) {
        //封装Page
        Page<Video> p=new Page<Video>();
        //设置当前页码
        p.setCurrentPage(currentPage);
        //设置每页显示条数
        p.setPageSize(pageSize);
        //获取总记录数
        int totalCount=videoDao.findTotalCountByKeyword(keyword);
        //设置总记录数
        p.setTotalCount(totalCount);
        /*
        开始的记录数
        比如第一页 (1-1)*16=0开始
        第二页 (2-1)*16=16开始
         */
        Integer start=(currentPage-1)*pageSize;
        List<Video> list=videoDao.findKeyWordByPage(start,pageSize,keyword);
        p.setList(list);
        /*
        设置总页数=总记录数除以每页显示条数
        除不尽加一页
         */
        int totalPage=0;
        if(totalCount%pageSize==0){
            totalPage=totalCount/pageSize;
        }else{
            totalPage=(totalCount/pageSize)+1;
        }
        p.setTotalPage(totalPage);

        return p;
    }
}
