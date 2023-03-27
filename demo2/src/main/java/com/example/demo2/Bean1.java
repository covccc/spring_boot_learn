package com.example.demo2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author: huzw
 * @date: 2023-03-27
 */
@Slf4j
public class Bean1 {


    @Autowired
    public String home(@Value("${JAVA_HOME}") String home) {
        log.info("Autowired:{}", home);
        return home;
    }

    public Bean2 getBean2() {
        return bean2;
    }

    @Autowired
    public void setBean2(Bean2 bean2) {
        log.info("Autowired ....");
        this.bean2 = bean2;
    }

    public Bean3 getBean3() {
        return bean3;
    }

    @Resource
    public void setBean3(Bean3 bean3) {
        log.info("Resource ....");
        this.bean3 = bean3;
    }


    private Bean2 bean2;


    private Bean3 bean3;

    @PostConstruct
    public void init() {
        log.info("初始化之后...");
    }

    @PreDestroy
    public void destroy() {
        log.info("销毁之前...");
    }

}
