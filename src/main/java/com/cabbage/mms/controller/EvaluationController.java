package com.cabbage.mms.controller;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cabbage.mms.Entity.Result;
import com.cabbage.mms.service.EvaluationService;
import com.cabbage.mms.utils.ApplicationContextUtils;
import com.cabbage.mms.utils.PageUtils;
import com.github.pagehelper.PageInfo;

//和前端说清楚用pageSize = 5就行了
@SuppressWarnings("all")
@RestController
@RequestMapping("/evaluation")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081", "http://localhost:8082" })
public class EvaluationController {

    public EvaluationService evaluationService() {
        return (EvaluationService) ApplicationContextUtils.getBean("evaluationService");
    }

    public PageUtils getPageUtils() {
        return (PageUtils) ApplicationContextUtils.getBean(PageUtils.class);
    }

    // 加几个配置bean
    @RequiresRoles(value = { "user" })
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result getEvaluationById(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "count", defaultValue = "false") boolean count) {
        try {
            Integer id = getIntegerId(SecurityUtils.getSubject().getPrincipal().toString());
            // 加上任务名称，否则不知道是哪个任务
            return Result.success(getPageUtils().getCommentInfoById(id, pageNum, 5));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @RequiresRoles(value = { "user" })
    @RequestMapping(value = "/{missionId}", method = RequestMethod.GET)
    public Result getEvaluationByMissionId(@PathVariable("missionId") Integer missionId,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "count", defaultValue = "false") boolean count) {
        Integer id = getIntegerId(SecurityUtils.getSubject().getPrincipal().toString());
        return Result.success(getPageUtils().getCommentInfoByMissionId(missionId, id, pageNum, 5));
    }

    @RequiresRoles(value = { "user" })
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Result getEvaluationByMissionName(@RequestParam("name") String missionName,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "count", defaultValue = "false") boolean count) {
        Integer id = getIntegerId(SecurityUtils.getSubject().getPrincipal().toString());
        // 加上任务名称，否则不知道是哪个任务
        return Result.success(getPageUtils().getCommentInfoByMissionName(missionName, id, pageNum, 5));
    }

    private Integer getIntegerId(String id) {
        return Integer.parseInt(id);
    }
}