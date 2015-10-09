(function () {
   'use strict';

   angular.module('myStartupApp')
           .factory('languageStorageService',languageStorageService);
   
   languageStorageService.$inject=['$cookies'];
   
   function languageStorageService($cookies){
      return {
         put: function(name,value){
        	//cookie will expire in one years
        	var expireDate = new Date();
        	expireDate.setFullYear(expireDate.getFullYear() + 1);
            $cookies.put('language', value, {'expires': expireDate});
         },
         get: function(name){
            return $cookies.get('language');
         }
      };
   }
})();