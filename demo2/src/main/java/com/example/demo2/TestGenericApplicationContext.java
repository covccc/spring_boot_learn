package com.example.demo2;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author: huzw
 * @date: 2023-03-27
 */
public class TestGenericApplicationContext {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();

        context.registerBean("bea1", Bean1.class);
        context.registerBean("bea2", Bean2.class);
        context.registerBean("bea3", Bean3.class);

        context.getDefaultListableBeanFactory()
                .setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);

        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        context.refresh();

        context.close();
    }
}
