package pl.konczak.mystartupapp.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/documentation")
@PreAuthorize("isAuthenticated()")
public class DocumentationController {

   @RequestMapping("/api")
   public String api() {
      return "documentation/api";
   }

}
