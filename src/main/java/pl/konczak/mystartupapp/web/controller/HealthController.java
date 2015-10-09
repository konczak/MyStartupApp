package pl.konczak.mystartupapp.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/health")
public class HealthController {

   @RequestMapping("/list")
   public String list(){
      return "/health/list";
   }
   
   @RequestMapping("/stacktraceModal")
   public String stacktraceModal(){
      return "/health/stacktraceModal";
   }
   
}
