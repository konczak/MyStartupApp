(function () {
	'use strict';
	
	angular
		.module('myStartupApp')
		.directive('parseName' , function () {
			return {
				restrict: 'A',
				require: 'ngModel',
				link: function (scope, elem, attr, ngModel) {
					ngModel.$name = attr.parseName;
				}
			};
		});
})();