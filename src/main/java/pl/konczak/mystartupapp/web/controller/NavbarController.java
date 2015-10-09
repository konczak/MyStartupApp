package pl.konczak.mystartupapp.web.controller;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Mateusz.Glabicki
 */
@Controller
public class NavbarController {

   @RequestMapping("/fragments/navbar")
   public ModelAndView navbar(ModelAndView modelAndView) {
      modelAndView.setViewName("fragments/navbar");
      
      Locale locale = LocaleContextHolder.getLocale();
      modelAndView.addObject("locale", locale.toString());
      
      return modelAndView;
   }
   
   @RequestMapping("/sessionExpired")
   public String sessionExpired() {
      return "fragments/sessionExpired";
   }
}
