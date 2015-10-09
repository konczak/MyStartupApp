(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .controller('NavbarController', NavbarController);

   NavbarController.$inject = [
      '$scope',
      '$modal',
      '$interval',
      '$http',
      '$cookies',
      'loggedEmployee',
      '$translate'
   ];

   function NavbarController(
           $scope, $modal, $interval,
           $http, $cookies, loggedEmployee, $translate) {
      $scope.loggedEmployee = loggedEmployee;
      $scope.changeLanguage = changeLanguage;
      $scope.activeLanguage = $cookies.get('language');

      $interval(verifySessionExpiration, 1200000);

      function verifySessionExpiration() {
         $http.get('api/authentication')
                 .error(function () {
                    $modal.open({
                       templateUrl: 'sessionExpired',
                       controller: 'SessionExpiredModalController',
                       backdrop: "static",
                       size: "sm"
                    });
                 });
      }

      function changeLanguage(language) {
         $cookies.put('language', language);
         $translate.use(language);
         $translate.refresh();
         $scope.activeLanguage = language;
      }
   }
})();
