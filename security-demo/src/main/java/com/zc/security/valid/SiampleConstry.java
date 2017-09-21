package com.zc.security.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Administrator on 2017/9/21 0021.
 */
public class SiampleConstry implements ConstraintValidator<MyConstry, Object> {


    @Override
    public void initialize(MyConstry myConstry) {
        System.out.println("initialize MyConstry");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("process MyConstry");

        return true;
    }
}
