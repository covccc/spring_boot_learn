package com.example.demo1.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: huzw
 * @date: 2023/3/21
 */
public class BeanFactory1 {

    /**
     * bean工厂相关方法
     *
     * @param args
     */

    /**
     * 学到了什么：
     * a.beanFactory不会做的事
     * 1.不会主动调用BeanFactory后处理器
     * 2.不会主动添加Bean后处理器
     * 3.不会主动初始化单例工
     * 4.不会解析beanFactory还不会解析${}与#{}
     * b.bean后处理器会有排序的逻辑
     *
     * @param args
     */

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(Config.class)
                .setScope(BeanDefinition.SCOPE_SINGLETON)
                .getBeanDefinition();

        //注册bean定义到bean工厂
        beanFactory.registerBeanDefinition("config", beanDefinition);

        //注册后置处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        //执行bean工厂后置处理器
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values()
                .forEach(beanFactoryPostProcessor -> beanFactoryPostProcessor.postProcessBeanFactory(beanFactory));

        //beanFactory.addBeanPostProcessors(beanFactory.getBeanPostProcessors());
        System.out.println("-------------getBeanPostProcessors()---------------");
        beanFactory.getBeanPostProcessors().forEach(System.out::println);

        System.out.println("-------------getBeansOfType(BeanPostProcessor.class).values()---------------");
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(System.out::println);

        //添加bean后置处理器
        beanFactory.addBeanPostProcessors(beanFactory.getBeansOfType(BeanPostProcessor.class).values());

        //获取bean工厂所有bean名字
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        //提前创建bean
        beanFactory.preInstantiateSingletons();

        //获取深层次bean
        System.out.println("-------------深层次bean---------------");
        System.out.println(beanFactory.getBean(Bean1.class).getBean2());
        //System.out.println(beanFactory.getBean(Config.class));

    }
}

//这些类不能为内部类  不然会报错
@Configuration
class Config {

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
class Bean1 {

    @Autowired
    private Bean2 bean2;

}

class Bean2 {

}
