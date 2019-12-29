package com.clock.intelligent.clock.model;

import lombok.Data;

/**闹钟实体类
 * @author hp
 */
@Data
public class ClockUser {
    private String id;
    private Integer logins;
    private String access;
    private String serialNumber;
    private String accountid;
    private String password;
    private String name;

}
