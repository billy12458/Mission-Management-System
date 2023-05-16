package com.cabbage.mms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
import com.cabbage.mms.Entity.Mission;

@Mapper
public interface MissionMapper {

    // @Select("SELECT * FROM mission")
    public List<Mission> findAll();

    public List<Mission> findMissionById(Integer id);

    // 还要在emp_mission表里面加上员工和任务的对应关系
    int addMission(Mission mission);

    public Mission getMissionByMissionId(Integer missionId);

    int insertEmpMission(@Param("id") Integer id, @Param("missionId") Integer missionId);

    public List<Mission> findCompletedById(Integer id);

    public List<Mission> findNotCompletedById(Integer id);

    public List<Mission> findEmergentById(Integer id);

    public List<Integer> getDepartmentIds();

    public Integer getDepartmentId(@Param("id") Integer id);

    public List<Integer> getEmpIdsInDepartment(Integer departId);

}
