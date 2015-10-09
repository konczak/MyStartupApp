(function(){
	'use strict';
	
	angular
		.module('myStartupApp')
		.directive('enterPress', enterPress);
	
	function enterPress(){
		return {
		  restrict: 'A',
		  link: function(scope, elem, attr) {
			  elem.bind('keydown', function(e){
				  if(e.which === 13){
					  scope.$apply(function(){
						  scope.$eval(attr.enterPress);
					  });
					  e.preventDefault(0);
				  }
			  });
		  }
		};
	}
	
})();