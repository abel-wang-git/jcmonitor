package com.zkyf.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/11/6.
 *
 */
@Entity
@Table(name = "log")
public class Log {
    public   static  final  int  ERROR=1;
    public   static  final  int  INFO=2;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private  String host;
    private  String date;
    private  String message;
    private  int type;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
