package com.clock.intelligent.clock.nettyServer;

import com.clock.intelligent.clock.mapper.ClockUserMapper;
import com.clock.intelligent.clock.nettyHandler.ServerChannelInitializer;
import com.clock.intelligent.clock.service.ClockUserService;
import com.clock.intelligent.clock.uitls.Utils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * NettyServer 配置类
 *
 * @author hp
 */
@Component
public class NettyServer {
    @Autowired
    NettyServer nettyServer;
    @Autowired
    private Utils utils;
    @Autowired
    ClockUserMapper clockUserMapper;
    @Autowired
    ClockUserService clockUserService;
    @Autowired
    ServerChannelInitializer serverChannelInitializer;
    //创建BossGroup 和 WorkerGroup
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    @Value("8848")
    private Integer port;

    //public void beginNettyServer() throws InterruptedException {
   // @PostConstruct
    public void run() throws InterruptedException {
//        nettyServer = this;
//        nettyServer.clockUserMapper = this.clockUserMapper;
//        nettyServer.clockUserService = this.clockUserService;
       /*
        1.创建两个线程组BossGroup 和 WorkerGroup
        2.bossGroup 知识处理链接请求,真正的和客户端业务处理,会交给WorkerGroup
        3.两个都是无限循环
        */

        try {


            //创建服务器端的启动对象,配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程来进行设置
            bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    //使用NioSocketChannell 作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    //使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    //设置线程队列的到连接个数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //将小的数据包包装成更大的帧进行传送，提高网络的负载
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //创建一个通道测试对象(匿名对象)
                    .childHandler(serverChannelInitializer);//给我们的workerGroup的EventLoop 对应的管道设置处理器
            System.out.println("服务齐------------");
            //绑定  启动服务
            ChannelFuture cf = bootstrap.bind().sync();

            //给cf  注册监听器,监听我们关心的事件

            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听端口成功");
                    } else {
                        System.out.println("失败");
                    }
                }
            });
            //对关闭通道进行监听
            //closeFuture  是什么?
            cf.channel().closeFuture().sync();
        } finally {

        }
    }

    @PreDestroy
    public void destory() throws InterruptedException {
        bossGroup.shutdownGracefully().sync();
        workerGroup.shutdownGracefully().sync();
    }

    public static void main(String[] args) throws InterruptedException {


    }
}
