(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .directive('mystartupappDaysRemain', mystartupappDaysRemain);

   function mystartupappDaysRemain() {
      return {
         restrict: 'EAC',
         scope: {
            date: '=date',
            number: '=number'
         },
         templateUrl: "directiveDaysRemainTemplate",
         controller: function ($scope, dateResolveUtil) {
            $scope.calculated = 0;

            if ($scope.date) {
               var timeDiff = dateResolveUtil.convertToDate($scope.date).getTime() - (new Date()).getTime();
               $scope.calculated = Math.ceil(timeDiff / (1000 * 3600 * 24));
            }
            if ($scope.number) {
               $scope.calculated = $scope.number;
            }
         }
      };
   }
})();

