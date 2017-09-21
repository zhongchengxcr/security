package com.zc.security.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2017/9/21 0021.
 */
@Constraint(validatedBy = {SiampleConstry.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface MyConstry {

    String message() default "这是一个测试注解";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
