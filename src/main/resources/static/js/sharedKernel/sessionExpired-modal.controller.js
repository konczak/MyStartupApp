(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .controller('SessionExpiredModalController', SessionExpiredModalController);

   SessionExpiredModalController.$inject = ['$scope', '$window', '$location'];

   function SessionExpiredModalController($scope, $window, $location) {
      $scope.close = function () {
         $window.location.href = $location.protocol() + "://" + $location.host() + ":" + $location.port() + '/MyStartupApp/login';
      };
   }
})();
