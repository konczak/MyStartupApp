package pl.konczak.mystartupapp.config;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * This class listen on Spring context initialization end and add passes configurable properties into Web context so it
 * could be accessed in HTML files.
 *
 * @author piotr.konczak
 */
@Component
public class CustomApplicationListener
   implements ApplicationListener<ContextRefreshedEvent> {

   @Value("${files.download.location}")
   private String filesDownloadLocation;

   @Value("${bugzilla.project.url}")
   private String bugzillaProjectUrl;
   
   @Value("${rpg.photo.link}")
   private String rpgPhotoLink;

   @Override
   public void onApplicationEvent(ContextRefreshedEvent e) {

      ApplicationContext appContext = e.getApplicationContext();
      if (!(appContext instanceof WebApplicationContext)) {
         return;
      }
      WebApplicationContext ctx = (WebApplicationContext) e.getApplicationContext();
      ServletContext context = ctx.getServletContext();

      context.setAttribute("filesDownloadLocation", filesDownloadLocation);
      context.setAttribute("bugzillaProjectUrl", bugzillaProjectUrl);
      context.setAttribute("rpgPhotoLink", rpgPhotoLink);
   }
}
