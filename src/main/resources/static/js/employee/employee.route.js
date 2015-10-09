(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .config(employeeProvider);

   employeeProvider.$inject = ['$stateProvider','$breadcrumbProvider'];

   function employeeProvider($stateProvider,$breadcrumbProvider) {
	   var resolveEmployeeTitles = ['$state', 'Employee', 'popupService', 'logToServerUtil', loadEmployeeTitles];
	   var resolveEmployees = ['$state', 'Employee', 'popupService', 'logToServerUtil', loadEmployees];
	   var resolveSingleEmployee = ['$state', '$stateParams', 'Employee', 'popupService', 'logToServerUtil', 
	                                loadSingleEmployee];
	   var title
	   	   
	   $stateProvider
 		.state('employee', {
 			parent: 'root',
 			url: '/employee',
 			abstract: true,
 			template: '<ui-view />',
         ncyBreadcrumb: {
            label: 'skipThisLabelInFavorOfManualHandling'
         }
 		})
 		.state('employee.list', {
 			url: '/list?lastname&firstname&title&deleted',
         reloadOnSearch: false,
 			templateUrl: '/employee/list',
           controller: 'EmployeeListCtrl',
           resolve: {
              employees: resolveEmployees,
              titles: resolveEmployeeTitles,
              translations: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader){
                    $translatePartialLoader.addPart('employee/list');
                    return $translate.refresh();
              }]
           },
           ncyBreadcrumb: {
			    label: 'breadcrumb.employee'
			  }
 		})
      .state('employee.common', {
         url: '/{id:int}',
         templateUrl: '/employee/common',
         controller: 'EmployeeCommonCtrl',
         redirectTo: 'employee.common.details',
         resolve: {
            employee: resolveSingleEmployee,
            translations: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                  $translatePartialLoader.addPart('employee/common');
                  return $translate.refresh();
               }]
         },
         ncyBreadcrumbData: [],
         ncyBreadcrumb: {
            parent: 'employee.list',
            label: '{{employee.firstname}} {{employee.lastname}}'
         }
 		})
 		.state('employee.common.details', {
 			url: '/details',
 			templateUrl: '/employee/details',
            controller: 'EmployeeDetailsCtrl',
			ncyBreadcrumb: {
				parent: 'employee.common',
			    label: 'shared.details'
			  },
           resolve: {
              translations: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader){
                    $translatePartialLoader.addPart('employee/details');
                    return $translate.refresh();
              }]
           }
 		});
	   
	   
      function loadEmployeeTitles($state, Employee, popupService, logToServerUtil) {
         var titlesPromise = Employee.titles().$promise;
         titlesPromise
                 .catch(function (reason) {
                    logToServerUtil.trace('get Employee titles failed', reason);
                    popupService.error( 'shared.popup.general-failure.title',
                                'shared.popup.general-failure.body');
                    $state.go('dashboard');
                 });
         return titlesPromise;
      }

      function loadEmployees($state, Employee, popupService, logToServerUtil) {
         var employeesPromise = Employee.query().$promise;
         employeesPromise
                 .then(function () {
                 }, function (reason) {
                    logToServerUtil.trace('get Employees failed', reason);
                    popupService.error( 'shared.popup.general-failure.title',
                                'shared.popup.general-failure.body');
                    $state.go('dashboard');
                 });
         return employeesPromise;
      }

      function loadSingleEmployee($state, $stateParams, Employee, popupService, logToServerUtil) {
         var employeePromise = Employee.get({id: $stateParams.id}).$promise;
         employeePromise
                 .catch(function (reason) {
                    logToServerUtil.trace('get Employee failed', reason);
                    popupService.error( 'shared.popup.general-failure.title',
                                'shared.popup.general-failure.body');
                    $state.go('dashboard');
                 });
         return employeePromise;
      }
   }
})();
