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
public class QuestionnaireTemplateEditValidator extends AbstractValidator {

   private final IQuestionnaireTemplateSnapshotFinder questionnaireTemplateSnapshotFinder;
   
   @Autowired
   public QuestionnaireTemplateEditValidator(IQuestionnaireTemplateSnapshotFinder questionnaireTemplateSnapshotFinder) {
      this.questionnaireTemplateSnapshotFinder = questionnaireTemplateSnapshotFinder;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return QuestionnaireTemplateEdit.class.isAssignableFrom(clazz);
   }

   @Override
   public void customValidation(Object target, Errors errors) {
      QuestionnaireTemplateEdit questionnaireTemplateEdit = (QuestionnaireTemplateEdit) target;
      QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot = questionnaireTemplateSnapshotFinder
            .findById(questionnaireTemplateEdit.getQuestionnaireTemplateId());
      
      if (questionnaireTemplateSnapshot == null) {
         errors.rejectValue("questionnaireTemplateId", "QuestionnaireTemplateWithSpecifiedIdDoeasntExist");
         return;
      }
      
      if (nameWasChanged(questionnaireTemplateEdit, questionnaireTemplateSnapshot) &&
            questionnaireTemplateWithGivenNameAlreadyExists(questionnaireTemplateEdit)) {
         errors.rejectValue("name", "QuestionnaireTemplateWithGivenNameAlreadyExists");
      }
      
      if(languageIsNotAllowed(questionnaireTemplateEdit)){
         errors.rejectValue("language", "LanguageNotAllowed");
      }
      
   }

   private boolean nameWasChanged(QuestionnaireTemplateEdit questionnaireTemplateEdit,
         QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot) {
      return !questionnaireTemplateSnapshot.getName().equals(questionnaireTemplateEdit.getName());
   }

   private boolean questionnaireTemplateWithGivenNameAlreadyExists(QuestionnaireTemplateEdit questionnaireTemplateEdit) {
      QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot = questionnaireTemplateSnapshotFinder.findByName(questionnaireTemplateEdit.getName());
      return questionnaireTemplateSnapshot != null;
   }

   private boolean languageIsNotAllowed(QuestionnaireTemplateEdit questionnaireTemplateEdit) {
      return !ValidLanguages.isValid(Locale.forLanguageTag(questionnaireTemplateEdit.getLanguage()));
   }

}
