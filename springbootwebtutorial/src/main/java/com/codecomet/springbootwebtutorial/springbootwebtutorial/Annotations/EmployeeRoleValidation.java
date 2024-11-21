package com.codecomet.springbootwebtutorial.springbootwebtutorial.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.Internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {EmployeeRoleValidator.class})
public @interface EmployeeRoleValidation {

    String message() default "The Role od employee can be USER or ADMIN only";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
