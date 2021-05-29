package edu.wbu.video.video.dao.impl;

import edu.wbu.video.video.dao.UserDao;
import edu.wbu.video.video.entity.User;
import edu.wbu.video.video.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yuanzhouli
 * @date 2020/12/7 - 11:02
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<User> findAll() {
        String sql="select id,username,email,nickname,mobile,gender,joinDate from my_user";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        //使用JDBC操作数据库
        return users;
    }

    @Override
    public User findByUsername(String username) {
        User user=null;
        try{
            String sql="select id,username,email,nickname,mobile,gender,joinDate from my_user where username=?";
            //没查找数据不会返回null而是报空指针异常
            user= jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username);
        }catch (Exception e){
            //当异常时返回null
            return null;
        }
        return user;
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @Override
    public int save(User user) {
        String sql="insert into my_user(username,password,joinDate) values(?,?,?)";
        KeyHolder keyHolder=new GeneratedKeyHolder();
        int update=jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,user.getUsername());
                String MD5Password=MD5Util.code(user.getPassword());
                ps.setString(2,MD5Password);
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String now=sdf.format(new Date());
                ps.setString(3,now);
                user.setJoinDate(now);
                user.setPassword(null);
                return ps;
            }
        },keyHolder);
        user.setId(Integer.valueOf((int) keyHolder.getKey().longValue()));
        //返回更新的记录数
        return update;
    }

    /**
     * 根据用户名和密码查询
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username,String password) {
        User result=null;
        try{
            String sql="select id,username,email,nickname,mobile,gender,joinDate from my_user where username=? and password=?";
            //没查找数据不会返回null而是报空指针异常
            result= jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,MD5Util.code(password));
            System.out.println(result);
        }catch (Exception e){
            //当异常时返回null
            return null;
        }
        return result;
    }

    @Override
    public int update(User user) {
        String sql="update my_user set email=?,nickname=?,mobile=?,gender=? where id=?";
        return jdbcTemplate.update(sql,user.getEmail(),user.getNickname(),
                user.getMobile(),user.getGender(),user.getId());
    }


}
