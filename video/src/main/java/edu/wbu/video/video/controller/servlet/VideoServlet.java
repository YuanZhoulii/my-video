package edu.wbu.video.video.controller.servlet;

import edu.wbu.video.video.entity.Page;
import edu.wbu.video.video.entity.Response;
import edu.wbu.video.video.entity.Video;
import edu.wbu.video.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuanzhouli
 * @date 2020/12/8 - 13:10
 */
@CrossOrigin(origins = "*",maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("/video")
public class VideoServlet {
    @Autowired
    private VideoService videoService;

    /**
     *
     * @param currentPage 当前页码，不填默认为第一页
     * @param pageSize 每页显示条数，不填默认显示16条
     * 请求示例：https://localhost:8080/video/list?currentPage=2&pageSize=3
     * @return
     */
    @GetMapping("/list")
    public Response list(
            @RequestParam(value = "currentPage",required = false) Integer currentPage,
            @RequestParam(value = "pageSize",required = false) Integer pageSize,HttpServletRequest request){
        System.out.println("/video/list:"+request.getSession().getId());
        //不传当前页码默认为第一页
        if(currentPage==null){
            currentPage=1;
        }
        //不传每页显示条数默认显示16条
        if(pageSize==null){
            pageSize=16;
        }
        Page<Video> videoPage = videoService.pageQuery(currentPage, pageSize);
        return Response.ok("查询成功").put("videos",videoPage);
    }

    /**
     * 请求地址：https://localhost:8080/video/save?userId=1
     * 请求参数：application/json
     * {
     *     "title":"哈哈哈哈哈哈",
     *     "desc":"测试"
     *     "userId":1
     * }
     * @param video
     * @return
     */
    @PostMapping("/save")
    public Response save(@RequestBody Video video,HttpServletRequest request){
        System.out.println("/video/save:"+request.getSession().getId());
        return videoService.save(video);
    }
    @PostMapping("/update")
    public Response update(@RequestBody Video video,HttpServletRequest request){
        System.out.println("/video/update:"+request.getSession().getId());
        return videoService.update(video);
    }
    @GetMapping("/info")
    public Response info(@RequestParam("id") Integer videoId, HttpServletRequest request){
        System.out.println("/video/info:"+request.getSession().getId());
        return videoService.info(videoId);
    }

    /**
     * https://10.159.244.159:8080/video/count
     * @param
     * {
     *     "id": 64,
     *     "viewCount":19
     * }
     * @return
     * success
     * {
     *     "msg": "更新观看数成功",
     *     "flag": true,
     *     "code": 200
     * }
     * error
     * {
     *     "msg": "更新观看数失败",
     *     "flag": false,
     *     "code": 500
     * }
     */
    @PostMapping("/count")
    public Response updateCount(@RequestBody Video video,HttpServletRequest request){
        System.out.println("/video/count:"+request.getSession().getId());
        int currentCount=video.getViewCount();
        System.out.println("currentCount:"+currentCount);
        return videoService.updateCount(video.getId(),currentCount+1);
    }
    @GetMapping("/search")
    public Response search(@RequestParam("word") String keyword,
                           @RequestParam(value = "currentPage",required = false) Integer currentPage,
                           @RequestParam(value = "pageSize",required = false) Integer pageSize,HttpServletRequest request){
        System.out.println("/video/search:"+request.getSession().getId());
        //不传当前页码默认为第一页
        if(currentPage==null){
            currentPage=1;
        }
        //不传每页显示条数默认显示16条
        if(pageSize==null){
            pageSize=16;
        }
        Page<Video> videoPage = videoService.pageQueryByWord(currentPage, pageSize,keyword);
        return Response.ok("查询成功").put("videos",videoPage);

    }
}
