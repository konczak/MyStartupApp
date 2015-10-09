(function () {
   'use strict';

   angular.module('myStartupApp', [
      /* Angular modules */
      'ngResource',
      'ngAnimate',
      'ngCookies',
      'ngSanitize',
      'ngTagsInput', 
      /* External modules */
      'ncy-angular-breadcrumb',
      'isteven-multi-select',
      'ui.bootstrap',
      'ui.router',
      'toaster',
      'xeditable',
      'swaggerUi',
      'pascalprecht.translate',
      'pszczolkowski.dateTimePicker'
   ]);
})();

(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('stacktraceService', stacktraceService);

   function stacktraceService() {
      // The "stacktrace" library that we included in the Scripts
      // is now in the Global scope; but, we don't want to reference
      // global objects inside the AngularJS components - that's
      // not how AngularJS rolls; as such, we want to wrap the
      // stacktrace feature in a proper AngularJS service that
      // formally exposes the print method.

      // "printStackTrace" is a global object.
      return({
         print: printStackTrace
      });
   }
})();


(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .provider('$exceptionHandler', exceptionHandler);

   function exceptionHandler() {
      return {
         $get: function (errorLogService) {
            return(errorLogService);
         }
      };
   }
})();

(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .run(runBlock);

   runBlock.$inject = ['$rootScope', '$state', 'editableOptions', '$modalStack'];

   function runBlock($root, $state, editableOptions, $modalStack) {
      editableOptions.theme = 'bs3';
      $root.$on('$stateChangeStart', function (e, curr) {
//         if (curr.$$route
//                 && curr.$$route.resolve) {
//            // Show a loading message until promises are not resolved
            $root.loadingView = true;
//         }
      });
      $root.$on('$stateChangeSuccess', function () {
         // Hide loading message
         $root.loadingView = false;
         $modalStack.dismissAll();
      });
      $root.$on('$stateChangeStart', function(evt, to, params) {
      if (to.redirectTo) {
        evt.preventDefault();
        $state.go(to.redirectTo, params);
      }
    });
   }
})();
