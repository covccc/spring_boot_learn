package com.example.demo1.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author: huzw
 * @date: 2023-03-27
 */
@Component
@Slf4j
public class LifeCycleBean {

    public LifeCycleBean() {
        log.info("LifeCycleBean 构造");
    }

    @Autowired
    public void autowire(@Value("$JAVA_HOME") String home) {
        log.info("依赖注入：{}", home);
    }

    @PostConstruct
    public void init() {
        log.info("初始化之后...");
    }

    @PreDestroy
    public void destroy() {
        log.info("销毁之前...");
    }
}
