package pl.konczak.mystartupapp.web.restapi.questionnairetemplate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.hateoas.ResourceSupport;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.domain.question.dto.QuestionnaireTemplateSnapshot;

import com.fasterxml.jackson.annotation.JsonView;

@ApiModel
public class QuestionnaireTemplate
   extends ResourceSupport {

   @JsonView(View.Summary.class)
   private final Long questionnaireTemplateId;
   @JsonView(View.Summary.class)
   private final String name;
   @JsonView(View.Summary.class)
   private final Long authorId;
   @JsonView(View.Summary.class)
   private final Long updatedBy;
   @JsonView(View.Summary.class)
   private final LocalDateTime createdAt;
   @JsonView(View.Summary.class)
   private final LocalDateTime updatedAt;
   @JsonView(View.Summary.class)
   private final Locale language;
   @JsonView(View.List.class)
   private Employee employee;

   public QuestionnaireTemplate(QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot) {
      this.questionnaireTemplateId = questionnaireTemplateSnapshot.getId();
      this.name = questionnaireTemplateSnapshot.getName();
      this.authorId = questionnaireTemplateSnapshot.getAuthorId();
      this.updatedBy = questionnaireTemplateSnapshot.getUpdatedBy();
      this.createdAt = questionnaireTemplateSnapshot.getCreatedAt();
      this.updatedAt = questionnaireTemplateSnapshot.getUpdatedAt();
      this.language = questionnaireTemplateSnapshot.getLanguage();
   }

   public QuestionnaireTemplate(QuestionnaireTemplateSnapshot questionnaireTemplateSnapshot,
      EmployeeSnapshot employeeSnapshot) {
      this(questionnaireTemplateSnapshot);
      this.employee = new Employee(employeeSnapshot);
   }

   @ApiModelProperty(
      value = "Unique identifier of Questionnaire Template",
      required = true)
   public Long getQuestionnaireTemplateId() {
      return questionnaireTemplateId;
   }

   @ApiModelProperty(
      value = "Name of Questionnaire Template",
      required = true)
   public String getName() {
      return name;
   }

   @ApiModelProperty(
      value = "Unique identifier of Employee who created Questionnaire Template",
      required = true)
   public Long getAuthorId() {
      return authorId;
   }

   @ApiModelProperty(
      value = "Unique identifier of Employee who last updated Questionnaire Template",
      required = true)
   public Long getUpdatedBy() {
      return updatedBy;
   }

   @ApiModelProperty(
      value = "Date of Questionnaire Template creation",
      required = true)
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   @ApiModelProperty(
      value = "Date of Questionnaire Template last update",
      required = true)
   public LocalDateTime getUpdatedAt() {
      return updatedAt;
   }

   @ApiModelProperty(
      value = "Language of Questionnaire Template",
      required = true)
   public Locale getLanguage() {
      return language;
   }

   @ApiModelProperty(
      value = "Employee who created or edited Questionnaire Template",
      required = true)
   public Employee getEmployee() {
      return employee;
   }

}
