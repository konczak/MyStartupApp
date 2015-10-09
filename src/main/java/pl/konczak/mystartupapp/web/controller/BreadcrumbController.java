package pl.konczak.mystartupapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BreadcrumbController {

   @RequestMapping("/fragments/breadcrumb")
   public String breadcrumb(){
      return "fragments/breadcrumb";
   }
   
}
