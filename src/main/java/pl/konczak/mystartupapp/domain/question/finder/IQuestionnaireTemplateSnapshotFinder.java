package pl.konczak.mystartupapp.domain.question.finder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.konczak.mystartupapp.domain.question.dto.QuestionnaireTemplateSnapshot;

public interface IQuestionnaireTemplateSnapshotFinder {

   List<QuestionnaireTemplateSnapshot> findAll();

   List<QuestionnaireTemplateSnapshot> findAll(Set<Long> ids);

   QuestionnaireTemplateSnapshot findById(Long questionnaireTemplateId);

   QuestionnaireTemplateSnapshot findByName(String name);

   Map<Long, QuestionnaireTemplateSnapshot> findAllAsMap(Collection<Long> questionnaireTemplateIds);

}
