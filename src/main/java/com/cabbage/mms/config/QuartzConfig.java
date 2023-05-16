package com.cabbage.mms.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    // 这叫链式编程
    @Bean
    public JobDetail getJobDetail() {
        JobDetail jobDetail = JobBuilder.newJob(EmpJob.class)
                .withIdentity("triggerEmp", "triggerEmp")
                // JobDataMap可以给任务execute传递参数
                .usingJobData("job_param", "job_param1")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger getTrigger() {
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(getJobDetail())
                // .withIdentity("triggerEmp", "triggerEmp")
                .usingJobData("triggerEm_param", "triggerEmp_param1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 * * ?"))
                .build();
        return trigger;
    }

}
