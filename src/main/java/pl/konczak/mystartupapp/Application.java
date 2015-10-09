package pl.konczak.mystartupapp;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This class is used in Maven DEVELOPMENT profile to run easily Spring boot application. Also it can be used while
 * using JAR packaging.
 *
 * @author piotr.konczak
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class Application {

   private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

   @Autowired
   private Environment env;

   @PostConstruct
   public void initApplication()
      throws IOException {
      if (env.getActiveProfiles().length == 0) {
         LOGGER.warn("No Spring profile configured, running with default configuration");
      } else {
         LOGGER.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
      }
   }

   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

}
