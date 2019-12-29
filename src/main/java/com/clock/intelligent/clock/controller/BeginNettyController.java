package com.clock.intelligent.clock.controller;

import com.clock.intelligent.clock.nettyServer.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BeginNettyController {
    @Autowired
    NettyServer nettyServer;

    @RequestMapping("/beginNetty")
    public String hello() {

//        try {
//            nettyServer.run();
//            System.out.println("2");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            System.out.println("启动失败");
//        }

        return "Netty服务器已经启动";
    }
    @RequestMapping("/beginNetty1")
    public String hello1() {



            System.out.println("2");


        return "Netty服务器已经启动";
    }
}
