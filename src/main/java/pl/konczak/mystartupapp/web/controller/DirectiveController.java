package pl.konczak.mystartupapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fragments/directives")
public class DirectiveController {

   @RequestMapping("/languageFlag")
   public String list(){
      return "fragments/directives/languageFlag";
   }
   @RequestMapping("/performanceLabel")
   public String performanceLabel(){
      return "fragments/directives/performanceLabel";
   }
   @RequestMapping("/potentialLabel")
   public String potentialLabel(){
      return "fragments/directives/potentialLabel";
   }
   
}
