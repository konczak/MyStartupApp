package pl.konczak.mystartupapp.domain.question.bo;

import java.util.Locale;

import pl.konczak.mystartupapp.domain.question.dto.QuestionnaireTemplateSnapshot;

public interface IQuestionnaireTemplateBO {

   QuestionnaireTemplateSnapshot add(String name, Long authorId, Locale language);

   QuestionnaireTemplateSnapshot edit(Long questionnaireTemplateId, Long updatedBy, String name, Locale language);

   void remove(Long questionnaireTemplateId);

}
