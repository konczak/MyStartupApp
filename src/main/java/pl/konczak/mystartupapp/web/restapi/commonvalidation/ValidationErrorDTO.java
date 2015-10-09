package pl.konczak.mystartupapp.web.restapi.commonvalidation;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO {

   private final List<FieldErrorDTO> fieldErrors = new ArrayList<>();

   public ValidationErrorDTO() {
   }

   public List<FieldErrorDTO> getFieldErrors() {
      return fieldErrors;
   }

   public void addFieldError(String path, String message) {
      FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(path, message);
      this.fieldErrors.add(fieldErrorDTO);
   }

   @Override
   public String toString() {
      return String.format("fieldErrors <%s>", fieldErrors);
   }
}
