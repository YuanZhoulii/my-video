package edu.wbu.video.video.dao.impl;

import edu.wbu.video.video.dao.VideoDao;
import edu.wbu.video.video.entity.Video;
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
 * @date 2020/12/9 - 18:31
 */
@Repository
public class VideoDaoImpl implements VideoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int findTotalCount() {
        String sql="select count(*) from my_video";
        Integer integer = jdbcTemplate.queryForObject(sql, Integer.class);
        return integer;
    }
    @Override
    public int findTotalCountByKeyword(String keyword){
        String sql="select count(*) from my_video where title like ? or `desc` like ?";
        String likeWord="%"+keyword+"%";
        Integer integer = jdbcTemplate.queryForObject(sql, Integer.class,likeWord,likeWord);
        return integer;
    }

    @Override
    public List<Video> findByPage(Integer start, Integer pageSize) {
        String sql="select * from my_video ORDER BY create_time DESC limit ?,?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Video>(Video.class),start,pageSize);
    }

    @Override
    public List<Video> findAll() {
        String sql="select * from my_video";
        List<Video> videos = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Video>(Video.class));
        //使用JDBC操作数据库
        return videos;
    }

    @Override
    public int save(Video video) {
        String sql="INSERT INTO my_video (title,`desc`,view_count,create_time,user_id) VALUES (?,?,0,?,?);";
        KeyHolder keyHolder=new GeneratedKeyHolder();
        int update=jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                //主键回填
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,video.getTitle());
                ps.setString(2,video.getDesc());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String now=sdf.format(new Date());
                ps.setString(3,now);
                ps.setInt(4,video.getUserId());
                video.setCreateTime(now);
                return ps;
            }
        },keyHolder);
        video.setId(Integer.valueOf((int) keyHolder.getKey().longValue()));
        video.setViewCount(0);
        System.out.println(video);
        return update;
    }

    @Override
    public int update(Video video) {
        String sql="UPDATE `my_video` SET title=?,`desc`=? WHERE id=?;";
        return jdbcTemplate.update(sql,video.getTitle(),video.getDesc(),video.getId());
    }

    @Override
    public Video findById(Integer videoId) {
        Video video=null;
        try{
            String sql="select * from my_video where id=?";
            //没查找数据不会返回null而是报空指针异常
            video= jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Video>(Video.class),videoId);
            System.out.println(video);
        }catch (Exception e){
            //当异常时返回null
            return null;
        }
        return video;
    }

    @Override
    public int updateCount(int videoId,int newCount) {
        String sql="UPDATE `my_video` SET view_count=? WHERE id=?;";
        return jdbcTemplate.update(sql,newCount,videoId);
    }

    @Override
    public List<Video> findKeyWordByPage(Integer start, Integer pageSize, String keyword) {
        String sql="select * from my_video where title like ? or `desc` like ? ORDER BY create_time DESC limit ?,?";
        //模糊查询
        String likeWord="%"+keyword+"%";
        System.out.println(start);
        System.out.println(pageSize);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Video>(Video.class),likeWord,likeWord,start,pageSize);
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String d=sdf.format(date);
        System.out.println(d);
    }

}
