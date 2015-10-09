
(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('errorLogService', errorLogService);

   errorLogService.$inject = ['$log', '$window', 'stacktraceService'];

   // The error log service is our wrapper around the core error
   // handling ability of AngularJS. Notice that we pass off to
   // the native "$log" method and then handle our additional
   // server-side logging.
   function errorLogService($log, $window, stacktraceService) {

      // I log the given error to the remote server.
      function log(exception, cause) {

         // Pass off the error to the default error handler
         // on the AngualrJS logger. This will output the
         // error to the console (and let the application
         // keep running normally for the user).
         $log.error.apply($log, arguments);

         // Now, we need to try and log the error the server.
         // --
         // NOTE: In production, I have some debouncing
         // logic here to prevent the same client from
         // logging the same error over and over again! All
         // that would do is add noise to the log.
         try {

            var errorMessage = exception.toString();
            var stackTrace = stacktraceService.print({e: exception});

            // Log the JavaScript error to the server.
            $.ajax({
               type: "POST",
               beforeSend: function (request) {
                  request.setRequestHeader(
                          "Authorization",
                          "AuthenticationUnit MWViNTA0NTQtZDU1Ny00MTgyLWI2ZmUtZjA1ZGU0MzMwOGQw"
                          );
               },
               url: "/api/javascript/error",
               contentType: "application/json",
               data: angular.toJson({
                  errorUrl: $window.location.href,
                  errorMessage: errorMessage,
                  stackTrace: stackTrace,
                  cause: (cause || "")
               })
            });

         } catch (loggingError) {
            // For Developers - log the log-failure.
            $log.warn("Error logging failed");
            $log.log(loggingError);
         }
      }

      // Return the logging function.
      return(log);
   }
})();
