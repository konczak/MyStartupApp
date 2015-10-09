package pl.konczak.mystartupapp.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Mateusz.Glabicki
 */
@Controller
public class MetricController {

   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @RequestMapping("/metric/list")
   public String list() {
      return "metric/list";
   }
}
