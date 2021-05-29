package edu.wbu.video.video.controller.servlet;

import edu.wbu.video.video.entity.*;
import edu.wbu.video.video.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuanzhouli
 * @date 2020/12/11 - 17:42
 */
@CrossOrigin(origins = "*",maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("/collect")
public class CollectServlet {
    @Autowired
    private CollectService collectService;
    @GetMapping("/find")
    public Response find(@RequestParam("videoId") Integer videoId, HttpServletRequest request){
        System.out.println("/collect/find:"+request.getSession().getId());
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return Response.error("您尚未登录");
        }
        return collectService.find(user.getId(),videoId);

    }
    @PostMapping("/save")
    public Response save(@RequestBody Collect collect, HttpServletRequest request){
        System.out.println("/collect/save:"+request.getSession().getId());
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return Response.error("您尚未登录");
        }
        return collectService.save(user.getId(),collect.getVideoId());
    }
    @PostMapping("/delete")
    public Response delete(@RequestBody Collect collect, HttpServletRequest request){
        System.out.println("/collect/delete:"+request.getSession().getId());
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return Response.error("您尚未登录");
        }
        return collectService.delete(user.getId(),collect.getVideoId());
    }
    @GetMapping("/list")
    public Response list(@RequestParam(value = "currentPage",required = false) Integer currentPage,
                         @RequestParam(value = "pageSize",required = false) Integer pageSize,HttpServletRequest request){
        System.out.println("/collect/list:"+request.getSession().getId());
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return Response.error("您尚未登录");
        }
        //不传当前页码默认为第一页
        if(currentPage==null){
            currentPage=1;
        }
        //不传每页显示条数默认显示16条
        if(pageSize==null){
            pageSize=16;
        }
        Page<Video> videoPage = collectService.pageQuery(currentPage, pageSize,user.getId());

        return Response.ok("获取收藏列表成功").put("videos",videoPage);
    }
}
