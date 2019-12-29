package com.clock.intelligent.clock.model;

import lombok.Data;

/**数据库表clockmsg对应的实体类
 * z主要是做缓存用
 * @author hp
 */
@Data
public class ClockMsg {
    private String id;
    private String serialNumber;
    private String clockMsg;
    private String msgstatus;
    private String createTime;
}
