package edu.wbu.video.video.controller.servlet;

import edu.wbu.video.video.entity.Response;
import edu.wbu.video.video.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuanzhouli
 * @date 2020/12/7 - 21:41
 * @CrossOrigin：允许所有跨域请求
 */
@CrossOrigin(origins = "*",maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("/upload")
public class UploadServlet {
    @Autowired
    UploadService uploadService;

    /**
     * 支持两种请求方式，例如
     * localhost:8080/cover/4
     * localhost:8080/cover?videoId=4
     * @param pathVideoId
     * @param videoId
     * @param upFile
     * @return
     */
    @PostMapping({"/cover/{id}","/cover"})
    public Response uploadCover(
            @PathVariable(value = "id",required = false) Integer pathVideoId,
            @RequestParam(value = "videoId",required = false) Integer videoId,
            @RequestParam("file") MultipartFile upFile, HttpServletRequest request){
        System.out.println("/upload/cover:"+request.getSession().getId());
        //没有传id值，直接返回404
        if(pathVideoId==null&&videoId==null){
            return Response.error(HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.getReasonPhrase());
        }
        System.out.println("上传封面");
        if(pathVideoId!=null&&videoId==null){
            videoId=pathVideoId;
        }
        System.out.println("视频id："+videoId);
        Response response=uploadService.upload(videoId,upFile,"image");
        return response;
    }

    @PostMapping("/avatar")
    public Response uploadAvatar(
            @RequestParam("userId") Integer userId,
            @RequestParam("file") MultipartFile upFile,HttpServletRequest request){
        System.out.println("/upload/avatar:"+request.getSession().getId());
        System.out.println("上传头像");
        System.out.println("用户id："+userId);
        Response response=uploadService.upload(userId,upFile,"image");
        return response;
    }
    @PostMapping("/video")
    public Response uploadVideo(
            @RequestParam("videoId") Integer videoId,
            @RequestParam("file") MultipartFile upFile,HttpServletRequest request){
        System.out.println("/upload/video:"+request.getSession().getId());
        System.out.println("上传视频");
        System.out.println("视频id："+videoId);
        Response response=uploadService.upload(videoId,upFile,"video");
        return response;
    }

}
