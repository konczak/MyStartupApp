package pl.konczak.mystartupapp.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Mateusz.Glabicki
 */
public class AuthenticatedUserLogFilter
   implements Filter {

   @Override
   public void init(FilterConfig filterConfig)
      throws ServletException {
      //nothing to do in init
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain)
      throws IOException,
             ServletException {

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null) {
         chain.doFilter(request, response);
      } else {
         MDC.put("userName", authentication.getName());
         try {
            chain.doFilter(request, response);
         } finally {
            MDC.remove("userName");
         }
      }
   }

   @Override
   public void destroy() {
      //nothing to clean
   }

}
