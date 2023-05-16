package com.cabbage.mms.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * The utility class for getting beans in spring/spring boot environment
 * especially when you use shiro and {@code @Autowired} or {@code @Resource}
 * sometimes can fail
 * 
 * @author 86136
 * @version 0.1
 */
@Component
@SuppressWarnings("all")
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static Object getBean(Class clazz) {
        return context.getBean(clazz);
    }

}
