package pl.konczak.mystartupapp.config.health;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MailHealthIndicator extends AbstractHealthIndicator {

   private JavaMailSender javaMailSender;

   @Autowired
   public MailHealthIndicator(JavaMailSender javaMailSender) {
      this.javaMailSender = javaMailSender;
   }
   
   private String stackTraceToStringFrom(Exception exception) {
      StringWriter stringWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(stringWriter);
      exception.printStackTrace(printWriter);
      
      return stringWriter.toString();
   }
   
   @Override
   protected void doHealthCheck(Builder builder) {
      try {
         ((JavaMailSenderImpl)javaMailSender).testConnection();
         
         builder.up();
      }catch(MessagingException exception){
         builder
            .down()
            .withDetail("error", stackTraceToStringFrom(exception));
      }
   }

}
