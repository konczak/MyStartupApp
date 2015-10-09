package pl.konczak.mystartupapp.domain.question.finder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pl.konczak.mystartupapp.domain.question.dto.QuestionnaireTemplateSnapshot;
import pl.konczak.mystartupapp.domain.question.entity.QuestionnaireTemplate;
import pl.konczak.mystartupapp.domain.question.repo.IQuestionnaireTemplateRepository;
import pl.konczak.mystartupapp.sharedkernel.annotations.Finder;

@Finder
public class QuestionnaireTemplateSnapshotFinder
   implements IQuestionnaireTemplateSnapshotFinder {

   private final IQuestionnaireTemplateRepository questionnaireTemplateRepository;

   @Autowired
   public QuestionnaireTemplateSnapshotFinder(IQuestionnaireTemplateRepository questionnaireTemplateRepository) {
      this.questionnaireTemplateRepository = questionnaireTemplateRepository;
   }

   @Override
   public List<QuestionnaireTemplateSnapshot> findAll() {
      List<QuestionnaireTemplate> questionnaireTemplates = questionnaireTemplateRepository.findAll();
      return toSnapshots(questionnaireTemplates);
   }

   private List<QuestionnaireTemplateSnapshot> toSnapshots(List<QuestionnaireTemplate> questionnaireTemplates) {
      return questionnaireTemplates
         .stream()
         .map(QuestionnaireTemplate::toSnapshot)
         .collect(Collectors.toList());
   }

   @Override
   public QuestionnaireTemplateSnapshot findById(Long questionnaireTemplateId) {
      QuestionnaireTemplate questionnaireTemplate = questionnaireTemplateRepository.findOne(questionnaireTemplateId);

      return questionnaireTemplate == null ? null : questionnaireTemplate.toSnapshot();
   }

   @Override
   public QuestionnaireTemplateSnapshot findByName(String name) {
      QuestionnaireTemplate questionnaireTemplate = questionnaireTemplateRepository.findByNameIgnoreCase(name);

      return questionnaireTemplate == null ? null : questionnaireTemplate.toSnapshot();
   }

   @Override
   public List<QuestionnaireTemplateSnapshot> findAll(Set<Long> ids) {
      List<QuestionnaireTemplate> questionnaireTemplates = questionnaireTemplateRepository.findAll(ids);
      return toSnapshots(questionnaireTemplates);
   }

   @Override
   public Map<Long, QuestionnaireTemplateSnapshot> findAllAsMap(Collection<Long> questionnaireTemplateIds) {
      return questionnaireTemplateRepository
         .findAll(questionnaireTemplateIds)
         .stream()
         .map(QuestionnaireTemplate::toSnapshot)
         .collect(Collectors.toMap(q -> q.getId(), Function.identity()));
   }

}
