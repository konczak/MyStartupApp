(function () {
	'use strict';
	
	angular
		.module('myStartupApp')
		.config(healthRouteProvider);
	
	healthRouteProvider.$inject = ['$stateProvider'];
	
	function healthRouteProvider($stateProvider) {
		$stateProvider
			.state('health', {
				parent: 'root',
				url: '/health',
				abstract: true,
				template: '<ui-view />',
            ncyBreadcrumb: {
               label: 'skipThisLabelInFavorOfManualHandling'
            }
			})
			.state('health.list', {
				 url: '/list',
				 templateUrl: '/health/list',
	             controller: 'HealthListCtrl',
   				 ncyBreadcrumb: {
   					label: 'breadcrumb.health'
				},
            resolve: {
               translations: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader){
                $translatePartialLoader.addPart('health/list');
                return $translate.refresh();
               }]
            }
			});
	}
})();