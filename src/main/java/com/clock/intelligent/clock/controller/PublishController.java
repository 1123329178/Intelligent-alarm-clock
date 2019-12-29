package com.clock.intelligent.clock.controller;


import com.clock.intelligent.clock.SingleMap.SingletonHashMap;
import com.clock.intelligent.clock.dto.ClookDTO;
import com.clock.intelligent.clock.mapper.ClockMsgMapper;
import com.clock.intelligent.clock.model.ClockUser;
import com.clock.intelligent.clock.model.Clook;
import com.clock.intelligent.clock.nettyHandler.NettyServerHandler;
import com.clock.intelligent.clock.service.ClookService;
import com.clock.intelligent.clock.uitls.PublishUtils;
import io.netty.channel.Channel;
import org.apache.commons.collections4.map.SingletonMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 发布问题控制类
 *
 * @author hp
 */
@Controller
public class PublishController {
    @Autowired
    private ClookService clookService;
    @Autowired
    private PublishUtils publishUtils;
    @Autowired
    private ClockSendMsgService clockSendMsgService;

    SingletonHashMap<String, Channel> channelMap = SingletonHashMap.getInstance();

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") int id,
                       Model model) {
        ClookDTO clookDTO = clookService.getId(id);
        model.addAttribute("musicpath", clookDTO.getMusicpath());
        model.addAttribute("weekday", clookDTO.getWeekday());
//        model.addAttribute("tag", clookDTO.getTag());
//        model.addAttribute("id", clookDTO.getId());
        return "publish";
    }


    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "weekday", required = false) String weekday,
            @RequestParam(value = "timeLengthHM", required = false) String timeLengthHM,
            @RequestParam(value = "timeLength", required = false) String timeLength,
            @RequestParam(value = "musicpath", required = false) String musicpath,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("weekday", weekday);
        model.addAttribute("timeLengthHM", timeLengthHM);
        model.addAttribute("timeLength", timeLength);
        model.addAttribute("musicpath", musicpath);

        if (weekday == null || weekday == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (timeLengthHM == null || timeLengthHM == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (timeLength == null || timeLength == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        if (musicpath == null || musicpath == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        ClockUser user = (ClockUser) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Clook clook = new Clook();
        clook.setHour(publishUtils.getTimeLengthM(timeLengthHM));//闹铃响的小时
        clook.setMinute(publishUtils.getTimeLengthS(timeLengthHM));//闹铃响的分钟
        clook.setTimeLengthM2(publishUtils.getTimeLengthM(timeLength));//音乐响长度 分钟
        clook.setTimeLengthS2(publishUtils.getTimeLengthS(timeLength));//音乐响长度 秒
        clook.setWeekday(weekday);
        clook.setMusicpath(musicpath);
        clook.setCreator(user.getId());
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentTime = simpleDate.format(date);
        clook.setCreateTime(currentTime);

        clook.setSerialNumber(user.getSerialNumber());
        //先查看是不是客户端闹钟有没有连接,有就返回通道channel
        Channel channel = getChannelStatus(clook.getSerialNumber());
        if (channel != null) {
            //创建问题或者插入
            //发送给客户端音乐信息,并且结束心跳包
            clockSendMsgService.SendMsgHeartBeat(channel, clook);
        } else {
            //存入一个表中,当一个客户端上线的时候,发送msg
            clockSendMsgService.insertMsg(clook);
        }
        clookService.createOrUpdate(clook);
        return "redirect:/";
    }

    public Channel getChannelStatus(String seralNumber) {
        if (StringUtils.isNotBlank(seralNumber)) {
            for (String s : channelMap.keySet()) {
                String[] split = s.split("@");
                if (split[1].equals(seralNumber)) {
                    Channel channel = channelMap.get(s);
                    if (channel != null) {
                        return channel;
                    }
                }
            }


        }
        return null;
    }

}
