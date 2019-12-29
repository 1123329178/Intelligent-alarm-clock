package com.clock.intelligent.clock.controller;

import com.clock.intelligent.clock.configuration.ApplicationContextFactory;
import com.clock.intelligent.clock.mapper.ClockUserMapper;
import com.clock.intelligent.clock.model.ClockUser;
import com.clock.intelligent.clock.model.RequestData;
import com.clock.intelligent.clock.service.ClockUserService;
import com.clock.intelligent.clock.uitls.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * Clockuser  控制处理层  数据库  网页
 *
 * @author hp
 */
@Component
public class ClockUserController {
    @Autowired
    private Utils utils = new Utils();
    @Autowired
    ClockUserMapper clockUserMapper;
    @Autowired
    ClockUserService clockUserService;


    public ClockUser findClockUser(String mesg) {

        if (StringUtils.isBlank(mesg)){
            return null;
        }
        RequestData requestData = utils.fenge(mesg);

        String SerialNumber= requestData.getSerialNumber();
        ClockUser clockUser = clockUserMapper.findClockUserBySerialNumber(SerialNumber);
        /*
        这里要加上redis  进行校验是否登录多次
         */
        if (clockUser!=null){
            return clockUser;
        }else {
            ClockUser user = new ClockUser();
            user.setId(requestData.getSerialNumber());
            user.setAccess(" ");
            user.setLogins(1);
            clockUserMapper.InsertClockUser(user);
        }
        return null;

    }
    /**
     * @param user
     * 查询 是否存在  不存在就插入   存在就更新
     */
    public void findAndUpdateClockUser(ClockUser user) {
        if (user==null)
        {
            return ;
        }
        ClockUser clockUser = clockUserMapper.findClockUserBySerialNumber(user.getSerialNumber());
         //不等于空进行更新密码
        if (clockUser!=null){
            clockUser.setName(user.getName());
            clockUser.setPassword(user.getPassword());
            clockUser.setAccountid(user.getAccountid());
            clockUserMapper.UpdateClockUser(clockUser);
        }else{
            //否则进行插入数据库
            clockUserMapper.InsertClockUser(user);
        }
    }
}
