package com.clock.intelligent.clock.model;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author hp
 */
@Component
@Data
public class Clook {
    private Integer id;
    private String M;// 2字符
    private String N;
    private String hour;
    private String minute;
    private String total;//TOTAL表示一共多少配置行   3字节
    private String timingM;
    private String serialNumber;
    private String timeLengthM2;//M2表示播放音乐持续的分钟  2字符
    private String timeLengthS2;//S2表示播放音乐持续的秒   2字符
    private String weekday;//WEEKDAY为8字符的数据，每个字符代表播放的星期几
                            //从左到右分别表示周一，到周日，最后一个字符表示是否循环播放
                            //比如1111001 表示周1，2，3，4，日开启音乐，周5，6不允许播放
    private String musicpath;
    private String creator;//创建人的ID
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getM() {
        return M;
    }

    public void setM(String m) {
        M = m;
    }

    public String getN() {
        return N;
    }

    public void setN(String n) {
        N = n;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTimingM() {
        return timingM;
    }

    public void setTimingM(String timingM) {
        this.timingM = timingM;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTimeLengthM2() {
        return timeLengthM2;
    }

    public void setTimeLengthM2(String timeLengthM2) {
        this.timeLengthM2 = timeLengthM2;
    }

    public String getTimeLengthS2() {
        return timeLengthS2;
    }

    public void setTimeLengthS2(String timeLengthS2) {
        this.timeLengthS2 = timeLengthS2;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getMusicpath() {
        return musicpath;
    }

    public void setMusicpath(String musicpath) {
        this.musicpath = musicpath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {

        return "Clook{" +
                "id=" + id +
                ", M='" + M + '\'' +
                ", N='" + N + '\'' +
                ", hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                ", total='" + total + '\'' +
                ", timingM='" + timingM + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", timeLengthM2='" + timeLengthM2 + '\'' +
                ", timeLengthS2='" + timeLengthS2 + '\'' +
                ", weekday1='" + weekday + '\'' +
                ", musicpath='" + musicpath + '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }
}
