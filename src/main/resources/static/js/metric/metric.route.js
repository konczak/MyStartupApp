(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .config(metricProvider);

   metricProvider.$inject = ['$stateProvider'];

   function metricProvider($stateProvider) {
	   var resolveMetrics = ['$state', 'Metrics', 'popupService', 'logToServerUtil', loadMetrics];
	   
      function loadMetrics($state, Metrics, popupService, logToServerUtil) {
         var metricsPromise = Metrics.getMetrics().$promise;
         metricsPromise
                 .catch(function (reason) {
                    logToServerUtil.trace('get Metrics failed', reason);
                    popupService.error( 'shared.popup.general-failure.title',
                            'shared.popup.general-failure.body');
                    $state.go('dashboard');
                 });
         return metricsPromise;
      }

      $stateProvider
      		  .state('metric', {
                 parent: 'root',
                 url: '/metric',
                 abstract: true,
                 template: '<ui-view />',
                 ncyBreadcrumb: {
                    label: 'skipThisLabelInFavorOfManualHandling'
                 }
      		  })
              .state('metric.list', {
            	 url: '/list',
                 templateUrl: '/metric/list',
                 controller: 'MetricListCtrl',
                 resolve: {
                    metrics: resolveMetrics,
                    translations: ['$translate','$translatePartialLoader',
                       function($translate, $translatePartialLoader){
                          $translatePartialLoader.addPart('metric/list');
                          return $translate.refresh();
                    }]
                 },
				ncyBreadcrumb: {
				    label: 'breadcrumb.metric'
				  }
              });
   }
})();
