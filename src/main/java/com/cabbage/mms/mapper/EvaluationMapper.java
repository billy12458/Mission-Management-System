package com.cabbage.mms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cabbage.mms.Entity.Evaluation;

@Mapper
public interface EvaluationMapper {

    List<Evaluation> getCommentById(Integer empId);

    List<Evaluation> getCommentsByMissionId(@Param("id") Integer id, @Param("missionId") Integer missionId);

    List<Evaluation> getCommentsByMissionName(@Param("id") Integer id, @Param("missionName") String missionName);

    // 为项目的每一个人评价
    int addComment(Evaluation comment);

    int addComments(Evaluation[] comment);

}
