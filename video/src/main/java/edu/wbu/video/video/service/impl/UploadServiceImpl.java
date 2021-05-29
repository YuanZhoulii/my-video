package edu.wbu.video.video.service.impl;

import edu.wbu.video.video.dao.VideoDao;
import edu.wbu.video.video.entity.Response;
import edu.wbu.video.video.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author yuanzhouli
 * @date 2020/12/7 - 23:17
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UploadServiceImpl implements UploadService {
    @Autowired
    VideoDao videoDao;
    /**
     * 获取项目资源目录路径的另一种方式（下面的方式无法获取路径时尝试使用）
     * File directory = new File("video/src/main/resources");
     * String reportPath = directory.getCanonicalPath();
     * System.out.println(reportPath);
     * 项目静态资源目录：F:\IdeaProject\myVideo\video\src\main\resources\static
     */
    private String classSrcPath = System.getProperty("user.dir") + "\\video\\src\\main\\resources\\static\\";

    /**
     * MultipartFile接收文件
     * @param id
     * @param upFile
     * @param type
     * @return
     */
    @Override
    public Response upload(Integer id, MultipartFile upFile, String type) {
        //上传源文件名
        String originalFilename = upFile.getOriginalFilename();
        System.out.println("源文件名：" + originalFilename);
        //文件上传目标路径
        String realPath = null;
        String newFileName = null;
        if ("image".equals(type) && (originalFilename.endsWith(".png") || originalFilename.endsWith(".jpg") || originalFilename.endsWith(".gif"))) {
            System.out.println("上传文件类型：图片文件");
            //图片存储至F:\IdeaProject\myVideo\video\src\main\resources\image
            realPath = classSrcPath + "image";
        } else if ("video".equals(type) && (originalFilename.endsWith(".mp4"))) {
            System.out.println("上传文件类型：视频文件");
            //视频存储至F:\IdeaProject\myVideo\video\src\main\resources\video
            realPath = classSrcPath + "video";
        } else {
            System.out.println("非法文件！");
            return Response.error("不支持该文件类型");
        }

        /*使用与文件相关联的id作为文件名加上源文件后缀
        头像：用户id
        视频封面：视频id
        视频：视频id
         */
        newFileName = id + originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println("保存文件名：" + newFileName);

        File file = new File(realPath, newFileName);
        //文件父目录不存在创建
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                //文件不存在上传
                upFile.transferTo(new File(realPath, newFileName));
                return Response.ok("文件上传成功").put("file",originalFilename);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                return Response.error("文件上传失败");
            }
        } else {
            //文件存在则先删除再上传
            file.delete();
            try {
                upFile.transferTo(new File(realPath, newFileName));
                return Response.ok("文件上传成功").put("file",originalFilename);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return Response.error("文件上传失败");
    }
}
