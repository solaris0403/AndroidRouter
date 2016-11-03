package com.tony.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要结合项目以及runtime/class运行效率及内存占用来分析
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RouterMap {
    String[] value();
}
