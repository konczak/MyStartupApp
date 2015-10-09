package pl.konczak.mystartupapp.web.filter;

import java.util.HashSet;
import java.util.Set;

/**
 * This class can be used to define REST endpoint which contains in request or response password which should not be
 * logged.
 *
 * @author Piotr.Konczak
 */
class PasswordInEndpointExpectationVerifier {

   private final Set<String> endpointsWithPassword = new HashSet<>();

   PasswordInEndpointExpectationVerifier(String endpoints) {
      if (endpoints != null) {
         String[] splitted = endpoints.split("\n");
         for (String string : splitted) {
            string = string.trim();
            if (!string.isEmpty()) {
               endpointsWithPassword.add(string);
            }
         }
      }
   }

   boolean isEndpointWithPasswordInContent(BufferedRequestWrapper request) {
      String path = request.getRequestURI().substring(request.getContextPath().length());
      return endpointsWithPassword.contains(path);
   }
}
