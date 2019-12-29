package com.clock.intelligent.clock.nettyHandler;

import com.clock.intelligent.clock.nettyHandler.NettyServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//给pipeline设置处理器

/**
 * @author hp
 * SimpleChannelInboundHandler  是 channelinboundHandlerAdapter
 */
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    NettyServerHandler nettyServerHandler;
    //给pipeline设置处理器
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //给我们的workerGroup的EventLoop 对应的管道设置处理器
        ch.pipeline().addLast(nettyServerHandler);
    }
}
