package edu.wbu.video.video.service.impl;

import edu.wbu.video.video.dao.CollectDao;
import edu.wbu.video.video.entity.Page;
import edu.wbu.video.video.entity.Response;
import edu.wbu.video.video.entity.Video;
import edu.wbu.video.video.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanzhouli
 * @date 2020/12/11 - 17:44
 */
@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectDao collectDao;
    @Override
    public Response find(Integer userId, Integer videoId) {
        if(collectDao.findByUserIdAndVideoId(userId,videoId)==1){
            return Response.ok("获取收藏状态成功").put("status",true);
        }
        return Response.ok("获取收藏状态成功").put("status",false);
    }

    @Override
    public Response save(Integer userId, Integer videoId) {
        System.out.println("userId"+userId);
        System.out.println("videoId"+videoId);
        if (collectDao.save(userId, videoId) == 1) {
            return Response.ok("保存收藏状态成功");
        }
        return Response.error("保存收藏状态失败");
    }

    @Override
    public Response delete(Integer userId, Integer videoId) {
        if (collectDao.delete(userId, videoId) == 1) {
            return Response.ok("保存收藏状态成功");
        }
        return Response.error("保存收藏状态失败");
    }

    @Override
    public Page<Video> pageQuery(Integer currentPage, Integer pageSize, Integer userId) {
        //封装Page
        Page<Video> p=new Page<Video>();
        //设置当前页码
        p.setCurrentPage(currentPage);
        //设置每页显示条数
        p.setPageSize(pageSize);
        //获取总记录数
        int totalCount= collectDao.findTotalCountByUserId(userId);
        //设置总记录数
        p.setTotalCount(totalCount);

        Integer start=(currentPage-1)*pageSize;
        List<Video> collects=collectDao.findPageByUserId(start,pageSize,userId);
        p.setList(collects);

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
