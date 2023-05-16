package com.cabbage.mms.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cabbage.mms.Entity.Evaluation;
import com.cabbage.mms.Entity.Employee;
import com.cabbage.mms.Entity.Mission;
import com.cabbage.mms.mapper.EvaluationMapper;
import com.cabbage.mms.service.EvaluationService;
import com.cabbage.mms.service.MissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

//需要在这里调用missionMapper的方法
@Component
@SuppressWarnings("all")
public class PageUtils {

    @Autowired
    public MissionService missionService;

    @Autowired
    public EvaluationService evalService;

    public static PageInfo<Employee> getEmpInfo(int pageNum, int pageSize,
            boolean count,
            List<Employee> list) {
        PageHelper.startPage(pageNum, pageSize, count);
        PageInfo<Employee> infoPage = new PageInfo<>(list);
        return infoPage;
    }

    public PageInfo<Mission> getMissionInfo(String methodName, Integer id, int pageNum, int pageSize)
            throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InstantiationException {
        Method lisMethod = MissionService.class.getMethod(methodName, Integer.class);
        PageHelper.startPage(pageNum, pageSize, true);
        List<Mission> allList = (List<Mission>) lisMethod.invoke(missionService, id);
        PageInfo<Mission> infoPage = new PageInfo<>(allList);
        return infoPage;
    }

    public PageInfo<Evaluation> getCommentInfoById(Integer empId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true);
        List<Evaluation> allList = evalService.getCommentById(empId);
        PageInfo<Evaluation> infoPage = new PageInfo<>(allList);
        return infoPage;
    }

    public PageInfo<Evaluation> getCommentInfoByMissionId(Integer missionId, Integer id, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true);
        List<Evaluation> allList = evalService.getCommentsByMissionId(missionId, id);
        PageInfo<Evaluation> infoPage = new PageInfo<>(allList);
        return infoPage;
    }

    public PageInfo<Evaluation> getCommentInfoByMissionName(String missionName, Integer id, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true);
        List<Evaluation> allList = evalService.getCommentsByMissionName(missionName, id);
        PageInfo<Evaluation> infoPage = new PageInfo<>(allList);
        return infoPage;
    }

}
