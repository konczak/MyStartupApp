(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('LoggedEmployee', LoggedEmployee);

   LoggedEmployee.$inject = ['Employee'];
   
   function LoggedEmployee(Employee) {
      var loggedEmployee = {};
      
      loggedEmployee.$promise = Employee.getLoggedEmployee().$promise;
      loggedEmployee.$promise
      	.then(function (loadedLoggedEmployee) {
    	  angular.extend(loggedEmployee, loadedLoggedEmployee);
      });
      
      return loggedEmployee;
   }
})();
