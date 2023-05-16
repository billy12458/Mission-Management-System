package com.cabbage.mms.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cabbage.mms.Entity.Mission;
import com.cabbage.mms.Entity.Result;
import com.cabbage.mms.Entity.Status;
import com.cabbage.mms.service.DepartmentService;
import com.cabbage.mms.service.MissionService;
import com.cabbage.mms.utils.ApplicationContextUtils;
import com.cabbage.mms.utils.PageUtils;

@SuppressWarnings("all")
@RestController
@RequestMapping("/missions")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081", "http://localhost:8082" })
public class MissionController {

    @Autowired
    public RedisTemplate redisTemplate;

    public MissionService missionService() {
        return (MissionService) ApplicationContextUtils.getBean("missionService");
    }

    public PageUtils getPageUtils() {
        return (PageUtils) ApplicationContextUtils.getBean(PageUtils.class);
    }

    public DepartmentService departmentService() {
        return (DepartmentService) ApplicationContextUtils.getBean("departmentService");
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addMission(@RequestBody Mission mission, HttpServletResponse response) {
        try {
            missionService().addMission(mission);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Result returnDetailedMission(@RequestParam("id") Integer missionId) {
        try {
            return Result.success(missionService().getMissionByMissionId(missionId));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    // 就直接给前端传pageInfo，用vue渲染
    @RequestMapping(value = "/completed", method = RequestMethod.GET)
    public Result returnCompletedModelAndView(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "count", defaultValue = "false") boolean count, HttpServletResponse response)
            throws IOException {
        // String id = SecurityUtils.getSubject().getPrincipal().toString();
        try {
            return Result.success(getPageUtils().getMissionInfo("findCompletedById", 13654, pageNum, 5));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result returnAllMissionsModelAndView(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "count", defaultValue = "false") boolean count, HttpServletResponse response)
            throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InstantiationException {
        Integer id = Integer.parseInt(SecurityUtils.getSubject().getPrincipal().toString());
        // if (ObjectUtils.isEmpty((Status) redisTemplate.opsForValue().get(id))) {
        // Status status = new Status(true, new SimpleDateFormat("yyyy-MM-dd
        // HH:mm:ss").format(new Date()),
        // "nothing here", "");
        // redisTemplate.opsForValue().set(id, status);
        // } else {
        // Status status = (Status) redisTemplate.opsForValue().get(id);
        // status.setOnline(true);
        // status.setLastLoginTime(new SimpleDateFormat("yyyy-MM-dd
        // HH:mm:ss").format(new Date()));
        // redisTemplate.opsForValue().set(id, status);
        // }
        return Result.success(getPageUtils().getMissionInfo("findMissionById", id, pageNum, 5));

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result returnMissionsByIdModelAndView(
            @PathVariable("id") Integer missionId,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "count", defaultValue = "false") boolean count, HttpServletResponse response)
            throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InstantiationException {
        Integer id = getIntegerId(SecurityUtils.getSubject().getPrincipal().toString());
        return Result.success(missionService().getMissionByMissionId(missionId));
    }

    @RequestMapping(value = "/notCompleted", method = RequestMethod.GET)
    public Result returnNotCompletedModelAndView(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "count", defaultValue = "false") boolean count, HttpServletResponse response)
            throws IOException {
        try {
            // String id = SecurityUtils.getSubject().getPrincipal().toString();
            return Result.success(getPageUtils().getMissionInfo("findNotCompletedById", 13654, pageNum, 5));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    // 写出来格式就像：localhost:80/missions/emergent?pageNum=6
    @RequestMapping(value = "/emergent", method = RequestMethod.GET)
    public Result returnEmergentModelAndView(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "count", defaultValue = "false") boolean count, HttpServletResponse response)
            throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InstantiationException {
        // String id = SecurityUtils.getSubject().getPrincipal().toString();
        return Result.success(getPageUtils().getMissionInfo("findEmergentById", 13654, pageNum, 5));
    }

    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    public Result returnDepartIdsResult()
            throws IOException {
        // String id = SecurityUtils.getSubject().getPrincipal().toString();
        return Result.success(missionService().getDepartmentIds());
    }

    @RequiresRoles(value = { "director", "user" })
    @RequestMapping(value = "/emps", method = RequestMethod.GET)
    public Result returnEmpIdsResult()
            throws IOException {
        Integer id = getIntegerId(SecurityUtils.getSubject().getPrincipal().toString());
        Integer departId = missionService().getDepartmentId(id);
        return Result.success(departmentService().getEmpIds(departId));
    }

    // @RequiresRoles(value = { "director", "user" })
    @RequestMapping(value = "/allEmps", method = RequestMethod.GET)
    public Result returnAllEmpIdsResult()
            throws IOException {
        return Result.success(departmentService().getAllEmps());
    }

    private Integer getIntegerId(String id) {
        return Integer.parseInt(id);
    }
}
