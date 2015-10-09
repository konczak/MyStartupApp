(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .directive('mystartupappDayOfWeek', mystartupappDayOfWeek);

   function mystartupappDayOfWeek() {
      return {
         restrict: 'EAC',
         scope: {
            value: '=mystartupappDayOfWeek'
         },
         templateUrl: "directiveDayOfWeekTemplate",
         controller: function ($scope, dateResolveUtil) {
            var value = $scope.value;

            if (typeof (value) === 'object' && typeof (value.getDay) === 'function') {
               $scope.calculated = value.getDay();
            } else if (typeof (value) === 'string') {
               $scope.calculated = dateResolveUtil.convertToDate(value).getDay();
            } else if (typeof (value) === 'number') {
               $scope.calculated = value;
            } else {
               $scope.calculated = -1;
            }
         }
      };
   }
})();
