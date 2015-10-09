package pl.konczak.mystartupapp.config.security;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import pl.konczak.mystartupapp.sharedkernel.constant.Profiles;
import pl.konczak.mystartupapp.web.filter.RequestResponseLogFilter;

/**
 * This Spring Security initialization configuration
 *
 * @author piotr.konczak
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,
   prePostEnabled = true)
public class SecurityConfigurer {

   @Bean
   public Filter requestResponseLogFilter() {
      return new RequestResponseLogFilter();
   }

   @Bean
   @Profile(Profiles.BASIC_AUTHENTICATION)
   public AuthenticationBasic authenticationBasic() {
      return new AuthenticationBasic();
   }

}
