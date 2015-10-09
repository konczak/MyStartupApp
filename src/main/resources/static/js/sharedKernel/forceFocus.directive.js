/**
 *
 *	Directive force-focus
 *
 *	Used to set focus on element.
 *	Restricted to attributes. Uses current scope.
 *
 *	Example:
 *		<input type="text" force-focus />
 *
 *	Author:
 *		Krzysztof Pszczółkowski
 */
(function () {
	'use strict';
	
	angular
		.module('myStartupApp')
		.directive('forceFocus' , ['$timeout', function ($timeout) {
			return {
				restrict: 'A',
				link: function (scope, element, attrs) {
					var DEFAULT_VALUE = 4;
					var timeout = DEFAULT_VALUE;
					
					if (attrs.focusTimeout === undefined) {
						timeout = parseInt(attrs.focusTimeout, 10);
					}
					
					 $timeout(function() {
						 element[0].focus();
				     }, timeout);
				}
			};
		}]);
})();