package pl.konczak.mystartupapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import pl.konczak.mystartupapp.sharedkernel.constant.Profiles;

/**
 * This class is used in WAR packaging to run eSpring boot application.
 *
 * @author piotr.konczak
 */
public class ApplicationWebXml
   extends SpringBootServletInitializer {

   private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationWebXml.class);

   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
      return application.profiles(addDefaultProfile())
         .sources(Application.class);
   }

   private String[] addDefaultProfile() {
      String profile = System.getProperty("spring.profiles.active");
      if (profile != null) {
         LOGGER.info("Running with Spring profile(s) : {}", profile);
         return new String[]{profile};
      }

      LOGGER.warn("No Spring profile configured, running with default configuration");
      return new String[]{Profiles.PRODUCTION};
   }
}
