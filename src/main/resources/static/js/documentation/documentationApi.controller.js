(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .controller('DocumentationApiCtrl', DocumentationApiCtrl);

   DocumentationApiCtrl.$inject = ['$scope', 'popupService', 'logToServerUtil'];

   function DocumentationApiCtrl($scope, popupService, logToServerUtil) {
      $scope.businessApi = 'v2/api-docs?group=Business-API';
      $scope.adminApi = 'v2/api-docs?group=Admin-API';
      $scope.authorization = 'AuthenticationUnit MWViNTA0NTQtZDU1Ny00MTgyLWI2ZmUtZjA1ZGU0MzMwOGQw';
      $scope.myErrorHandler = myErrorHandler;
      $scope.myTransform = myTransform;
      $scope.switchApi = switchApi;

      // init form
      switchApi($scope.businessApi);

      //////////

      function switchApi(api) {
         $scope.url = api;
         $scope.swaggerUrl = api;
      }
      // error management
      function myErrorHandler(data, status) {
         logToServerUtil.error('Load Swagger documentation failure', status);
         popupService.error('documentation-api.popup.load.failure.title','documentation-api.popup.load.failure.title');
      }
      // transform API explorer requests
      function myTransform(request) {
         request.headers['Authorization'] = $scope.authorization;
      }
   }
})();
