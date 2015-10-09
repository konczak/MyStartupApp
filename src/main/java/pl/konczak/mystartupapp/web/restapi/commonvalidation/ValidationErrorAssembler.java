package pl.konczak.mystartupapp.web.restapi.commonvalidation;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidationErrorAssembler {

   private static final Logger LOGGER = LoggerFactory.getLogger(ValidationErrorAssembler.class);

   private final MessageSource messageSource;

   @Autowired
   public ValidationErrorAssembler(MessageSource messageSource) {
      this.messageSource = messageSource;
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
      LOGGER.info("in ValidationErrorAssembler.processValidationError");
      BindingResult result = ex.getBindingResult();
      List<FieldError> fieldErrors = result.getFieldErrors();

      ValidationErrorDTO validationErrorDTO = processFieldErrors(fieldErrors);
      LOGGER.error("validationErrorDTO <{}>", validationErrorDTO);
      return validationErrorDTO;
   }

   private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
      ValidationErrorDTO dto = new ValidationErrorDTO();

      fieldErrors.stream()
         .forEach(fieldError -> {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
         });

      return dto;
   }

   private String resolveLocalizedErrorMessage(FieldError fieldError) {
      Locale currentLocale = LocaleContextHolder.getLocale();
      LOGGER.info("Locale <{}>", currentLocale);
      String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

      LOGGER.info("localizedErrorMessage <{}>", localizedErrorMessage);
      if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
         String[] fieldErrorCodes = fieldError.getCodes();
         localizedErrorMessage = fieldErrorCodes[0];
      }
      LOGGER.info("localizedErrorMessage <{}> after", localizedErrorMessage);

      return localizedErrorMessage;
   }
}
