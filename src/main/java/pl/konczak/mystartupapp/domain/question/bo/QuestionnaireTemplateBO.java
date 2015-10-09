package pl.konczak.mystartupapp.domain.question.bo;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.konczak.mystartupapp.domain.question.dto.QuestionnaireTemplateSnapshot;
import pl.konczak.mystartupapp.domain.question.entity.QuestionnaireTemplate;
import pl.konczak.mystartupapp.domain.question.repo.IQuestionnaireTemplateRepository;
import pl.konczak.mystartupapp.sharedkernel.annotations.BussinesObject;

@BussinesObject
public class QuestionnaireTemplateBO
   implements IQuestionnaireTemplateBO {

   private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireTemplateBO.class);
   private final IQuestionnaireTemplateRepository questionnaireTemplateRepository;

   @Autowired
   public QuestionnaireTemplateBO(IQuestionnaireTemplateRepository questionnaireTemplateRepository) {
      this.questionnaireTemplateRepository = questionnaireTemplateRepository;
   }

   @Override
   public QuestionnaireTemplateSnapshot add(String name, Long authorId, Locale language) {
      QuestionnaireTemplate questionnaireTemplate = new QuestionnaireTemplate(name, authorId, language);
      questionnaireTemplate = questionnaireTemplateRepository.save(questionnaireTemplate);

      LOGGER.info("Add Questionnaire Template <{}> <{}> <{}>", name, authorId, language);

      return questionnaireTemplate.toSnapshot();
   }

   @Override
   public QuestionnaireTemplateSnapshot edit(Long questionnaireTemplateId, Long updatedBy, String name, Locale language) {
      QuestionnaireTemplate questionnaireTemplate = questionnaireTemplateRepository.findOne(questionnaireTemplateId);
      questionnaireTemplate.edit(updatedBy, name, language);
      questionnaireTemplate = questionnaireTemplateRepository.save(questionnaireTemplate);

      QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot = questionnaireTemplate.toSnapshot();

      LOGGER.info("Edit Questionnaire Template <{}> <{}>", questionnaireTemplateSnapshot.getId(),
         questionnaireTemplateSnapshot.getName());

      return questionnaireTemplateSnapshot;
   }

   @Override
   public void remove(Long questionnaireTemplateId) {
      questionnaireTemplateRepository.delete(questionnaireTemplateId);

      LOGGER.info("Delete Questionnaire Template <{}>", questionnaireTemplateId);
   }

}
