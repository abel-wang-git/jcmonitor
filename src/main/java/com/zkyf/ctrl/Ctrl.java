package com.zkyf.ctrl;

import com.zkyf.service.Croe;
import com.zkyf.service.Runner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 *
 */
@Controller
public class Ctrl {
    @Resource
    private Croe croe;

    @Value("${jc.ips}") private  String host;
    @GetMapping(value ="/" )
    public String index(){
        return "login";
    }
    @PostMapping(value = "/login")
    public String login(Model model){
       List hosts =croe.listHost();

        model.addAttribute("ips", hosts);
        model.addAttribute("count",croe.countError());
        return "home";
    }
    @GetMapping(value = "/message")
    public String message(){
        return "body";
    }
    @GetMapping(value = "/log")
    public String log(){
        return "log";
    }
    @PostMapping( value="/log")
    public String catLog(@RequestParam String ip, Model model){
        List list = croe.ListLog(ip);
        model.addAttribute("list",list);
        return "log" ;
    }

    @PostMapping( value="/error")
    public String error(@RequestParam(defaultValue = "") String ip, Model model){
        List list = croe.ListError(ip);
        model.addAttribute("list",list);
        return "log" ;
    }
    @PostMapping( value="/errorhis")
    public String errorHis(@RequestParam(defaultValue = "") String ip, Model model){
        List list = croe.ListError(ip);
        model.addAttribute("list",list);
        return "log";
    }
    @PostMapping(value = "/countErr")
    @ResponseBody
    public String CountErr(){
        int i =  croe.countError();
        return i+"";
    }

    @GetMapping(value = "/wb")
    public String wb(){
        return "wb";
    }
}
