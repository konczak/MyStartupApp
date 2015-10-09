package pl.konczak.mystartupapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dateTimePicker")
public class DateTimePickerController {

   @RequestMapping("/dateTimePickerModal")
   public String modal() {
      return "dateTimePicker/dateTimePickerModal";
   }

   @RequestMapping("/dateTimePickerCalendar")
   public String calendar() {
      return "dateTimePicker/dateTimePickerCalendar";
   }

}
