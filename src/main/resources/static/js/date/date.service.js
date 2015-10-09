(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('dateResolveUtil', dateResolveUtil);

   function dateResolveUtil() {
      var service = {
         formatToISODate: formatToISODate,
         convertIfDateToString: convertIfDateToString,
         convertToDate: convertToDate,
         isAfterNow: isAfterNow,
         onlyDate: onlyDate,
         dateWithoutSeconds: dateWithoutSeconds
      };

      return service;

      //////////

      function formatToISODate(date) {
         if (date === null) {
            return null;
         }
         var year = date.getFullYear();
         var month = date.getMonth() + 1;
         var day = date.getDate();

         var hours = date.getHours();
         var minutes = date.getMinutes();
         if (month < 10) {
            month = '0' + month;
         }
         if (day < 10) {
            day = '0' + day;
         }

         if (minutes < 10) {
            minutes = '0' + minutes;
         }
         if (hours < 10) {
            hours = '0' + hours;
         }
         return (year + "-" + month + "-" + day + "T" + hours + ':' + minutes + ":00");
      }

      function convertIfDateToString(date) {
         if (typeof (date) === "string") {
            return date;
         } else {
            return formatToISODate(date);
         }
      }

      function convertToDate(isoDate) {
         var date = null;
         if (isoDate !== null) {
            date = new Date(isoDate.slice(0, 4),
                    isoDate.slice(5, 7) - 1,
                    isoDate.slice(8, 10),
                    isoDate.slice(11, 13),
                    isoDate.slice(14, 16),
                    isoDate.slice(17, 19),
                    0);
         }
         return date;
      }

      function isAfterNow(date) {
         if (date === null
                 || date > new Date()) {
            return true;
         }

         if (date <= new Date()) {
            return false;
         }
      }

      function onlyDate(date) {
         var result = null;
         if (date !== null) {
            result = date.substring(0, 10);
         }
         return result;
      }

      function dateWithoutSeconds(date) {
         var result = null;
         if (date !== null) {
            result = date.substring(0, 16);
         }
         return result;
      }
   }
})();
