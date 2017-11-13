package com.zkyf.service;

import com.alibaba.fastjson.JSON;
import com.zkyf.ctrl.Wb;
import com.zkyf.entity.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.zkyf.service.Runner.decrypt;

/**
 * Created by Administrator on 2017/11/7.
 */
@Component
public class Ping implements  Runnable{
    private JdbcTemplate jdbcTemplate;
    private String ip;
    private Wb tsCtrl;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setTsCtrl(Wb tsCtrl) {
        this.tsCtrl = tsCtrl;
    }

    @Override
    public void run() {
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        Process pro = null;
        String line = null;
        BufferedReader buf=null;
        int status =1;
        try {
            pro = Runtime.getRuntime().exec("ping " + decrypt("123",ip));
            buf = new BufferedReader(new InputStreamReader(pro.getInputStream(), Charset.forName("GBK")));
            int type;
            while((line = buf.readLine()) != null){
                System.out.println(line);
                type=(line.contains("ttl")||line.contains("TTL"))? Log.INFO:Log.ERROR;
                if(!line.equals(""))
                    jdbcTemplate.update("insert into log ( `date`,`message`,`type`,`host`) VALUE ('"+
                            sdf.format(new Date())+"','"+line+"',"+type+",'"+decrypt("123",ip)+"')");
                if(type!=status){
                    jdbcTemplate.update("UPDATE host SET status=? WHERE  ip=?",type,decrypt("123",ip));
                    Long id= jdbcTemplate.queryForObject("SELECT id FROM host WHERE ip=?",Long.class,decrypt("123",ip));
                    Map<String,Object> map = new HashMap<>();
                    map.put("id",id);
                    map.put("status",status+"");
                    tsCtrl.onMessage(JSON.toJSONString(map),null);
                    status=type;
                    System.out.println(type+"===="+status);
                }
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            try {
                buf.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            pro.destroy();
            run();
            e.printStackTrace();
        } catch (InterruptedException e) {
            try {
                buf.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            pro.destroy();
            run();
            e.printStackTrace();
        }
    }
}
