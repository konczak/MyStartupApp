package pl.konczak.mystartupapp.domain.question.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.konczak.mystartupapp.domain.question.entity.QuestionnaireTemplate;

public interface IQuestionnaireTemplateRepository
   extends JpaRepository<QuestionnaireTemplate, Long> {

   QuestionnaireTemplate findByNameIgnoreCase(String name);

}
