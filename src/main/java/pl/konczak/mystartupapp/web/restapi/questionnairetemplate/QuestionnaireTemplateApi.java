package pl.konczak.mystartupapp.web.restapi.questionnairetemplate;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.domain.employee.finder.IEmployeeSnapshotFinder;
import pl.konczak.mystartupapp.domain.question.bo.IQuestionnaireTemplateBO;
import pl.konczak.mystartupapp.domain.question.dto.QuestionnaireTemplateSnapshot;
import pl.konczak.mystartupapp.domain.question.finder.IQuestionnaireTemplateSnapshotFinder;
import pl.konczak.mystartupapp.service.employee.LoggedEmployeeService;

@RestController
@RequestMapping("/api/questionnaireTemplate")
public class QuestionnaireTemplateApi {

   private final IQuestionnaireTemplateSnapshotFinder questionnaireTemplateSnapshotFinder;
   private final IQuestionnaireTemplateBO questionnaireTemplateBO;
   private final IEmployeeSnapshotFinder employeeSnapshotFinder;
   private final Validator questionnaireTemplateNewValidator;
   private final Validator questionnaireTemplateEditValidator;

   @Autowired
   public QuestionnaireTemplateApi(IQuestionnaireTemplateSnapshotFinder questionnaireSnapshotFinder,
      IQuestionnaireTemplateBO questionnaireTemplateBO, IEmployeeSnapshotFinder employeeSnapshotFinder,
      @Qualifier("questionnaireTemplateNewValidator") Validator questionnaireTemplateNewValidator,
      @Qualifier("questionnaireTemplateEditValidator") Validator questionnaireTemplateEditValidator) {
      this.questionnaireTemplateSnapshotFinder = questionnaireSnapshotFinder;
      this.questionnaireTemplateBO = questionnaireTemplateBO;
      this.employeeSnapshotFinder = employeeSnapshotFinder;
      this.questionnaireTemplateNewValidator = questionnaireTemplateNewValidator;
      this.questionnaireTemplateEditValidator = questionnaireTemplateEditValidator;
   }

   @InitBinder("questionnaireTemplateNew")
   public void initNewBinder(WebDataBinder binder) {
      binder.setValidator(questionnaireTemplateNewValidator);
   }

   @InitBinder("questionnaireTemplateEdit")
   public void initEditBinder(WebDataBinder binder) {
      binder.setValidator(questionnaireTemplateEditValidator);
   }

