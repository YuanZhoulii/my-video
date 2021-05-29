package edu.wbu.video.video.dao;


import edu.wbu.video.video.entity.User;

import java.util.List;

/**
 * @author yuanzhouli
 * @date 2020/12/6 - 22:27
 */
public interface UserDao {
    public List<User> findAll();
    /**
     * 根据用户账号查询用户信息
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 用户保存
     * @param user
     */
    public int save(User user);

    /**
     * 根据用户账号和密码查询用户信息
     * @param username
     * @param password
     * @return
     */
    public User findByUsernameAndPassword(String username,String password);

    int update(User user);
}
