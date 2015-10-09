package pl.konczak.mystartupapp.web.restapi.commonvalidation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Use this abstract validator when you need to validate your REST API body objects with @Valid. It will take care about
 * validation of simple java.validation.constraints
 *
 * @author Piotr.Konczak
 */
public abstract class AbstractValidator
   extends LocalValidatorFactoryBean
   implements Validator {

   /**
    * Define your custom validation and errors.
    *
    * @param target
    * @param errors
    */
   public abstract void customValidation(Object target, Errors errors);

   @Override
   public final void validate(Object target, Errors errors) {
      super.validate(target, errors);
      if (errors.hasErrors()) {
         return;
      }
      customValidation(target, errors);
   }

   @Override
   public final void validate(Object target, Errors errors, Object... validationHints) {
      super.validate(target, errors, validationHints);
      if (errors.hasErrors()) {
         return;
      }
      customValidation(target, errors);
   }
}
