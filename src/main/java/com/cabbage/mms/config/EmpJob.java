package com.cabbage.mms.config;

import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

public class EmpJob implements Job {

    // 记得加上ThreadContext，否则shiro会一直报错
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Subject subject = SecurityUtils.getSubject();
        /*
         * switch (format.format(new Date())) {
         * case TimeEnum.EARLY_LUNCH.getNumberOfTime():
         * alert(subject.getPrincipal().toString(), "歪比巴布");
         * break;
         * }
         */
        StringBuffer buffer = new StringBuffer();
        buffer.append(new Date().toString() + " ");
        buffer.append("该吃饭了/该休息了");
        System.out.println(buffer.toString());
    }

    private void alert(String userName, String message) {

    }

}
