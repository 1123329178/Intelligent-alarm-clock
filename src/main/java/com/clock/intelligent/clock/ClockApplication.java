package com.clock.intelligent.clock;

import com.clock.intelligent.clock.nettyServer.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@ComponentScan({"com.clock.intelligent.clock.controller"
//        ,"com.clock.intelligent.clock.mapper"
//        ,"com.clock.intelligent.clock.model"
//        ,"com.clock.intelligent.clock.nettyHandler"
//        ,"com.clock.intelligent.clock.nettyServer"
//        ,"com.clock.intelligent.clock.service"
//        ,"com.clock.intelligent.clock.uitls"})
@MapperScan("com.clock.intelligent.clock.mapper")
@SpringBootApplication
public class ClockApplication implements CommandLineRunner{
    @Autowired
    NettyServer nettyServer;
//    #T,1234567890ABCDFE,1,2,2,0
    public static void main(String[] args) {
        SpringApplication.run(ClockApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        nettyServer.run();
    }
}
