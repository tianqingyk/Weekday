package com.team3.weekday.net.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-05
 */

@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Action {
}
