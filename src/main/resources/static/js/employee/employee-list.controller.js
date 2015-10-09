(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .controller('EmployeeListCtrl', EmployeeListCtrl);

   EmployeeListCtrl.$inject = [
      '$scope',
      'Employee',
      'employees',
      'titles',
      '$stateParams',
      '$location',
      '$timeout'];

   function EmployeeListCtrl($scope, Employee, employees, titles, $stateParams, $location, $timeout) {
      $scope.loading = false;
      $scope.employees = employees;
      $scope.filterRow = true;
      $scope.searchText = [];
      $scope.searchText.deleted = 'false';
      $scope.title = 'breadcrumb.employee';

      var firstnameChangeTimeout;
      var lastnameChangeTimeout;

      if ($stateParams.deleted !== undefined) {
         $scope.searchText.deleted = $stateParams.deleted;
      }
      $scope.searchText.firstname = $stateParams.firstname;
      $scope.searchText.lastname = $stateParams.lastname;
      $scope.searchText.title = $stateParams.title;
      $scope.refresh = refresh;

      $scope.updateTitleParam = updateTitleParam;
      $scope.updateStatusParam = updateStatusParam;
      $scope.updateFirstnameParam = updateFirstnameParam;
      $scope.updateLastnameParam = updateLastnameParam;

      var employeeTitles = [];
      employeeTitles = employeeTitles.concat(titles);
      employeeTitles.sort();
      $scope.employeeTitles = employeeTitles;

      function reloadEmployees() {
         $scope.loading = true;
         Employee.query().$promise
                 .then(function (data) {
                    $scope.employees = data;
                    $scope.loading = false;
                 });
      }

      function refresh() {
         Employee.refresh().$promise
                 .then(function () {
                    reloadEmployees();
                    $('#ProcessingModal').modal('hide');
                 });
      }

      function updateTitleParam() {
         $stateParams.title = $scope.searchText.title;
         $location.search($stateParams);
      }
      function updateStatusParam() {
         $stateParams.deleted = $scope.searchText.deleted;
         $location.search($stateParams);
      }
      function updateFirstnameParam() {
         if (firstnameChangeTimeout !== undefined) {
            $timeout.cancel(firstnameChangeTimeout);
         }
         firstnameChangeTimeout = $timeout(updateFirstnameParamAtTimeout, 1000);
      }
      function updateLastnameParam() {
         if (lastnameChangeTimeout !== undefined) {
            $timeout.cancel(lastnameChangeTimeout);
         }
         lastnameChangeTimeout = $timeout(updateLastnameParamAtTimeout, 1000);
      }

      function updateFirstnameParamAtTimeout() {
         if ($scope.searchText.firstname) {
            $stateParams.firstname = $scope.searchText.firstname;
         } else {
            $stateParams.firstname = undefined;
         }
         $location.search($stateParams);
         firstnameChangeTimeout = undefined;
      }
      function updateLastnameParamAtTimeout() {
         if ($scope.searchText.lastname) {
            $stateParams.lastname = $scope.searchText.lastname;
         } else {
            $stateParams.lastname = undefined;
         }
         $location.search($stateParams);
         lastnameChangeTimeout = undefined;
      }
   }
})();
