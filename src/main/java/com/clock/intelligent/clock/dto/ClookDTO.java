package com.clock.intelligent.clock.dto;

import com.clock.intelligent.clock.model.ClockUser;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ClookDTO {
    private String M;// 2字符
    private String N;
    private String total;//TOTAL表示一共多少配置行   3字节
    private String timingM;
    private String seralNumber;
    private String timeLengthM2;//M2表示播放音乐持续的分钟  2字符
    private String timeLengthS2;//S2表示播放音乐持续的秒   2字符
    private String weekday;//WEEKDAY为8字符的数据，每个字符代表播放的星期几
    //从左到右分别表示周一，到周日，最后一个字符表示是否循环播放
    //比如1111001 表示周1，2，3，4，日开启音乐，周5，6不允许播放
    private String musicpath;
    private String creater;//创建人的ID
    private ClockUser clockUser;

}
