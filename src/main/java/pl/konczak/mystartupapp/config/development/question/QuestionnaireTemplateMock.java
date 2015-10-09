package pl.konczak.mystartupapp.config.development.question;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.domain.employee.finder.IEmployeeSnapshotFinder;
import pl.konczak.mystartupapp.domain.question.bo.IQuestionnaireTemplateBO;
import pl.konczak.mystartupapp.sharedkernel.constant.Profiles;

@Service
@Profile(Profiles.DEVELOPMENT)
public class QuestionnaireTemplateMock
   implements IQuestionnaireTemplateMock {

   private static final String EMPLOYEE_USERNAME = "piko";

   @Autowired
   private IEmployeeSnapshotFinder employeeSnapshotFinder;

   @Autowired
   private IQuestionnaireTemplateBO questionnaireTemplateBO;

   private EmployeeSnapshot employeeSnapshot;

   @Override
   public void mockQuestionaireTemplate() {
      employeeSnapshot = findEmployee();
      createPolishQuestionnaireTemplate();
      createEnglishQuestionnaireTemplate();
      createOpenQuestionnaireTemplate();
   }

   private void createPolishQuestionnaireTemplate() {
      String name = "Szablon polskiej ankiety";
      Locale language = Locale.forLanguageTag("pl-PL");

      questionnaireTemplateBO.add(
         name,
         employeeSnapshot.getId(),
         language);
   }

   private void createEnglishQuestionnaireTemplate() {
      String name = "English questionaie template";
      Locale language = Locale.forLanguageTag("en-GB");

      questionnaireTemplateBO.add(
         name,
         employeeSnapshot.getId(),
         language);
   }

   private void createOpenQuestionnaireTemplate() {
      String name = "Questionnaire template with only open questions";
      Locale language = Locale.forLanguageTag("en-GB");

      questionnaireTemplateBO.add(
         name,
         employeeSnapshot.getId(),
         language);
   }

   private EmployeeSnapshot findEmployee() {
      return employeeSnapshotFinder.findByUsername(EMPLOYEE_USERNAME);
   }

}
