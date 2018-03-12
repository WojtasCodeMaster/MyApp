package com.test.demo.date;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.*;
import java.time.LocalDate;

@Target({ElementType.FIELD}) // ograniczenie, że tę adnotację możemy zastosować jedynie do pól
@Retention(RetentionPolicy.RUNTIME) //adnotacje będą wykrywane w czasie wykonywania programu
@Constraint(validatedBy = PastLocalDate.PastValidator.class)
@Documented
public @interface PastLocalDate{
    String message() default "{javax.validation.constraints.Past.message}";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
    class PastValidator implements ConstraintValidator<PastLocalDate, LocalDate> {
        public void initialize(PastLocalDate past){
        }
        public  boolean isValid(LocalDate localDate, ConstraintValidatorContext context){
            return localDate == null || localDate.isBefore(LocalDate.now());
        }
    }

}