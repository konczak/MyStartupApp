package pl.konczak.mystartupapp.config.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This class should be used only in development. It allows to avoid LDAP authentication in favor of quick in memory
 * authentication.
 *
 * @author piotr.konczak
 */
public class AuthenticationBasic
   extends WebSecurityConfigurerAdapter {

   private static final String PASSWORD = "password";
   private static final String ROLE_HR = "HR";
   private static final String ROLE_ADMIN = "ADMIN";

   @Override
   public void configure(WebSecurity web)
      throws Exception {
      AuthenticationCommonUtil.configure(web);
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
      InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> authManagerConfigurer
         = auth.inMemoryAuthentication();

      addEmployees(authManagerConfigurer);
      addHr(authManagerConfigurer);
      addAdmin(authManagerConfigurer);
   }

   private void addEmployees(InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> authManagerConfigurer) {
      authManagerConfigurer
         .withUser("piko").password(PASSWORD).roles();
   }

   private void addHr(InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> authManagerConfigurer) {
      authManagerConfigurer
         .withUser("hruser").password(PASSWORD).roles(ROLE_HR);
   }

   private void addAdmin(InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> authManagerConfigurer) {
      authManagerConfigurer
         .withUser("admin").password(PASSWORD).roles(ROLE_ADMIN);
   }

   @Override
   protected void configure(HttpSecurity http)
      throws Exception {
      AuthenticationCommonUtil.configure(http);
   }

}
