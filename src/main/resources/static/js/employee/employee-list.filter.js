(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .filter('employeeListFilter', employeeListFilter);

   function employeeListFilter() {
      return filter;

      //////////

      function filter(value, input) {
         var result = [];
         for (var i = 0; i < value.length; i++) {
            if (input !== undefined) {
               if (!isDeleted(value[i], input)
                       || !isMatchTitle(value[i], input)
                       || !isMatchFirstName(value[i], input)
                       || !isMatchLastName(value[i], input)) {
                  continue;
               }
            }
            result.push(value[i]);
         }
         return result;
      }

      function isDeleted(value, input) {
         if (input.deleted !== undefined && input.deleted !== '') {
            if (value.deleted.toString() !== input.deleted) {
               return false;
            }
         }
         return true;
      }

      function isMatchTitle(value, input) {
         if (input.title !== undefined && input.title !== "" && input.title !== null) {
               if (input.title !== value.title) {
                  return false;
            }
         }
         return true;
      }

      function isMatchFirstName(value, input) {
         if (input.firstname !== undefined) {
            if (!(value.firstname.toUpperCase().match(input.firstname.toUpperCase()))) {
               return false;
            }
         }
         return true;
      }

      function isMatchLastName(value, input) {
         if (input.lastname !== undefined) {
            if (!(value.lastname.toUpperCase().match(input.lastname.toUpperCase()))) {
               return false;
            }
         }
         return true;
      }
   }
})();
