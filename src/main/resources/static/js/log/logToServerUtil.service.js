
(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('logToServerUtil', logToServerUtil);

   logToServerUtil.$inject = ['$log', 'Logger', '$window'];

   function logToServerUtil($log, Logger, $window) {
      var service = {
         info: info,
         warn: warn,
         error: error,
         trace: trace,
         debug: debug
      };

      return service;
      
      //////////

      function info(msg, cause, stackTrace) {
         var javaScriptLog = new Logger();
         javaScriptLog.url = $window.location.href;
         javaScriptLog.message = msg || 'No message specified';
         javaScriptLog.cause = cause || 'No cause specified';
         javaScriptLog.stackTrace = stackTrace || 'No stacktrace specified';
         $log.info(javaScriptLog.url, javaScriptLog.message, javaScriptLog.cause, javaScriptLog.stackTrace);
         javaScriptLog.$info();
      }

      function warn(msg, cause, stackTrace) {
         var javaScriptLog = new Logger();
         javaScriptLog.url = $window.location.href;
         javaScriptLog.message = msg || 'No message specified';
         javaScriptLog.cause = cause || 'No cause specified';
         javaScriptLog.stackTrace = stackTrace || 'No stacktrace specified';
         $log.warn(javaScriptLog.url, javaScriptLog.message, javaScriptLog.cause, javaScriptLog.stackTrace);
         javaScriptLog.$warn();
      }

      function error(msg, cause, stackTrace) {
         var javaScriptLog = new Logger();
         javaScriptLog.url = $window.location.href;
         javaScriptLog.message = msg || 'No message specified';
         javaScriptLog.cause = cause || 'No cause specified';
         javaScriptLog.stackTrace = stackTrace || 'No stacktrace specified';
         $log.error(javaScriptLog.url, javaScriptLog.message, javaScriptLog.cause, javaScriptLog.stackTrace);
         javaScriptLog.$error();
      }

      function trace(msg, reason, stackTrace) {
         var javaScriptLog = new Logger();
         javaScriptLog.url = $window.location.href;
         javaScriptLog.message = msg || 'No message specified';
         javaScriptLog.cause = (reason.status || 'No status specified')
                 + ' '
                 + (reason.statusText || 'No statusText specified')
                 + '. Data: '
                 + (reason.data || 'No data specified');
         javaScriptLog.stackTrace = stackTrace || 'No stacktrace specified';
         $log.error(javaScriptLog.url, javaScriptLog.message, javaScriptLog.cause, javaScriptLog.stackTrace);
         javaScriptLog.$error();
      }

      function debug(msg, cause, stackTrace) {
         var javaScriptLog = new Logger();
         javaScriptLog.url = $window.location.href;
         javaScriptLog.message = msg || 'No message specified';
         javaScriptLog.cause = cause || 'No cause specified';
         javaScriptLog.stackTrace = stackTrace || 'No stacktrace specified';
         $log.debug(javaScriptLog.url, javaScriptLog.message, javaScriptLog.cause, javaScriptLog.stackTrace);
         javaScriptLog.$debug();
      }
   }
})();
