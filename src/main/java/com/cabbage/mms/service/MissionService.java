package com.cabbage.mms.service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import com.cabbage.mms.Entity.Mission;
import com.cabbage.mms.mapper.MissionMapper;

@Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
@Service("missionService")
public class MissionService {

    @Autowired
    public MissionMapper missionMapper;

    @Autowired
    public FileService fileService;

    public List<Mission> getAllMissions() {
        return missionMapper.findAll();
    }

    // 看看后端怎么取复选框中的值
    public int addMission(Mission mission) throws IOException {
        // 默认status就是0
        // mission.setDescription(mission.getDescription());
        System.out.println(mission.getTo());
        for (Integer id : mission.getTo()) {
            missionMapper.insertEmpMission(id, mission.getMissionId());
        }
        fileService.uploadFileToMongo(mission.getMissionId(), mission.getMultipartFile());
        return missionMapper.addMission(mission);
        // mission.setStatus(0);
        // mission.setDescription(mission.getDescription());
        // JSONArray array = new JSONArray();
        // for (String empName : mission.getTo().split(",")) {
        // JSONObject object = new JSONObject();
        // object.put("员工号", empName);
        // array.add(object);
        // }
        // mission.setTo(array.toString());
        // return missionMapper.addMission(mission);
    }

    /*
     * public List<Mission> findMissionById(String id) {
     * return missionMapper.findMissionById(id);
     * }
     */

    @Cacheable(value = "missionCache", key = "#missionId")
    public Mission getMissionByMissionId(Integer missionId) {
        return missionMapper.getMissionByMissionId(missionId);
    }

    // 这里固定是比如说20条
    public List<Mission> findMissionById(Integer id) {
        return missionMapper.findMissionById(id);
    }

    public List<Mission> findAllMissionByEmpId(Integer id) {
        return missionMapper.findAll();
    }

    public List<Mission> findCompletedById(Integer id) {
        return missionMapper.findCompletedById(id);
    }

    public List<Mission> findNotCompletedById(Integer id) {
        return missionMapper.findNotCompletedById(id);
    }

    public List<Mission> findEmergentById(Integer id) {
        return missionMapper.findEmergentById(id);
    }

    public List<Integer> getDepartmentIds() {
        return missionMapper.getDepartmentIds();
    }

    public List<Integer> getEmpIdsInDepartment(Integer departId) {
        return missionMapper.getEmpIdsInDepartment(departId);
    }

    public Integer getDepartmentId(Integer id) {
        return missionMapper.getDepartmentId(id);
    }
}
