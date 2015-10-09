(function(){
	'use strict';
	
	angular
		.module('myStartupApp')
		.service('Health' , Health);
	
	Health.$inject = ['$resource'];
	
	function Health($resource){
		return $resource('/api/admin/health' , {}, {});
	}
})();