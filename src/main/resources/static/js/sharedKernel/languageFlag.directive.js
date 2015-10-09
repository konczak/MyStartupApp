(function(){
	'use strict';
	
	angular
		.module('myStartupApp')
		.directive('languageFlag', languageFlag);
      
   languageFlag.$inject = ['$translate', '$translatePartialLoader'];
	
	function languageFlag($translate, $translatePartialLoader){
		return {
		  restrict: 'AE',
		  templateUrl: '/fragments/directives/languageFlag.html',
		  scope: {
			languageFlag: '=',
			withLabel: '='
		  },
        link: function (scope, element, attributes, ngModelController) {
            $translatePartialLoader.addPart('directives/languageFlag');
            $translate.refresh();
         }
		};
	}
	
})();