(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('popupService', popupService);

   popupService.$inject = ['$translate', 'toaster'];

   function popupService($translate, toaster) {
      return {
         error: error,
         note: note,
         success: success,
         wait: wait,
         warning: warning
      };
      

      function invokeToaster(type, title, body) {
    	  toaster.pop(type, parseText(title), parseText(body));
      }
      
      function parseText(text) {
    	  if (typeof text === 'string') {
    		  return $translate.instant(text)
    	  }
    	  
    	  if (!text.hasOwnProperty('message') && !text.hasOwnProperty('key')) {
    		  throw 'one of "message" or "key" properties has to be defined';
    	  } else if (text.hasOwnProperty('message') && text.hasOwnProperty('key')) {
    		  throw 'only one of "message" or "key" properties can be defined';
    	  }
    	  
    	  if (text.hasOwnProperty('message')) {
    		  return text.message;
    	  } else if (text.hasOwnProperty('data')) {
    		  return $translate.instant(text.key, text.data);
    	  } else {
    		  return $translate.instant(text.key);
    	  }
      }
      
      function error(title, body) {
    	 invokeToaster('error', title, body);
      }
      
      function note(title, body) {
    	 invokeToaster('note', title, body);
      }
      
      function success(title, body) {
    	 invokeToaster('success', title, body);
      }
      
      function wait(title, body) {
    	 invokeToaster('wait', title, body);
      }
      
      function warning(title, body) {
    	 invokeToaster('warning', title, body);
      }
   }
})();