package com.clock.intelligent.clock.nettyHandler;

import com.clock.intelligent.clock.SingleMap.SingletonHashMap;
import com.clock.intelligent.clock.controller.ClockUserController;
import com.clock.intelligent.clock.controller.RequestCodeProServer;
import com.clock.intelligent.clock.mapper.ClockMsgMapper;
import com.clock.intelligent.clock.model.ClockMsg;
import com.clock.intelligent.clock.model.RequestData;
import com.clock.intelligent.clock.uitls.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/*
1.我们自定义一个handler 需要继承netty 固定好的某个handlerAdapter
2.这时我们自定义一个hander
 */
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler {
    private String clientMsg = "";
    @Autowired
    RequestCodeProServer requestCodeProcess = new RequestCodeProServer();
    @Autowired
    Utils utils = new Utils();
    @Autowired
    ClockUserController clockUserController = new ClockUserController();
    @Autowired
    ClockUserController clockUserControlle;
    @Autowired
    ClockMsgMapper clockMsgMapper;
    public volatile String SerialNumber="";
    /**
     *  定义一个 Channel 组,管理所有的channel
     *     GlobalEventExecutor.INSTANCE   一个单例的
     */
    private static ChannelGroup channelGroup= new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    public static void setChannelGroup(ChannelGroup channelGroup) {
        NettyServerHandler.channelGroup = channelGroup;
    }
    /*
    使用一个hashmap来管理所有的 channel 后续使用redis
     */

    public  static SingletonHashMap<String,Channel> channelMap=   SingletonHashMap.getInstance();

    //读取数据(客户端)
    /*
    1.channelhandlerContext ctx 上下文对象  含有 管道pipeline   通道channel,地址
    2.object msg  就是客户端发送的数据  默认object
     */

    /**
     * @param ctx   表示连接建立,第一个被执行
     *              将当前的channel  加入到 channelGroup
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //将新来的客户端channelj加入到channelGroup
        Channel channel = ctx.channel();
        channelGroup.add(channel);
        //KEY 为地址端口号,value 为channel
        String add = channel.remoteAddress().toString();
        channelMap.put(add,channel);
    }

    /**
     * @param ctx   断开连接,将xx客户端断开的给杀掉
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx)  {
        Channel channel = ctx.channel();
        String ip2 = channel.remoteAddress().toString();
        channelGroup.remove(channel);
        for (String s : channelMap.keySet()) {
            String[] split = s.split("@");
            if (split[0].equals(ip2)){
                channelMap.remove(s);
            }
        }

        System.out.println(channel.remoteAddress()+"   断开连接");
    }

    /**
     * 客户端注册发生的时间
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        //1.把每一个连接的对象利用redis 寄存起来,超过100次,就无法访问,断开连接.
        System.out.println("注册-------------");
    }

    /**
     * @param ctx 读取客户端数据
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception  {
        //将msg转化一个ByteBuf,是netty提供的   不是nio的
        ByteBuf buf = (ByteBuf) msg;
        String mesg = buf.toString(CharsetUtil.UTF_8);
        /*
        把连接的客户端  存入一个mapa里面确保,设置音乐,发送给客户端.
        SerialNumber是唯一的
         */
        RequestData requestData = utils.fenge(mesg);
        String SerialNumber= requestData.getSerialNumber();
        Channel channel = ctx.channel();
        String ip = channel.remoteAddress().toString();
        String ipSerialNumber=ip+"@"+SerialNumber;
        channelMap.put(ipSerialNumber,channel);
        Channel channel1 = channelMap.get(ip);
        if (channel1 !=null){
            channelMap.remove(ip);
        }


        //第一次连接查数据库是否有,插入数据库
        clockUserController.findClockUser(mesg);
        //处理Code码
        try {
            clientMsg = requestCodeProcess.processAddress(ip, mesg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //查询是否有设置音乐信息,而没有同步到闹钟上去的数据
        List<ClockMsg> clockMsgs = clockMsgMapper.findMsg(SerialNumber, "1");
        if (clockMsgs!=null){
            for (ClockMsg clockMsg : clockMsgs) {
                String clockMsg1 = clockMsg.getClockMsg();
                channel.writeAndFlush(clockMsg1);
                clockMsg.setMsgstatus("2");
                clockMsgMapper.updateMsg(clockMsg);
            }

        }
        System.out.println(ip);

        System.out.println("客户端发送的消息是:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端发送的地址是:" + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将msg转化一个ByteBuf,是netty提供的   不是nio的
        ByteBuf buf = (ByteBuf) msg;
        String mesg = buf.toString(CharsetUtil.UTF_8);
        String ip = ctx.channel().remoteAddress().toString();
        //第一次连接查数据库是否有,插入数据库
        clockUserController.findClockUser(mesg);
        //处理Code码
        clientMsg = requestCodeProcess.processAddress(ip, mesg);
        System.out.println("客户端发送的消息是:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端发送的地址是:" + ctx.channel().remoteAddress());
    }

    /**
     * @param ctx 数据读取完毕,的时间
     * @throws Exception
     */

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入缓冲并刷新
        //一般要对发送的数据进行编码

        ctx.writeAndFlush(Unpooled.copiedBuffer(clientMsg, CharsetUtil.UTF_8));

        //ctx.close();
    }
    //处理异常

    /**
     * @param ctx   异常事件
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         ctx.close();
        System.out.println("出错了netty异常");
    }
}
