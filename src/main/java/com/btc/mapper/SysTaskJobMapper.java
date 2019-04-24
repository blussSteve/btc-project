package com.btc.mapper;

import org.apache.ibatis.annotations.Select;

import com.btc.model.SysTaskJob;

public interface SysTaskJobMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysTaskJob record);

    int insertSelective(SysTaskJob record);

    SysTaskJob selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysTaskJob record);

    int updateByPrimaryKeyWithBLOBs(SysTaskJob record);

    int updateByPrimaryKey(SysTaskJob record);
    
    @Select("SELECT count(1)>0 FROM sys_task_job t WHERE t.job_type=#{arg0} AND DATE(t.count_date)=DATE(#{arg1}) AND t.is_success=1")
    boolean checkJobIsRun(int type,String date);
}