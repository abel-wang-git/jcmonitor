package com.zkyf.ctrl;

import com.zkyf.service.Runner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/11/1.
 */
@Controller
public class Ctrl {

    @Value("${jc.ips}") private  String host;
    @GetMapping(value ="/" )
    public String index(){
        return "login";
    }
    @PostMapping(value = "/login")
    public String login(Model model){
        model.addAttribute("ips", Runner.decrypt("123",host));
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

}
