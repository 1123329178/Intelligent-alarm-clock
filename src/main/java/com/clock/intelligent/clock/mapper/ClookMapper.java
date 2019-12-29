package com.clock.intelligent.clock.mapper;

import com.clock.intelligent.clock.model.Clook;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClookMapper {
    @Select( "select * from Clook where #{id}=id")
    Clook findById(int id);
    @Select( "select * from Clook where #{creator}=creator")
    List<Clook> findAllClook(String creator);

    @Insert("insert into clook  ( timingM ,serialNumber,timeLengthM2,timeLengthS2," +
            "weekday,creator,M,N,musicpath,createTime,hour,minute) values " +
            "(#{timingM},#{serialNumber},#{timeLengthM2},#{timeLengthS2},#{weekday},#{creator},#{M},#{N},#{musicpath},#{createTime},#{hour},#{minute})")
    void insert(Clook clook);
}
