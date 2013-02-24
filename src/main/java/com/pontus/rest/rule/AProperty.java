package com.pontus.rest.rule;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: pontus
 * Date: 11/6/12
 * Time: 10:07 AM
 * To change this template use File | Settings | File Templates.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface AProperty {
}
