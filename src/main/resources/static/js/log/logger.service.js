(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('Logger', Logger);

   Logger.$inject = ['$resource'];

   function Logger($resource) {
      return $resource('/api/javascript', {}, {
         info: {
            method: 'POST',
            url: '/api/javascript/info'
         },
         warn: {
            method: 'POST',
            url: '/api/javascript/warn'
         },
         error: {
            method: 'POST',
            url: '/api/javascript/error'
         },
         debug: {
            method: 'POST',
            url: '/api/javascript/debug'
         }
      });
   }
})();
