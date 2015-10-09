package pl.konczak.mystartupapp.web.restapi.authentication;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is used to check that user is authenticated. It is useful in case of expiring session. Example usage could
 * be scheduled call or through interceptor before every call to REST API.
 *
 * @author Mateusz.Glabicki
 */
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationApi {

   @RequestMapping(method = RequestMethod.GET)
   public HttpEntity authentication() {
      if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
         return new ResponseEntity(HttpStatus.UNAUTHORIZED);
      } else {
         return new ResponseEntity(HttpStatus.OK);
      }
   }

}
