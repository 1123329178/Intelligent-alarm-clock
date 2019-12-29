package com.clock.intelligent.clock.model;

import lombok.Data;

@Data
public class RequestData {
    /*
    //请求头
     */
    private String reqHead;
    /*
    序列号
     */
    private String serialNumber;
    /*
    请求功能吗
     */
    private String reqCode;
    /*
    //模块类型
     */
    private String ModuleType;
    /*
    //工作模式
     */
    private String workMode;
    /*
    //保留code
     */
    private String keepCode;



}
