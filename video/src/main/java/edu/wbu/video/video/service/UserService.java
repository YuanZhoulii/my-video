package edu.wbu.video.video.service;


import edu.wbu.video.video.entity.Response;
import edu.wbu.video.video.entity.User;

import java.util.List;

/**
 * @author yuanzhouli
 * @date 2020/12/7 - 10:58
 */
public interface UserService {
    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAll();
    public User login(User user);
    public Response regist(User user);

    int update(User user);
}
