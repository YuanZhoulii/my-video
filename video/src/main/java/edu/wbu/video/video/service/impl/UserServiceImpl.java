package edu.wbu.video.video.service.impl;


import edu.wbu.video.video.dao.UserDao;
import edu.wbu.video.video.entity.Response;
import edu.wbu.video.video.entity.User;
import edu.wbu.video.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuanzhouli
 * @date 2020/12/7 - 11:00
 * @Transactional
 * 标示类中的所有方法都进行事务处理，在遇到RuntimeException的时候回滚
 */
@Transactional(rollbackFor = RuntimeException.class)
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public Response regist(User user) {
        //帐号是否合法(必须以字母开头，允许字母数字下划线，长度5-16之间)
        //密码是否合法(必须包含大小写字母和数字的组合，可以使用特殊字符，长度在8-16之间)：
        if(!user.getUsername().matches("^[a-zA-Z][a-zA-Z0-9_]{4,15}$")){
            return Response.error("账号必须以字母开头，之后允许字母数字下划线，总长度5-16");
        }
        if(!user.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$")){
            return Response.error("密码至少分别包含一个大、小写字母和数字，此外可以使用特殊字符，总长度8-16");
        }
        User u=userDao.findByUsername(user.getUsername());
        //不为null说明该用户已存在
        if(u!=null){
            return Response.error("用户已存在");
        }

        //如果保存记录数为1说明注册成功
        if(userDao.save(user)==1){
            return Response.ok("注册成功").put("user",user);
        }
        //其它情况说明注册失败
        return Response.error("服务器错误");
    }

    @Override
    public int update(User user) {
        return userDao.update(user);

    }
}