   @ApiOperation(
      value = "Get list of all Questionnaire Templates [HR, Manager]",
      notes = "Returns all Questionnaire Templates")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Found list of all Questionnaire Templates")
   })
   @JsonView(View.List.class)
   @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_MANAGER')")
   @RequestMapping(method = RequestMethod.GET)
   public HttpEntity<List<QuestionnaireTemplate>> list() {
      List<QuestionnaireTemplate> questionnaireTemplates = fetchAllQuestionnaireTemplates();
      return new ResponseEntity<>(questionnaireTemplates, HttpStatus.OK);
   }

   private List<QuestionnaireTemplate> fetchAllQuestionnaireTemplates() {
      List<QuestionnaireTemplateSnapshot> questionnaireTemplateSnapshots = questionnaireTemplateSnapshotFinder.findAll();
      Set<Long> authorIds = fetchAuthorIdsFrom(questionnaireTemplateSnapshots);
      Map<Long, EmployeeSnapshot> employeeSnapshots = employeeSnapshotFinder.findAllAsMap(authorIds);

      List<QuestionnaireTemplate> questionnaireTemplates = questionnaireTemplateSnapshots
         .stream()
         .map(s -> {
            return new QuestionnaireTemplate(s, employeeSnapshots.get(s.getAuthorId()));
         }).collect(Collectors.toList());

      return questionnaireTemplates;
   }

   private Set<Long> fetchAuthorIdsFrom(List<QuestionnaireTemplateSnapshot> questionnaireTemplateSnapshots) {
      return questionnaireTemplateSnapshots
         .stream()
         .map(s -> s.getAuthorId())
         .collect(Collectors.toSet());
   }

   @ApiOperation(
      value = "Get Questionnaire Template by Id",
      notes = "Returns Questionnaire Template with specified Id")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Found Questionnaire Template with specified Id"),
      @ApiResponse(code = 404,
         message = "Questionnaire Template not found")
   })
   @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_MANAGER')")
   @RequestMapping(value = "/{id}",
      method = RequestMethod.GET)
   public HttpEntity<QuestionnaireTemplate> get(@PathVariable("id") Long questionnaireTemplateId) {
      QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot = questionnaireTemplateSnapshotFinder.findById(
         questionnaireTemplateId);

      if (questionnaireTemplateSnapshot == null) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } else {
         EmployeeSnapshot employeeSnapshot = employeeSnapshotFinder.findById(questionnaireTemplateSnapshot.getAuthorId());
         QuestionnaireTemplate questionnaireTemplate = new QuestionnaireTemplate(questionnaireTemplateSnapshot,
            employeeSnapshot);

         return new ResponseEntity<>(questionnaireTemplate, HttpStatus.OK);
      }
   }

   @ApiOperation(
      value = "Add new Questionnaire Template [HR, Manager]",
      notes = "Returns created Questionnaire Template")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "New Questionnaire Template created"),
      @ApiResponse(code = 400,
         message = "Invalid input")})
   @JsonView(View.Summary.class)
   @PreAuthorize("hasAnyRole('ROLE_HR','ROLE_MANAGER')")
   @RequestMapping(method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
   public HttpEntity<QuestionnaireTemplate> add(@Valid @RequestBody QuestionnaireTemplateNew newQuestionnaireTemplate) {
      EmployeeSnapshot author = LoggedEmployeeService.getSnapshot();

      QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot = questionnaireTemplateBO.add(
         newQuestionnaireTemplate.getName(), author.getId(),
         Locale.forLanguageTag(newQuestionnaireTemplate.getLanguage()));

      return new ResponseEntity<>(new QuestionnaireTemplate(questionnaireTemplateSnapshot), HttpStatus.OK);
   }

   @ApiOperation(
      value = "Update existing Questionnaire Template [HR, Manager]",
      notes = "Returns updated Questionnaire Template")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Questionnaire Template updated"),
      @ApiResponse(code = 400,
         message = "Invalid input")})
   @PreAuthorize("hasAnyRole('ROLE_HR','ROLE_MANAGER')")
   @RequestMapping(method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_VALUE)
   public HttpEntity<QuestionnaireTemplate> update(
      @Valid @RequestBody QuestionnaireTemplateEdit updatedQuestionnaireTemplate) {
      Long editorId = LoggedEmployeeService.getSnapshot().getId();

      QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot = questionnaireTemplateBO.edit(
         updatedQuestionnaireTemplate.getQuestionnaireTemplateId(), editorId,
         updatedQuestionnaireTemplate.getName(), Locale.forLanguageTag(updatedQuestionnaireTemplate.getLanguage()));

      return new ResponseEntity<>(new QuestionnaireTemplate(questionnaireTemplateSnapshot), HttpStatus.OK);
   }

   @ApiOperation(
      value = "Delete Questionnaire Template [HR, Manager]",
      notes = "Returns empty body")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Questionnaire Template deleted")})
   @PreAuthorize("hasAnyRole('ROLE_HR','ROLE_MANAGER')")
   @RequestMapping(value = "/{id}",
      method = RequestMethod.DELETE)
   public HttpEntity<QuestionnaireTemplate> delete(@PathVariable("id") Long questionnaireTemplateId) {
      QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot = questionnaireTemplateSnapshotFinder.findById(
         questionnaireTemplateId);
      if (questionnaireTemplateSnapshot != null) {
         questionnaireTemplateBO.remove(questionnaireTemplateId);
      }

      return new ResponseEntity<>(HttpStatus.OK);
   }

}
