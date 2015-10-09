(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('CommonUtilService', CommonUtilService);

   CommonUtilService.$inject = [];

   function CommonUtilService() {
      var service = {
         initCommonAndOperator: initCommonAndOperator
      };

      return service;

      //////////

      function initCommonAndOperator($scope) {

         $scope.andOperator = function () {
            var result = arguments.length > 0;
            for (var i = 0; i < arguments.length; i++) {
               result = result && arguments[i];
            }
            return result;
         };
      }
   }
})();


