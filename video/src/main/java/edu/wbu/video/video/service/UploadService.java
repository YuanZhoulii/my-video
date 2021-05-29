package edu.wbu.video.video.service;

import edu.wbu.video.video.entity.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yuanzhouli
 * @date 2020/12/7 - 23:16
 */

public interface UploadService {
    public Response upload(Integer imageId, MultipartFile upFile,String type);
}
