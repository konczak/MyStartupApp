package pl.konczak.mystartupapp.web.restapi.questionnairetemplate;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.konczak.mystartupapp.domain.question.dto.QuestionnaireTemplateSnapshot;
import pl.konczak.mystartupapp.domain.question.finder.IQuestionnaireTemplateSnapshotFinder;
import pl.konczak.mystartupapp.sharedkernel.annotations.RestValidator;
import pl.konczak.mystartupapp.sharedkernel.enums.ValidLanguages;
import pl.konczak.mystartupapp.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class QuestionnaireTemplateNewValidator extends AbstractValidator {

   private final IQuestionnaireTemplateSnapshotFinder questionnaireTemplateSnapshotFinder;

   @Autowired
   public QuestionnaireTemplateNewValidator(IQuestionnaireTemplateSnapshotFinder questionnaireTemplateSnapshotFinder) {
      this.questionnaireTemplateSnapshotFinder = questionnaireTemplateSnapshotFinder;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return QuestionnaireTemplateNew.class.isAssignableFrom(clazz);
   }
      
   @Override
   public void customValidation(Object target, Errors errors) {
      QuestionnaireTemplateNew questionnaireTemplateNew = (QuestionnaireTemplateNew) target;
      
      if (questionnaireTemplateWithGivenNameAlreadyExists(questionnaireTemplateNew)) {
         errors.rejectValue("name", "QuestionnaireTemplateWithGivenNameAlreadyExists");
      }
      
      if(languageIsNotAllowed(questionnaireTemplateNew)){
         errors.rejectValue("language", "LanguageNotAllowed");
      }
   }

   private boolean questionnaireTemplateWithGivenNameAlreadyExists(QuestionnaireTemplateNew questionnaireTemplateNew) {
      QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot = questionnaireTemplateSnapshotFinder.findByName(questionnaireTemplateNew.getName());
      return questionnaireTemplateSnapshot != null;
   }

   private boolean languageIsNotAllowed(QuestionnaireTemplateNew questionnaireTemplateNew) {
      return !ValidLanguages.isValid(Locale.forLanguageTag(questionnaireTemplateNew.getLanguage()));
   }

}
