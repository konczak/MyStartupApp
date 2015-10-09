(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .config(documentationProvider);

   documentationProvider.$inject = ['$stateProvider'];

   function documentationProvider($stateProvider) {
	   $stateProvider
	   	.state('documentationApi', {
	   	   parent: 'root',
		   url: '/documentation/api',
		   templateUrl: '/documentation/api',
           controller: 'DocumentationApiCtrl',
           resolve:{
              translations:['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader){
                    $translatePartialLoader.addPart('documentation/api');
                    return $translate.refresh();
              }]
           },
			ncyBreadcrumb: {
			    label: 'breadcrumb.documentation'
			  }
	   });
   }
})();
