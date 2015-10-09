package pl.konczak.mystartupapp.web.filter;

import java.util.HashSet;
import java.util.Set;

/**
 * This class can be used to define REST endpoint which request and response should not be logged. Example: when you
 * need to send password through REST API it shouldn't be logged.
 *
 * @author Piotr.Konczak
 */
public class ResponseLogSkipperVerifier {

   private final Set<String> endpointsWithNoResponseToLog = new HashSet<>();

   ResponseLogSkipperVerifier(String endpoints) {
      if (endpoints != null) {
         String[] splitted = endpoints.split("\n");
         for (String string : splitted) {
            string = string.trim();
            if (!string.isEmpty()) {
               endpointsWithNoResponseToLog.add(string);
            }
         }
      }
   }

   boolean isEndpointWithNoResponseToLogInContent(BufferedRequestWrapper request) {
      String path = request.getRequestURI().substring(request.getContextPath().length());
      return endpointsWithNoResponseToLog.contains(path);
   }
}
