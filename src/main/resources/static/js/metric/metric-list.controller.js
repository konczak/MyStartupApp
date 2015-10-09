(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .controller('MetricListCtrl', MetricListCtrl);

   MetricListCtrl.$inject = ['$scope', 'metrics'];

   function MetricListCtrl($scope, metrics) {
      var array = [];
      $scope.errorsJs = 0;
      $scope.metrics = array;

      angular.forEach(metrics.data, function (value, key) {
         array.push({
            value: value,
            key: key
         });
         if (key === "counter.status.200.api.javascript.error") {
            $scope.errorsJs = value;
         }
      });
   }
})();
