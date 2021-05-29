package edu.wbu.video.video.controller.servlet;

import edu.wbu.video.video.util.NonStaticResourceHttpRequestHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author yuanzhouli
 * @date 2020/12/7 - 17:28
 */
@CrossOrigin(origins = "*",maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("/download")
public class DownloadServlet {
    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;
    //F:/IdeaProject/myVideo/video/target/classes/
    private String classSrcPath=System.getProperty("user.dir") + "\\video\\src\\main\\resources\\static\\";


    public DownloadServlet(NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler) {
        this.nonStaticResourceHttpRequestHandler = nonStaticResourceHttpRequestHandler;
    }

    /**
     * @RequestParam中的参数名与后面的属性名相同可以省略
     * @param id
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/video")
    public void downloadVideo(@RequestParam("videoId") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("/download/video:"+request.getSession().getId());
        //F:\IdeaProject\myVideo\video\src\main\resources\static\video\{id}.mp4
        String realPath = classSrcPath +"video\\"+id+".mp4";
        System.out.println("获取视频");
        returnFile(request, response, realPath);
    }
    @GetMapping("/avatar")
    public void downloadAvatar(@RequestParam("userId") String id, HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("/download/avatar:"+request.getSession().getId());
        //F:\IdeaProject\myVideo\video\src\main\resources\static\video\{id}.jpg
        //这里的扩展名之后可以从数据库取
        String realPath = classSrcPath +"image\\"+id+".jpg";
        System.out.println("获取头像");
        returnFile(request, response, realPath);
    }
    @GetMapping("/cover")
    public void downloadCover(@RequestParam("videoId") String id, HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("/download/cover:"+request.getSession().getId());
        //F:\IdeaProject\myVideo\video\src\main\resources\static\video\{id}.jpg
        //这里的扩展名之后可以从数据库取
        String realPath = classSrcPath +"image\\"+id+".jpg";
        System.out.println("获取封面");
        returnFile(request, response, realPath);
    }

    private void returnFile(HttpServletRequest request, HttpServletResponse response, String realPath) throws IOException, ServletException {
        System.out.println(realPath);
        Path filePath = Paths.get(realPath);
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }


}
