package com.sjw.magiconf.client.annotation;

import com.sjw.fastnetty.utils.StringPool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * @author : shijiawei
 * Date: 2019/7/15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MagiConf {
    String value() default StringPool.EMPTY;
}
