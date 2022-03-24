package com.malred.micro_mvc.Handler;

import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * @author malguy-wang sir
 * @create ---
 */
public interface HandlerMapping extends InstantiationAwareBeanPostProcessor {
    Object getHandler(String url);
}
