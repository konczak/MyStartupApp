package pl.konczak.mystartupapp.domain.question.bo;

import java.util.Locale;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import pl.konczak.mystartupapp.Application;
import pl.konczak.mystartupapp.domain.question.dto.QuestionnaireTemplateSnapshot;
import pl.konczak.mystartupapp.domain.question.entity.QuestionnaireTemplate;
import pl.konczak.mystartupapp.domain.question.repo.IQuestionnaireTemplateRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class IQuestionnaireTemplateBOTest {

   private static final String CLAZZ = IQuestionnaireTemplateBOTest.class.getSimpleName();
   private static final Locale POLISH = Locale.forLanguageTag("pl-PL");
   private static final Locale ENGLISH = Locale.forLanguageTag("en-GB");

   @Autowired
   private IQuestionnaireTemplateBO questionnaireTemplateBO;
   @Autowired
   private IQuestionnaireTemplateRepository questionnaireTemplateRepository;
   private QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot;

   @Before
   public void setUp() {
      questionnaireTemplateSnapshot = questionnaireTemplateBO.add(CLAZZ + " setUp", 1l, POLISH);
   }

   @After
   public void tearDown() {
      if (questionnaireTemplateSnapshot != null) {
         questionnaireTemplateBO.remove(questionnaireTemplateSnapshot.getId());
         questionnaireTemplateSnapshot = null;
      }
   }

   @Test
   public void shouldAddQuestionnaire_whenAddInvoked() {
      // given
      String name = CLAZZ;
      Long authorId = 1l;
      Locale language = POLISH;

      // when
      QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot = questionnaireTemplateBO.add(name, authorId, language);

      // then
      assertThat(questionnaireTemplateSnapshot.getName(), is(equalTo(name)));
      assertThat(questionnaireTemplateSnapshot.getAuthorId(), is(equalTo(authorId)));
      assertThat(questionnaireTemplateSnapshot.getLanguage(), is(equalTo(language)));
   }

   @Test
   public void shouldEditQuestionnaire_whenEditInvoked() {
      // given
      String newName = CLAZZ + " " + CLAZZ;
      Locale newLanguage = ENGLISH;
      Long updatedBy = 2L;

      // when
      QuestionnaireTemplateSnapshot editedQuestionnaireTemplateSnapshot = questionnaireTemplateBO.edit(
         questionnaireTemplateSnapshot.getId(), updatedBy, newName, newLanguage);

      // then
      assertThat(editedQuestionnaireTemplateSnapshot.getId(), is(equalTo(questionnaireTemplateSnapshot.getId())));
      assertThat(editedQuestionnaireTemplateSnapshot.getUpdatedBy(), is(equalTo(updatedBy)));
      assertThat(editedQuestionnaireTemplateSnapshot.getName(), is(equalTo(newName)));
      assertThat(editedQuestionnaireTemplateSnapshot.getLanguage(), is(equalTo(newLanguage)));
   }

   @Test
   public void shouldRemoveQuestionnaire_whenRemoveInvoked() {
      // given
      Long questionnaireTemplateId = questionnaireTemplateSnapshot.getId();

      // when
      questionnaireTemplateBO.remove(questionnaireTemplateId);

      // then
      questionnaireTemplateSnapshot = null;
      QuestionnaireTemplate questionnaireTemplate = questionnaireTemplateRepository.findOne(questionnaireTemplateId);
      assertThat(questionnaireTemplate, is(nullValue()));
   }

}
