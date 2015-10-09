package pl.konczak.mystartupapp.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import pl.konczak.mystartupapp.web.filter.AuthenticatedUserLogFilter;

/**
 * This class is common Spring Security configuration. It allows for non secured access to static resources, login page,
 * REST API (endpoints are secured independent with @PreAuthorize annotation)
 *
 * @author piotr.konczak
 */
final class AuthenticationCommonUtil {

   private AuthenticationCommonUtil() {
   }

   final static void configure(WebSecurity web) {
      web.ignoring()
         .antMatchers(
            "/css/**",
            "/img/**",
            "/js/**",
            "/webjars/**",
            "/fonts/**");
   }

   final static void configure(HttpSecurity http)
      throws Exception {
      http.authorizeRequests()
         .antMatchers("/login").permitAll()
         .antMatchers("/sessionExpired").permitAll()
         .antMatchers("/api/admin/**").hasRole("ADMIN")
         .antMatchers("/api/**").permitAll()
         .anyRequest().authenticated();

      http.formLogin()
         .loginPage("/login")
         .defaultSuccessUrl("/")
         .failureUrl("/login?error");

      http.exceptionHandling()
         .accessDeniedPage("/403error");

      http.logout()
         .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

      http.csrf().disable();

      http.addFilterAfter(new AuthenticatedUserLogFilter(), SecurityContextPersistenceFilter.class);
   }
}
