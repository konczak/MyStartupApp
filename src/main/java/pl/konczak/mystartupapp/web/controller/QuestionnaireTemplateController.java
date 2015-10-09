package pl.konczak.mystartupapp.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questionnaireTemplate")
@PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_MANAGER')")
public class QuestionnaireTemplateController {

   @RequestMapping("/list")
   public String list() {
      return "questionnaireTemplate/list";
   }
   
   @RequestMapping("/add")
   public String add(){
      return "questionnaireTemplate/add";
   }
   
   @RequestMapping("/details")
   public String detail(){
      return "questionnaireTemplate/details";
   }
   
   @RequestMapping("/questionnaireTemplateCopyModal")
   public String copyModal(){
      return "questionnaireTemplate/questionnaireTemplateCopyModal";
   }
   
   @RequestMapping("/questionView")
   public String questionView(){
      return "questionnaireTemplate/questionView";
   }
   
   @RequestMapping("/detachQuestionModal")
   public String detachQuestionModal(){
      return "questionnaireTemplate/detachQuestionModal";
   }
}
