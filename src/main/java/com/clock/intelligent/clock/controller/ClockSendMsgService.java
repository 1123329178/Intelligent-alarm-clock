package com.clock.intelligent.clock.controller;

import com.clock.intelligent.clock.mapper.ClockMsgMapper;
import com.clock.intelligent.clock.model.ClockMsg;
import com.clock.intelligent.clock.model.Clook;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 前端插入音乐,现在给客户端 单片机同步发送歌曲信息,并且以心跳包检测
 *
 * @author hp
 */
@Service
public class ClockSendMsgService {
    @Autowired
    private ClockMsgMapper clockMsgMapper;

    public void SendMsgHeartBeat(Channel channel, Clook clook) {
        String msg1 = getMsgString(clook);
        System.out.println(channel.remoteAddress());
        channel.writeAndFlush(Unpooled.copiedBuffer(msg1, CharsetUtil.UTF_8));
    }

    private String getMsgString(Clook clook) {
        String weekDay = "";
        if (clook.getN() == null) {
            clook.setN("002");
        }
        if (clook.getTotal() == null) {
            clook.setN("003");
        }
        if (clook.getWeekday() == null) {
            clook.setWeekday("11111000");
        } else {
            String weekdays = clook.getWeekday();
            String cou = "1234567";

            for (int i = 0; i < 7; i++) {

                if (StringUtils.contains(weekdays, cou.charAt(i))) {
                    weekDay = weekDay + cou.charAt(i);
                }

            }


        }

        String msg = "*M," + clook.getN() +
                "," + clook.getTotal() +
                "," + clook.getMusicpath() +
                "," + clook.getHour() +
                "," + clook.getMinute() +
                "," + clook.getTimeLengthM2() +
                "," + clook.getTimeLengthS2() +
                "," + weekDay +
                "," + "ENDN";
        return msg;
    }

    /**
     * 客户端没有登录的话,就直接存入数据库当连接的时候就发送.
     *
     * @param clook
     */
    public void insertMsg(Clook clook) {
        String seralNumber = clook.getSerialNumber();
        String msg1 = getMsgString(clook);
        ClockMsg clockMsg = new ClockMsg();
        clockMsg.setClockMsg(msg1);
        clockMsg.setMsgstatus("1");
        clockMsg.setSerialNumber(seralNumber);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentTime = simpleDate.format(date);
        clockMsg.setCreateTime(currentTime);
        if (StringUtils.isNotBlank(msg1)) {
            clockMsgMapper.insertMsg(clockMsg);
        }
    }
}
