(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('Employee', Employee);

   Employee.$inject = ['$resource', 'dateResolveUtil'];

   function Employee($resource, dateResolveUtil) {
      return $resource('/api/employee/:id', {}, {
    	 query: {
			method: 'GET',
			isArray: true,
			transformResponse: transformResponse
		 },
		 get: {
			method: 'GET',
			transformResponse: transformResponse
		 },
         refresh: {
            method: 'POST',
            url: '/api/employee/refresh',
            isArray: false
         },
         titles: {
            method: 'GET',
            url: '/api/employee/title',
            isArray: true
         },
         getLoggedEmployee: {
            method: 'GET',
            params: {},
            url: '/api/employee/logged',
			transformResponse: transformResponse
         },
         active: {method: 'GET',
            url: '/api/employee/active',
            isArray: true,
			transformResponse: transformResponse
         }
      });
      
      ////////////
      
      function transformResponse(data){
			data = angular.fromJson(data);
			
			if($.isArray(data)){
				for(var i in data){
					transformSingle(data[i]);
				}
			}else{
				transformSingle(data);
			}
			
			return data;
		}
		
		function transformSingle(employee){
			employee.createdAt = dateResolveUtil.convertToDate(employee.createdAt);
			employee.updatedAt = dateResolveUtil.convertToDate(employee.updatedAt);
			employee.deletedAt = dateResolveUtil.convertToDate(employee.deletedAt);
		}
   }
})();
