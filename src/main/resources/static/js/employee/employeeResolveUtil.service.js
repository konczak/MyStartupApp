(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('employeeResolveUtil', employeeResolveUtil);

   function employeeResolveUtil() {
      function isEmployeeIdIn(employeeId, employees) {
         for (n = 0; n < employees.length; n++) {
            if (employees[n].employeeId === employeeId) {
               return true;
            }
         }
         return false;
      }

      return({
         isEmployeeIdIn: isEmployeeIdIn
      });
   }
})();
