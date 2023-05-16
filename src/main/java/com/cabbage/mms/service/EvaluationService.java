package com.cabbage.mms.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cabbage.mms.Entity.Evaluation;
import com.cabbage.mms.mapper.EvaluationMapper;
import com.cabbage.mms.utils.PageUtils;
import com.github.pagehelper.PageInfo;

@SuppressWarnings("all")
@Service("evaluationService")
public class EvaluationService {

    @Autowired
    public EvaluationMapper evaluationMapper;

    public List<Evaluation> getCommentById(Integer empId) {
        return evaluationMapper.getCommentById(empId);
    }

    public int addComment(Evaluation comment) {
        return evaluationMapper.addComment(comment);
    }

    public List<Evaluation> getCommentsByMissionId(Integer missionId, Integer id) {
        return evaluationMapper.getCommentsByMissionId(id, missionId);
    }

    public List<Evaluation> getCommentsByMissionName(String missionName, Integer id) {
        return evaluationMapper.getCommentsByMissionName(id, missionName);
    }

}
