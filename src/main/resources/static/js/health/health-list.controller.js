(function(){
	'use strict';
	
	angular
		.module('myStartupApp')
		.controller('HealthListCtrl' , HealthListCtrl);
	
	HealthListCtrl.$inject = [
          '$scope',
          '$state',
          '$modal',
          'Health',
          'popupService',
          'logToServerUtil'];
	
	function HealthListCtrl($scope, $state, $modal, Health, popupService, logToServerUtil){
		$scope.healths = undefined;
		$scope.status = undefined;
		$scope.refresh = refresh;
		$scope.showStackTrace = showStackTrace;
		
		loadHealths();
		
		
		function resolveHealths(healths){
			$scope.healths = healths;
			$scope.status = healths.status;
			
			delete healths.status;
		}
		
		function loadHealths(){
			Health.query().$promise
				.then(function (healths) {
					resolveHealths(healths);
				}, function(reason) {
					if(reason.status && reason.status === 503 && reason.data){
						resolveHealths(reason.data);
					}else{
						logToServerUtil.trace('get Health failed', reason);
                  popupService.error( 'shared.popup.general-failure.title',
                          'shared.popup.general-failure.body');
						$state.go('dashboard');
					}
				});
		}
		
		function refresh(){
			$scope.healths = undefined;
			$scope.status = undefined;
			
			loadHealths();
		}
		
		function showStackTrace(stackTrace){
			$modal.open({
				size: 'lg',
				templateUrl: '/health/stacktraceModal.html',
				controller: ['$scope', '$modalInstance', function($scope, $modalInstance){
					$scope.stackTrace = stackTrace;
					$scope.cancel = function(){
						$modalInstance.dismiss('cancel');
					};
				}],
         resolve:{
            translations:['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader){
                  $translatePartialLoader.addPart('health/stacktrace');
                  return $translate.refresh();
            }]
         }
			});
		}
	}
})();