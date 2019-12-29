package com.clock.intelligent.clock.controller;

import com.clock.intelligent.clock.service.ManageClientService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 当 使用网页管理 客户端歌曲的时候
 *
 * @author 刘亚博
 */
@RestController
public class ManageClientController {

@Autowired
private ManageClientService manageClientService;
    @RequestMapping
    public void chickMusic(String music,
                           Session session) {
        if (StringUtils.isNotBlank(music)) {
            /*通过session 获得密码账号
             *通过密码账号,在数据库中查询 序列码,同时插入歌曲信息.
             * 通过序列码在ChannelGroup 找到Channel 发送修改成功
             */
        }
    }

}
