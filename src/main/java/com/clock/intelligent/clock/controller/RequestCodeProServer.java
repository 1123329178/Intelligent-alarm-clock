package com.clock.intelligent.clock.controller;


import com.clock.intelligent.clock.model.RequestData;
import com.clock.intelligent.clock.service.DiscessCodeService;
import com.clock.intelligent.clock.uitls.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class RequestCodeProServer {
    @Autowired
    private DiscessCodeService discessCode = new DiscessCodeService();
    @Autowired
    private DiscessCodeService discessCoe;
    @Autowired
    private RequestCodeProServer requestCodeProServer;

    /**
     * 把通道传过来,处理
     *
     * @param ctx
     * @param buf
     */
    @Autowired
    Utils utils;
////3.容器初始化的时候进行执行-这里是重点
//@PostConstruct
//public void init() {
//    RequestCodeProServer = this;
//    nettyWebSocketServerHandler.appUserService = this.appUserService;
//    nettyWebSocketServerHandler.userCoinRecordService = this.userCoinRecordService;
//}
    public String processAddress(String ctx, String msg) throws Exception {
        try {

            utils = new Utils();
            RequestData requestData = utils.fenge(msg);
            String ipAddress = ctx;
            //1.判断什么模式
            //1.1出厂模式,把信息插入数据库
            if (StringUtils.equals(requestData.getWorkMode(), "1")) {
                System.out.println("出场模式");
            } else if (StringUtils.equals(requestData.getReqCode(), "1")) {//1.2工作模式,请求时间和天气
                //根据请求码来判断,请求什么功能
            /*
            1.表示请求时间+天气
             */
                String time = discessCode.disposeCode1Time(ipAddress);
                String werther = discessCode.disposeWeather(ipAddress);
                String timeAndWerther = "#" + time + werther + ",END";
                return timeAndWerther;
            } else if (StringUtils.equals(requestData.getReqCode(), "2")) {
              /*
            2.表示请求音乐配置信息
             */
                discessCode.disposeCode2MusicMsg();
            } else if (StringUtils.equals(requestData.getReqCode(), "3")) {
              /*
            3.表示请求音乐目录
             */
                discessCode.disposeCode3MusicCon();
            } else if (StringUtils.equals(requestData.getReqCode(), "4")) {
              /*
            4.表示请求音乐歌曲信息
             */
                discessCode.disposeCode4Music();
            } else if (StringUtils.equals(requestData.getReqCode(), "5")) {
              /*
            5.表示更新程序
             */
                discessCode.disposeCode5();
            }


        } catch (Exception e) {
            return "发送格式有错误";
        }
        return "发送格式有错误";
    }
}
