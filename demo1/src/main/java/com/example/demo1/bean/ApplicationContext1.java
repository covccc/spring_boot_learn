package com.example.demo1.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @author: huzw
 * @date: 2023/3/21
 */
@Slf4j
public class ApplicationContext1 {
    public static void main(String[] args) {

//        testClassPathXmlApplicationContext();
//        testFileSystemXmlApplicationContext();
//        testAnnotationConfigApplicationContext();
        testAnnotationConfigServletWebServerApplicationContext();

    }

    public static void testClassPathXmlApplicationContext() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("b01.xml");

        log.info("-----------ClassPathXmlApplicationContext--------------");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        //原理
        log.info("-----------ClassPathXmlApplicationContext source--------------");
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        log.info("读取之前...");
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new ClassPathResource("b01.xml"));
        log.info("读取之后...");
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }

    public static void testFileSystemXmlApplicationContext() {
        FileSystemXmlApplicationContext context =
                new FileSystemXmlApplicationContext("D:\\project\\spring_boot_learn\\demo1\\src\\main\\resources\\b01.xml");

        log.info("-----------FileSystemXmlApplicationContext--------------");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        //原理
        log.info("-----------FileSystemXmlApplicationContext source--------------");
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        log.info("读取之前...");
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new FileSystemResource("D:\\project\\spring_boot_learn\\demo1\\src\\main\\resources\\b01.xml"));
        log.info("读取之后...");
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

    }

    public static void testAnnotationConfigApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        log.info("-----------AnnotationConfigApplicationContext--------------");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(context.getBean(Bean1.class).getBean2());
    }

    /**
     * 基于web servlet（web环境）
     */
    public static void testAnnotationConfigServletWebServerApplicationContext() {
        log.info("-----------AnnotationConfigServletWebServerApplicationContext--------------");
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }

    @Configuration
    static class WebConfig{

        @Bean
        public ServletWebServerFactory servletWebServerFactory() {
            return new TomcatServletWebServerFactory();
        }

        @Bean
        public DispatcherServlet dispatcherServlet() {
            return new DispatcherServlet();
        }

        /**
         * 把dispatchServlet与Tomcat关联起来
         * @param dispatcherServlet
         * @return
         */
        @Bean
        public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
            return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        }

        @Bean("/hello")
        public Controller controller() {
            return (request, response) -> {
                response.getWriter().print("hello1232");
                return null;
            };
        }
    }

    //这些类不能为内部类  不然会报错(或者为静态类)
    @Configuration
    static class Config {

        @Bean
        Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        Bean2 bean2() {
            return new Bean2();
        }
    }

    @Data
    static class Bean1 {
        @Autowired
        private Bean2 bean2;
    }

    static class Bean2 {

    }
}
