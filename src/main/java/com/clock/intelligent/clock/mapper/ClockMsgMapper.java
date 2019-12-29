package com.clock.intelligent.clock.mapper;

import com.clock.intelligent.clock.model.ClockMsg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * ClockMsg 数据库 SQL语句
 *
 * @author hp
 */

public interface ClockMsgMapper {
    /**
     * @param clockMsg
     */
    @Insert("insert into clockmsg  (serialNumber,clockMsg,msgstatus,createTime) values(#{serialNumber},#{clockMsg},#{msgstatus},#{createTime})")
    void insertMsg(ClockMsg clockMsg);

    /**
     * @param serialNumber
     * @param i 状态
     * @return
     */
    @Select("select * from clockmsg where serialNumber=#{serialNumber} and  msgstatus =#{msgstatus}")//返回应该是list
    List<ClockMsg> findMsg(@Param("serialNumber") String serialNumber,  @Param("msgstatus") String msgstatus);

    /**
     * @param clockMsg
     * @return
     */
    @Update("update clockmsg set msgstatus =#{msgstatus} where serialNumber=#{serialNumber}  and createTime= #{createTime}")
    int  updateMsg(ClockMsg clockMsg);
}
