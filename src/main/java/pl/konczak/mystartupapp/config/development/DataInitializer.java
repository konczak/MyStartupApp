package pl.konczak.mystartupapp.config.development;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.konczak.mystartupapp.config.development.question.IQuestionnaireTemplateMock;
import pl.konczak.mystartupapp.domain.employee.finder.IEmployeeSnapshotFinder;
import pl.konczak.mystartupapp.domain.question.finder.IQuestionnaireTemplateSnapshotFinder;
import pl.konczak.mystartupapp.sharedkernel.constant.Profiles;

@Component
@Profile(Profiles.DEVELOPMENT)
public class DataInitializer {

   @Autowired
   private IEmployeeInitializer employeeInitializer;

   @Autowired
   private IEmployeeSnapshotFinder employeeSnapshotFinder;

   @Autowired
   private IQuestionnaireTemplateSnapshotFinder questionnaireTemplateSnapshotFinder;

   @Autowired
   private IQuestionnaireTemplateMock questionnaireTemplateMock;

   @Transactional
   @PostConstruct
   public void init() {
      if (employeeSnapshotFinder.findAll().isEmpty()) {
         employeeInitializer.initalizer();
      }

      if (questionnaireTemplateSnapshotFinder.findAll().isEmpty()) {
         questionnaireTemplateMock.mockQuestionaireTemplate();
      }
   }

}
