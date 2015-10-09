package pl.konczak.mystartupapp.web.restapi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This annotation is used in REST API model to mark that LocalDateTime should be validated that is in supported range.
 *
 * @author Mateusz.Glabicki
 */
@Documented
@Constraint(validatedBy = RangeOfTimestampValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RangeOfTimestamp {

   String message() default "{RangeOfTimestamp}";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};
}
