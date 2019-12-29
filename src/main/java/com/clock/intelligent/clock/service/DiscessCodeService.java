package com.clock.intelligent.clock.service;

import com.clock.intelligent.clock.uitls.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能code处理类
 *
 * @author hp
 */
@Component
public class DiscessCodeService {
    @Autowired
    Utils utils;

    /**
     * 请求网络时间
     *
     * @param
     */
    public String disposeCode1Time(String ipAddress) throws Exception {
        String ntptime;
        String[] ips = this.utils.getIpAddress(ipAddress);
        String diqu = this.utils.getAddressByIP(ips[0]);
        if (diqu==null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd,hh,mm,ss,ss");
            Date date = new Date();
            ntptime = dateFormat.format(date)+",";
            return ntptime;
        }
        ntptime = this.utils.getNtptime(diqu)+",";

        return ntptime;
    }

    public void disposeCode2MusicMsg() {
    }

    public void disposeCode2Music() {
    }


    public void disposeCode4Music() {
    }

    public void disposeCode5() {
    }

    public void disposeCode3MusicCon() {
    }

    /**
     * 请求天气
     * @param ipAddress
     */
    public String disposeWeather(String ipAddress) {
        String[] ips = utils.getIpAddress(ipAddress);
        String tianQi = utils.getTianQi(ips[0]);
        return tianQi;
    }
}
