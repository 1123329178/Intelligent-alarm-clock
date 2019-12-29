package com.clock.intelligent.clock.mapper;

import com.clock.intelligent.clock.model.ClockUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**数据库映射接口
 * clockuser
 * @author hp
 */

public interface ClockUserMapper {
    @Insert("insert into clockuser  ( logins ,access,serialNumber,password,name,accountid) values (#{logins},#{access},#{serialNumber},#{password},#{name},#{accountid})")
    void InsertClockUser(ClockUser clockUser);
    @Select("select * from clockuser where  serialNumber=#{serialNumber}")
    ClockUser findClockUserBySerialNumber(@Param("serialNumber") String serialNumber);
    @Update("update clockuser set password= #{password},name=#{name},accountid=#{accountid}where  serialNumber=#{serialNumber}")
    int UpdateClockUser(ClockUser clockUser);

    @Select("select * from clockuser where  accountid=#{accountid} and password= #{password}")
    ClockUser findClockUserByAccPass(ClockUser clockUser);
}
