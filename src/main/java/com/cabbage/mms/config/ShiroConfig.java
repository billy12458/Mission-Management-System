package com.cabbage.mms.config;

import java.util.LinkedHashMap;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

/**
 * The configuration class for shiro framework
 * 
 * @author 86136
 * @author bill12458
 * @version 0.2
 */
@SuppressWarnings("all")
@Configuration
public class ShiroConfig {

    @Autowired
    public Environment environment;

    @Bean
    public ShiroFilterFactoryBean getFactoryBean() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 这里用haspmap也没啥事，不过linked更好
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<String, String>();
        factoryBean.setLoginUrl("/login");
        factoryBean.setSuccessUrl("/success");
        // 这样放行后就可以访问到静态资源了
        filterMap.put("/static/**", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/templates/css/**", "anon");
        filterMap.put("/templates/js/**", "anon");
        filterMap.put("/templates/img/**", "anon");
        filterMap.put("/templates/fonts/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/img/**", "anon");
        filterMap.put("/index", "anon");
        filterMap.put("/fonts/**", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/missions", "anon");
        filterMap.put("/missions/**", "anon");
        filterMap.put("/register", "anon");
        filterMap.put("/register/sure", "anon");
        filterMap.put("/user/getImage", "anon");
        filterMap.put("/register/getImage", "anon");
        filterMap.put("/upload", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-ui/index.html", "anon");
        filterMap.put("/readme", "authc");
        filterMap.put("/**", "user");
        // 其他网页全部需要authentication
        factoryBean.setFilterChainDefinitionMap(filterMap);
        factoryBean.setSecurityManager(getManager());
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setCacheManager(getCacheManager());
        manager.setRememberMeManager(getRememberMe());
        // 设置为自定义的ShiroRealm
        manager.setRealm(getCustomRealm());
        return manager;
    }

    @Bean
    public CacheManager getCacheManager() {
        CacheManager cManager = null;
        return cManager;
    }

    /**
     * The function for encrypting specific passwords
     */
    @Bean
    public ShiroRealm getCustomRealm() {
        ShiroRealm realm = new ShiroRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA-256");
        matcher.setHashIterations(512);
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    @Bean
    public SimpleCookie getCookie() {
        // 可以对应前端的是否选择记住我
        SimpleCookie simpleCookie = new SimpleCookie("rememberMongo");
        // 禁止通过js脚本读取cookie，防止xss攻击
        simpleCookie.setMaxAge(864000);
        simpleCookie.setHttpOnly(true);
        // simpleCookie.setSecure(true);
        return simpleCookie;
    }

    /** The method responsible for summoning cookies and encrypting them */
    @Bean
    public CookieRememberMeManager getRememberMe() {
        CookieRememberMeManager rememberMe = new CookieRememberMeManager();
        rememberMe.setCookie(getCookie());
        rememberMe.setCipherKey(environment.getProperty("org.billy.key").getBytes());
        return rememberMe;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(getManager());
        return authorizationAttributeSourceAdvisor;
    }
}
