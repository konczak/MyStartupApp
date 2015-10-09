package pl.konczak.mystartupapp.web.restapi.questionnairetemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.konczak.mystartupapp.Application;
import pl.konczak.mystartupapp.domain.employee.bo.IEmployeeBO;
import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.domain.question.bo.IQuestionnaireTemplateBO;
import pl.konczak.mystartupapp.domain.question.dto.QuestionnaireTemplateSnapshot;
import pl.konczak.mystartupapp.domain.question.finder.IQuestionnaireTemplateSnapshotFinder;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.thymeleaf.util.StringUtils.repeat;
import static pl.konczak.mystartupapp.util.IsISODateTime.isISODateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class QuestionnaireTemplateApiTest {

   private static final int TEMPLATE_NAME_MAX_LENGTH = 250;
   private static final String MOCKED_USERNAME = "questionnaireTemplate.api";
   private static final String CLAZZ = QuestionnaireTemplateApiTest.class.getSimpleName();
   private static final Locale POLISH = Locale.forLanguageTag("pl-PL");
   private static final Locale ENGLISH = Locale.forLanguageTag("en-GB");

   @Autowired
   private IQuestionnaireTemplateSnapshotFinder questionnaireSnapshotFinder;
   @Autowired
   private IQuestionnaireTemplateBO questionnairetemplateBO;
   @Autowired
   private IEmployeeBO employeeBO;
   @Autowired
   private WebApplicationContext context;

   private EmployeeSnapshot employeeSnapshot;
   private QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot;
   private MockMvc mockMvc;

   @Before
   public void setUp() {
      mockMvc();

      if (employeeSnapshot == null) {
         employeeSnapshot = createEmployee();
      }

      addQuestionnaireTemplate();
   }

   private EmployeeSnapshot createEmployee() {
      return employeeBO.add(UUID.randomUUID(), CLAZZ, CLAZZ, CLAZZ, MOCKED_USERNAME, LocalDateTime.now(),
         CLAZZ + "@traplan.com");
   }

   private void addQuestionnaireTemplate() {
      String name = CLAZZ + " setUp";
      Long employeeId = employeeSnapshot.getId();
      questionnaireTemplateSnapshot = questionnairetemplateBO.add(name, employeeId, POLISH);
   }

   private void mockMvc() {
      this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
         .apply(springSecurity())
         .build();
   }

   @After
   public void tearDown() {
      QuestionnaireTemplateSnapshot existingQuestionnaireTemplateSnapshot = questionnaireSnapshotFinder
         .findById(questionnaireTemplateSnapshot.getId());

      if (existingQuestionnaireTemplateSnapshot != null) {
         questionnairetemplateBO.remove(questionnaireTemplateSnapshot.getId());
         questionnaireTemplateSnapshot = null;
      }
   }

   /* LIST */
   private MockHttpServletRequestBuilder aRequestToList() {
      MockHttpServletRequestBuilder request = get("/api/questionnaireTemplate")
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
      return request;
   }

   @SuppressWarnings("unchecked")
   private void shouldReturnListOfAllQuestionnaireTemplates()
      throws Exception {
      // given
      List<QuestionnaireTemplateSnapshot> questionnaireTemplateSnapshots = questionnaireSnapshotFinder.findAll();
      MockHttpServletRequestBuilder request = aRequestToList();

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isOk())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$").isArray())
         .andExpect(jsonPath("$", hasSize(equalTo(questionnaireTemplateSnapshots.size()))))
         .andExpect(jsonPath("$[*].questionnaireTemplateId", hasItems(isA(Integer.class))))
         .andExpect(jsonPath("$[*].authorId", hasItems(isA(Integer.class))))
         .andExpect(jsonPath("$[*].updatedBy", hasItems(isA(Integer.class))))
         .andExpect(jsonPath("$[*].createdAt", hasItems(isISODateTime())))
         .andExpect(jsonPath("$[*].updatedAt", hasItems(isISODateTime())))
         .andExpect(jsonPath("$[*].name", hasItems(isA(String.class))))
         .andExpect(jsonPath("$[*].language", hasItems(isA(String.class))))
         .andExpect(jsonPath("$[*].questions").doesNotExist())
         .andExpect(jsonPath("$[*].employee").exists());
   }

   @Test
   @WithMockUser(roles = "HR")
   public void shouldReturnListOfAllQuestionnaireTemplates_whenListInvokedByUserWithHRRole()
      throws Exception {
      shouldReturnListOfAllQuestionnaireTemplates();
   }

   @Test
   @WithMockUser(roles = "MANAGER")
   public void shouldReturnListOfAllQuestionnaireTemplates_whenListInvokedByUserWithManagerRole()
      throws Exception {
      shouldReturnListOfAllQuestionnaireTemplates();
   }

   @Test
   @WithMockUser()
   public void shouldReturnForbidden_whenListInvokedByUserWithoutHROrManagerRole()
      throws Exception {
      // given
      MockHttpServletRequestBuilder request = aRequestToList();

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isForbidden())
         .andExpect(content().string(isEmptyOrNullString()));
   }

   /* GET */
   private MockHttpServletRequestBuilder aRequestToGet() {
      MockHttpServletRequestBuilder request = get("/api/questionnaireTemplate/{id}",
         questionnaireTemplateSnapshot.getId())
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
      return request;
   }

   private MockHttpServletRequestBuilder aRequestToGetWithInvalidId() {
      int invalidId = 12312312;
      MockHttpServletRequestBuilder request = get("/api/questionnaireTemplate/{id}", invalidId)
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
      return request;
   }

   @SuppressWarnings("unchecked")
   private void shouldReturnQuestionnaireTemplateWithSpecifiedId()
      throws Exception {
      // given
      MockHttpServletRequestBuilder request = aRequestToGet();

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isOk())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.questionnaireTemplateId").value(is(questionnaireTemplateSnapshot.getId().intValue())))
         .andExpect(jsonPath("$.authorId").value(is(questionnaireTemplateSnapshot.getAuthorId().intValue())))
         .andExpect(jsonPath("$.updatedBy").value(is(questionnaireTemplateSnapshot.getUpdatedBy().intValue())))
         .andExpect(jsonPath("$.createdAt").value(isISODateTime()))
         .andExpect(jsonPath("$.updatedAt").value(isISODateTime()))
         .andExpect(jsonPath("$.language").value(
               is(equalTo(questionnaireTemplateSnapshot.getLanguage().toLanguageTag()))))
         .andExpect(jsonPath("$.name").value(is(questionnaireTemplateSnapshot.getName())))
         .andExpect(jsonPath("$.questions").exists())
         .andExpect(jsonPath("$.questions").isArray())
         .andExpect(jsonPath("$.questions[*].questionId", hasItems(isA(Integer.class))))
         .andExpect(jsonPath("$.questions[*].authorId", hasItems(isA(Integer.class))))
         .andExpect(jsonPath("$.questions[*].createdAt", hasItems(isISODateTime())))
         .andExpect(jsonPath("$.questions[*].updatedAt", hasItems(isISODateTime())))
         .andExpect(jsonPath("$.questions[*].language", hasItems(isA(String.class))))
         .andExpect(jsonPath("$.employee").exists())
         .andExpect(jsonPath("$.employee.employeeId").value(is(equalTo(employeeSnapshot.getId().intValue()))))
         .andExpect(jsonPath("$.employee.firstname").value(is(equalTo(employeeSnapshot.getFirstname()))))
         .andExpect(jsonPath("$.employee.lastname").value(is(equalTo(employeeSnapshot.getLastname()))))
         .andExpect(jsonPath("$.employee.mail").value(is(equalTo(employeeSnapshot.getMail()))));
   }

   @Test
   @WithMockUser(roles = "HR")
   public void shouldReturnQuestionnaireTemplateWithSpecifiedId_whenGetInvokedByUserWithHRRole()
      throws Exception {
      shouldReturnQuestionnaireTemplateWithSpecifiedId();
   }

   @Test
   @WithMockUser(roles = "MANAGER")
   public void shouldReturnQuestionnaireTemplateWithSpecifiedId_whenGetInvokedByUserWithManagerRole()
      throws Exception {
      shouldReturnQuestionnaireTemplateWithSpecifiedId();
   }

   @Test
   @WithMockUser()
   public void shouldReturnForbidden_whenGetInvokedByUserWithoutHROrManagerRole()
      throws Exception {
      MockHttpServletRequestBuilder request = aRequestToGet();

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isForbidden())
         .andExpect(content().string(isEmptyOrNullString()));
   }

   @Test
   @WithMockUser(roles = "HR")
   public void shouldReturnNotFound_whenGetInvokedWithInvalidId()
      throws Exception {
      // given
      MockHttpServletRequestBuilder request = aRequestToGetWithInvalidId();

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isNotFound())
         .andExpect(content().string(isEmptyOrNullString()));
   }

   /* CREATE */
   private MockHttpServletRequestBuilder aRequestToCreateWithValues(String name, String language)
      throws JsonProcessingException {
      QuestionnaireTemplateNew questionnaireTemplateNew = new QuestionnaireTemplateNew();
      questionnaireTemplateNew.setName(name);
      questionnaireTemplateNew.setLanguage(language);
      String body = new ObjectMapper().writeValueAsString(questionnaireTemplateNew);

      MockHttpServletRequestBuilder request = post("/api/questionnaireTemplate")
         .contentType(MediaType.APPLICATION_JSON)
         .content(body)
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
      return request;
   }

   private void shouldCreateQuestionnaireTemplate()
      throws JsonProcessingException,
             Exception {
      // given
      String name = CLAZZ + new Random().nextInt();
      String language = POLISH.toLanguageTag();
      MockHttpServletRequestBuilder request = aRequestToCreateWithValues(name, language);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isOk())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.createdAt").value(isISODateTime()))
         .andExpect(jsonPath("$.language").value(is(equalTo(language))))
         .andExpect(jsonPath("$.employee").doesNotExist());
   }

   @Test
   @WithMockUser(username = MOCKED_USERNAME,
      roles = "HR")
   public void shouldCreateQuestionnaireTemplate_whenCreateInvokedByUserwithHRRole()
      throws Exception {
      shouldCreateQuestionnaireTemplate();
   }

   @Test
   @WithMockUser(username = MOCKED_USERNAME,
      roles = "MANAGER")
   public void shouldCreateQuestionnaireTemplate_whenCreateInvokedByUserwithManagerRole()
      throws Exception {
      shouldCreateQuestionnaireTemplate();
   }

   @Test
   @WithMockUser
   public void shouldReturnForbidden_whenCreateInvokedByUserWithoutHROrManagerRole()
      throws Exception {
      // given
      String name = CLAZZ;
      String language = POLISH.toLanguageTag();
      MockHttpServletRequestBuilder request = aRequestToCreateWithValues(name, language);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isForbidden())
         .andExpect(content().string(isEmptyOrNullString()));
   }

   @Test
   @WithMockUser(username = MOCKED_USERNAME,
      roles = "HR")
   public void shouldReturnBadRequest_whenCreateInvokedWithTooShortName()
      throws Exception {
      // given
      String tooShortName = "a";
      String language = POLISH.toLanguageTag();
      MockHttpServletRequestBuilder request = aRequestToCreateWithValues(tooShortName, language);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.fieldErrors[0].field").value(is("name")))
         .andExpect(jsonPath("$.fieldErrors[0].message").value(is("Size.questionnaireTemplateNew.name")));;
   }

   @Test
   @WithMockUser(username = MOCKED_USERNAME,
      roles = "HR")
   public void shouldReturnBadRequest_whenCreateInvokedWithDuplicatedName()
      throws Exception {
      // given
      String duplicatedName = questionnaireTemplateSnapshot.getName();
      String language = POLISH.toLanguageTag();
      MockHttpServletRequestBuilder request = aRequestToCreateWithValues(duplicatedName, language);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.fieldErrors[0].field").value(is("name")))
         .andExpect(jsonPath("$.fieldErrors[0].message").value(is(
                  "QuestionnaireTemplateWithGivenNameAlreadyExists.questionnaireTemplateNew.name")));;
   }

   @Test
   @WithMockUser(username = MOCKED_USERNAME,
      roles = "HR")
   public void shouldReturnBadRequest_whenCreateInvokedWithTooLongName()
      throws Exception {
      // given
      String tooLongName = repeat("a", TEMPLATE_NAME_MAX_LENGTH + 1);
      String language = POLISH.toLanguageTag();
      MockHttpServletRequestBuilder request = aRequestToCreateWithValues(tooLongName, language);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.fieldErrors[0].field").value(is("name")))
         .andExpect(jsonPath("$.fieldErrors[0].message").value(is("Size.questionnaireTemplateNew.name")));;
   }

   @Test
   @WithMockUser(username = MOCKED_USERNAME,
      roles = "HR")
   public void shouldReturnBadRequest_whenCreateInvokedWithInvalidLanguage()
      throws Exception {
      // given
      String name = CLAZZ;
      String invalidLanguage = "de-asd";
      MockHttpServletRequestBuilder request = aRequestToCreateWithValues(name, invalidLanguage);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.fieldErrors[0].field").value(is("language")))
         .andExpect(jsonPath("$.fieldErrors[0].message").value(
               is("LanguageNotAllowed.questionnaireTemplateNew.language")));;
   }

   /* UPDATE */
   private MockHttpServletRequestBuilder aRequestToUpdateWithValues(Long questionnaireTemplateId, String newName,
      String newLanguage)
      throws JsonProcessingException {
      QuestionnaireTemplateEdit questionnaireTemplateEdit = new QuestionnaireTemplateEdit();
      questionnaireTemplateEdit.setQuestionnaireTemplateId(questionnaireTemplateId);
      questionnaireTemplateEdit.setName(newName);
      questionnaireTemplateEdit.setLanguage(newLanguage);
      String body = new ObjectMapper().writeValueAsString(questionnaireTemplateEdit);

      MockHttpServletRequestBuilder request = put("/api/questionnaireTemplate").contentType(MediaType.APPLICATION_JSON)
         .content(body).accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
      return request;
   }

   private void shouldUpdateQuestionnaireTemplate()
      throws Exception {
      // given
      Long questionnaireTemplateId = questionnaireTemplateSnapshot.getId();
      String newName = questionnaireTemplateSnapshot.getName() + " edited";
      String newLanguage = ENGLISH.toLanguageTag();
      MockHttpServletRequestBuilder request = aRequestToUpdateWithValues(questionnaireTemplateId, newName, newLanguage);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isOk())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.questionnaireTemplateId").value(is(questionnaireTemplateId.intValue())))
         .andExpect(jsonPath("$.name").value(is(equalTo(newName))))
         .andExpect(jsonPath("$.language").value(is(equalTo(newLanguage))))
         .andExpect(jsonPath("$.employee").doesNotExist());
   }

   @Test
   @WithMockUser(username = MOCKED_USERNAME,
      roles = "HR")
   public void shouldUpdateQuestionnaireTemplate_whenUpdateInvokedByUserWithHRRole()
      throws Exception {
      shouldUpdateQuestionnaireTemplate();
   }

   @Test
   @WithMockUser(username = MOCKED_USERNAME,
      roles = "MANAGER")
   public void shouldUpdateQuestionnaireTemplate_whenUpdateInvokedByUserWithManagerRole()
      throws Exception {
      shouldUpdateQuestionnaireTemplate();
   }

   @Test
   @WithMockUser
   public void shouldReturnForbidden_whenUpdateInvokedByUserWithoutHROrManagerRole()
      throws Exception {
      // given
      Long questionnaireTemplateId = questionnaireTemplateSnapshot.getId();
      String newName = CLAZZ + " edited";
      String newLanguage = ENGLISH.toLanguageTag();
      MockHttpServletRequestBuilder request = aRequestToUpdateWithValues(questionnaireTemplateId, newName, newLanguage);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isForbidden())
         .andExpect(content().string(isEmptyOrNullString()));
   }

   @Test
   @WithMockUser(username = MOCKED_USERNAME,
      roles = "HR")
   public void shouldReturnBadRequest_whenUpdateInvokedWithDuplicatedName()
      throws Exception {
      // given
      String duplicatedName = "duplicated name";
      Long employeeId = employeeSnapshot.getId();
      questionnairetemplateBO.add(duplicatedName, employeeId, POLISH);

      Long questionnaireTemplateId = questionnaireTemplateSnapshot.getId();
      String language = questionnaireTemplateSnapshot.getLanguage().toLanguageTag();
      MockHttpServletRequestBuilder request = aRequestToUpdateWithValues(questionnaireTemplateId, duplicatedName,
         language);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.fieldErrors[0].field").value(is("name")))
         .andExpect(jsonPath("$.fieldErrors[0].message").value(is(
                  "QuestionnaireTemplateWithGivenNameAlreadyExists.questionnaireTemplateEdit.name")));;
   }

   @Test
   @WithMockUser(username = MOCKED_USERNAME,
      roles = "HR")
   public void shouldReturnBadRequest_whenUpdateInvokedWithTooShortName()
      throws Exception {
      // given
      Long questionnaireTemplateId = questionnaireTemplateSnapshot.getId();
      String newName = "a";
      String language = questionnaireTemplateSnapshot.getLanguage().toLanguageTag();
      MockHttpServletRequestBuilder request = aRequestToUpdateWithValues(questionnaireTemplateId, newName, language);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.fieldErrors[0].field").value(is("name")))
         .andExpect(jsonPath("$.fieldErrors[0].message").value(is("Size.questionnaireTemplateEdit.name")));;
   }

   @WithMockUser(username = MOCKED_USERNAME,
      roles = "HR")
   public void shouldReturnBadRequest_whenUpdateInvokedWithTooLongName()
      throws Exception {
      // given
      Long questionnaireTemplateId = questionnaireTemplateSnapshot.getId();
      String newName = repeat("a", TEMPLATE_NAME_MAX_LENGTH + 1);
      String language = questionnaireTemplateSnapshot.getLanguage().toLanguageTag();
      MockHttpServletRequestBuilder request = aRequestToUpdateWithValues(questionnaireTemplateId, newName, language);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.fieldErrors[0].field").value(is("name")))
         .andExpect(jsonPath("$.fieldErrors[0].message").value(is("Size.questionnaireTemplateEdit.name")));;
   }

   @Test
   @WithMockUser(username = MOCKED_USERNAME,
      roles = "HR")
   public void shouldReturnBadRequest_whenUpdateInvokedWithInvalidLanguage()
      throws Exception {
      // given
      Long questionnaireTemplateId = questionnaireTemplateSnapshot.getId();
      String name = CLAZZ + new Random().nextInt();
      String newLanguage = "de-DE";
      MockHttpServletRequestBuilder request = aRequestToUpdateWithValues(questionnaireTemplateId, name, newLanguage);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.fieldErrors[0].field").value(is("language")))
         .andExpect(jsonPath("$.fieldErrors[0].message").value(is(
                  "LanguageNotAllowed.questionnaireTemplateEdit.language")));
   }

   @Test
   @WithMockUser(username = MOCKED_USERNAME,
      roles = "HR")
   public void shouldReturnBadRequest_whenUpdateInvokedWithInvalidId()
      throws Exception {
      // given
      Long invalidId = 123123L;
      String name = CLAZZ + new Random().nextInt();
      String language = questionnaireTemplateSnapshot.getLanguage().toLanguageTag();
      MockHttpServletRequestBuilder request = aRequestToUpdateWithValues(invalidId, name, language);

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.fieldErrors[0].field").value(is("questionnaireTemplateId")))
         .andExpect(jsonPath("$.fieldErrors[0].message").value(is(
                  "QuestionnaireTemplateWithSpecifiedIdDoeasntExist.questionnaireTemplateEdit.questionnaireTemplateId")));
   }

   /* DELETE */
   private MockHttpServletRequestBuilder aRequestToDelete() {
      Long questionnaireTemplateId = questionnaireTemplateSnapshot.getId();
      MockHttpServletRequestBuilder request = delete("/api/questionnaireTemplate/{id}", questionnaireTemplateId)
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
      return request;
   }

   private MockHttpServletRequestBuilder aRequestToDeleteWithInvalidId() {
      Long invalidId = 123123123L;
      MockHttpServletRequestBuilder request = delete("/api/questionnaireTemplate/{id}", invalidId)
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
      return request;
   }

   private void shouldDeleteQuestionnaireTemplate()
      throws Exception {
      // given
      MockHttpServletRequestBuilder request = aRequestToDelete();

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isOk())
         .andExpect(content().string(isEmptyOrNullString()));
   }

   @Test
   @WithMockUser(roles = "HR")
   public void shouldDeleteQuestionnaireTemplate_whenDeleteInvokedByUserWithHRRole()
      throws Exception {
      shouldDeleteQuestionnaireTemplate();
   }

   @Test
   @WithMockUser(roles = "MANAGER")
   public void shouldDeleteQuestionnaireTemplate_whenDeleteInvokedByUserWithManagerRole()
      throws Exception {
      shouldDeleteQuestionnaireTemplate();
   }

   @Test
   @WithMockUser
   public void shouldReturnForbidden_whenDeleteInvokedByUserWithoutHROrManagerRole()
      throws Exception {
      // given
      MockHttpServletRequestBuilder request = aRequestToDelete();

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isForbidden())
         .andExpect(content().string(isEmptyOrNullString()));
   }

   @Test
   @WithMockUser(roles = "HR")
   public void shouldReturnOK_whenDeleteInvokedWithInvalidId()
      throws Exception {
      // given
      MockHttpServletRequestBuilder request = aRequestToDeleteWithInvalidId();

      // when
      ResultActions result = mockMvc.perform(request);

      // then
      result
         .andExpect(status().isOk())
         .andExpect(content().string(isEmptyOrNullString()));
   }

}
