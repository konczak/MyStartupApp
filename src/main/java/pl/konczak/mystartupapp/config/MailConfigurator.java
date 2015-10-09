package pl.konczak.mystartupapp.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * This is email configuration. It configures email sender and Thymeleaf render engine.
 *
 * @author piotr.konczak
 */
@Configuration
public class MailConfigurator {

   @Value("${email.userName}")
   private String userName;

   @Value("${email.password}")
   private String password;

   @Value("${email.host}")
   private String host;

   @Value("${email.port}")
   private int port;

   @Value("${email.protocol}")
   private String protocol;

   @Value("${email.auth}")
   private String auth;

   @Value("${email.debug}")
   private String emailDebug;

   @Bean
   public JavaMailSender javaMailSender() {
      JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

      Properties props = new Properties();
      props.put(auth, "true");
      props.setProperty("mail.debug", emailDebug);

      javaMailSender.setJavaMailProperties(props);
      javaMailSender.setUsername(userName + "@cybercom.com");
      javaMailSender.setPassword(password);
      javaMailSender.setHost(host);
      javaMailSender.setPort(port);
      javaMailSender.setProtocol(protocol);

      return javaMailSender;
   }

   @Bean
   public ClassLoaderTemplateResolver emailTemplateResolver() {
      ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
      emailTemplateResolver.setPrefix("mails/");
      emailTemplateResolver.setSuffix(".html");
      emailTemplateResolver.setTemplateMode("HTML5");
      emailTemplateResolver.setCharacterEncoding("UTF-8");
      emailTemplateResolver.setOrder(1);
      emailTemplateResolver.setCacheable(false);

      return emailTemplateResolver;
   }
}
