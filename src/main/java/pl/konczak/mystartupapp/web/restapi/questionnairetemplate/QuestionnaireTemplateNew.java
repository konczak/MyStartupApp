package pl.konczak.mystartupapp.web.restapi.questionnairetemplate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@ApiModel
public class QuestionnaireTemplateNew {

   @NotNull
   @Size(min = 3, max = 250)
   private String name;
   
   @NotEmpty
   private String language;

   @ApiModelProperty(
         value = "Name of Questionnaire Template", 
         required = true)
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @ApiModelProperty(
         value = "Language of Questionnaire Template", 
         required = true)
   public String getLanguage() {
      return language;
   }
   
   public void setLanguage(String language) {
      this.language = language;
   }
   
}
