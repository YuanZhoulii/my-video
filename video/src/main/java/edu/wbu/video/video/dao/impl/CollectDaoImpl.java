package edu.wbu.video.video.dao.impl;

import edu.wbu.video.video.dao.CollectDao;
import edu.wbu.video.video.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yuanzhouli
 * @date 2020/12/11 - 17:52
 */
@Repository
public class CollectDaoImpl implements CollectDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public int findByUserIdAndVideoId(Integer userId, Integer videoId) {
        String sql="select count(*) from my_video_collected where user_id=? and video_id=?";
        Integer count=jdbcTemplate.queryForObject(sql,Integer.class,userId,videoId);
        return count;
    }

    @Override
    public int save(Integer userId, Integer videoId) {
        String sql="insert into my_video_collected(user_id,video_id) values(?,?)";
        return jdbcTemplate.update(sql,userId,videoId);
    }

    @Override
    public int delete(Integer userId, Integer videoId) {
        String sql="DELETE FROM my_video_collected WHERE user_id=? AND video_id=?";
        return jdbcTemplate.update(sql,userId,videoId);
    }

    @Override
    public int findTotalCountByUserId(Integer userId) {
        String sql="select count(*) from my_video_collected where user_id=?";
        Integer integer = jdbcTemplate.queryForObject(sql, Integer.class,userId);
        return integer;
    }

    @Override
    public List<Video> findPageByUserId(Integer start, Integer pageSize, Integer userId) {
        String sql="SELECT * from my_video WHERE id=any(SELECT video_id from my_video_collected WHERE user_id=?) ORDER BY create_time DESC LIMIT ?,?";
        System.out.println(start);
        System.out.println(pageSize);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Video>(Video.class),userId,start,pageSize);

    }
}
