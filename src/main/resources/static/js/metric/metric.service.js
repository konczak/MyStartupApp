(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('Metrics', Metrics);

   Metrics.$inject = ['$resource'];

   function Metrics($resource) {
      return $resource('/api/admin', {}, {
         getMetrics: {
            method: 'GET',
            url: '/api/admin/metrics',
            transformResponse: function (data) {
               return {data: JSON.parse(data)};
            }}
      });
   }
})();
