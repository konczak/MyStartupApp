(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .controller('EmployeeCommonCtrl', EmployeeCommonCtrl);

   EmployeeCommonCtrl.$inject = ['$scope', 'employee'];

   function EmployeeCommonCtrl($scope, employee) {
      $scope.employee = employee;
      $scope.changeView = changeView;
      function changeView(state) {
         $scope.view = {
            details: false,
            surveys: false,
            meetings: false,
            goals: false,
            performance: false,
            actions: false,
            trainings: false
         };
         
         switch (state) {
            case 'DETAILS':
               $scope.view.details = true;
               break;
            case 'SURVEYS':
               $scope.view.surveys = true;
               break;
            case 'MEETINGS':
               $scope.view.meetings = true;
               break;
            case 'GOALS':
               $scope.view.goals = true;
               break;
            case 'PERFORMANCE':
               $scope.view.performance = true;
               break;
            case 'ACTIONS':
               $scope.view.actions = true;
               break;
            case 'TRAININGS':
               $scope.view.trainings = true;
               break;
            default:
               break;
         }
      }
   }
})();
