(function () {
	'use strict';
	
	angular
		.module('myStartupApp')
		.factory('languageUtil', languageUtil);
	
	function languageUtil () {
		return {
			tagFor: tagFor
		}
		
		
		function tagFor(language) {
			switch(language){
				case 'polish':
					return 'pl-PL';
					break;
				case 'english':
					return 'en-GB';
					break;
				default:
					throw 'language not supported';
			}
		}
	}
})();