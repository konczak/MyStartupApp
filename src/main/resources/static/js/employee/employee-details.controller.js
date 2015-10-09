(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .controller('EmployeeDetailsCtrl', EmployeeDetailsCtrl);

   EmployeeDetailsCtrl.$inject = ['$scope', 'employee'];

   function EmployeeDetailsCtrl($scope, employee) {
      $scope.employee = employee;
      $scope.$parent.changeView('DETAILS');
   }
})();
