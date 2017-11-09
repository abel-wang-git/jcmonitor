package com.zkyf.service;

import com.zkyf.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */
@Service("croe")
public class Croe {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List ListLog(String ip){
        List list = jdbcTemplate.queryForList("SELECT * FROM log WHERE  host = ? ORDER BY date desc",ip);
        return list;
    }

    public  List ListError(String ip){
        List list=ip.equals("")?jdbcTemplate.queryForList("SELECT * FROM log WHERE type=1 ORDER BY date desc")
                :jdbcTemplate.queryForList("SELECT * FROM log WHERE host = ? AND type=1 ORDER BY date desc",ip);
        if(ip.equals("")){jdbcTemplate.update("UPDATE log SET type=3 WHERE type=1");}
        else{jdbcTemplate.update("UPDATE log SET type=3 WHERE host = ? AND type=1",ip);}
        return list;
    }
    public  List ListErrorHis(String ip){
        List list=ip.equals("")?jdbcTemplate.queryForList("SELECT * FROM log WHERE type=3 ORDER BY date desc")
                :jdbcTemplate.queryForList("SELECT * FROM log WHERE host = ? type=3 ORDER BY date desc",ip);
        return list;
    }

    public  int countError(){
        int i =  jdbcTemplate.queryForObject("SELECT COUNT(*) FROM log WHERE type=1",Integer.class);
        return i;
    }

    public List findHost(String ip){
        List list = jdbcTemplate.queryForList("SELECT * FROM  host WHERE ip =? ",ip);
        return list;
    }

    public void insertHost(int status,String ip){
        jdbcTemplate.update("INSERT INTO host (`ip`,`status`) VALUES(?,?) " ,ip,status);
    }

    public List listHost(){
        List list = jdbcTemplate.queryForList("SELECT  * FROM  host ");
        return list;
    }
}
