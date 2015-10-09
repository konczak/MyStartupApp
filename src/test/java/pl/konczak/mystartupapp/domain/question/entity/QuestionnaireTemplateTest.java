package pl.konczak.mystartupapp.domain.question.entity;

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
import pl.konczak.mystartupapp.domain.question.repo.IQuestionnaireTemplateRepository;
import pl.konczak.mystartupapp.sharedkernel.exception.EntityInStateNewException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class QuestionnaireTemplateTest {

   private static final String CLAZZ = QuestionnaireTemplateTest.class.getSimpleName();
   private static final Locale POLISH = Locale.forLanguageTag("pl-PL");
   private static final Locale ENGLISH = Locale.forLanguageTag("en-GB");

   @Autowired
   private IQuestionnaireTemplateRepository questionnaireTemplateRepository;
   private QuestionnaireTemplate questionnaireTemplate;

   @Before
   public void setUp() {
      String name = CLAZZ + " setUp";
      questionnaireTemplate = new QuestionnaireTemplate(name, 1L, POLISH);
      questionnaireTemplateRepository.save(questionnaireTemplate);
   }

   @After
   public void tearDown() {
      questionnaireTemplateRepository.delete(questionnaireTemplate);
      questionnaireTemplate = null;
   }

   @Test
   public void shouldReturnSnapshot_whenToSnapshotInvoked_andEntityIsPersisted() {
      // given
      String name = CLAZZ;
      Long authorId = 1L;
      Locale language = POLISH;
      QuestionnaireTemplate questionnaireTemplate = new QuestionnaireTemplate(name, authorId, language);
      questionnaireTemplateRepository.save(questionnaireTemplate);

      // when
      QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot = questionnaireTemplate.toSnapshot();

      // then
      assertThat(questionnaireTemplateSnapshot.getName(), is(equalTo(name)));
      assertThat(questionnaireTemplateSnapshot.getAuthorId(), is(equalTo(authorId)));
      assertThat(questionnaireTemplateSnapshot.getLanguage(), is(equalTo(language)));
   }

   @Test(expected = EntityInStateNewException.class)
   public void shouldThrowEntityInStateNewException_whenToSnapshotInvoked_andEntityIsNotPersisted() {
      // given
      String name = CLAZZ;
      Long authorId = 1L;
      Locale language = POLISH;
      QuestionnaireTemplate questionnaireTemplate = new QuestionnaireTemplate(name, authorId, language);

      // when
      questionnaireTemplate.toSnapshot();
   }

   @Test
   public void shouldEditQuestionnaire_whenEditInvoked() {
      // given
      String newName = CLAZZ + " " + CLAZZ;
      Locale newLanguage = ENGLISH;
      Long updatedBy = 2L;

      // when
      questionnaireTemplate.edit(updatedBy, newName, newLanguage);

      // then
      QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot = questionnaireTemplate.toSnapshot();
      assertThat(questionnaireTemplateSnapshot.getUpdatedBy(), is(equalTo(updatedBy)));
      assertThat(questionnaireTemplateSnapshot.getName(), is(equalTo(newName)));
      assertThat(questionnaireTemplateSnapshot.getLanguage(), is(equalTo(newLanguage)));
   }

}
